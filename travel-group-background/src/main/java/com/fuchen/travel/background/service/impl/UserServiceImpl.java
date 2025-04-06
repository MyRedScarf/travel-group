package com.fuchen.travel.background.service.impl;

import com.fuchen.travel.background.entity.LoginTicket;
import com.fuchen.travel.background.entity.User;
import com.fuchen.travel.background.mapper.UserMapper;
import com.fuchen.travel.background.service.UserService;
import com.fuchen.travel.background.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author 伏辰
 * @Date 2023/1/5
 * 用户-service层-实现类
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService, TravelConstant {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private HostHolder hostHolder;

    /**
     * 腾讯云存储地区
     */
    @Value("${qcloud.cosRegion}")
    private String cosRegion;

    /**
     * 腾讯云密钥id
     */
    @Value("${qcloud.key.secretId}")
    private String secretId;

    /**
     * 腾讯云密钥key
     */
    @Value("${qcloud.key.secretKey}")
    private String secretKey;

    /**
     * 腾讯云对象存储桶名
     */
    @Value("${qcloud.bucket.header.name}")
    private String bucketName;

    /**
     * 腾讯云对象存储访问路径
     */
    @Value("${qcloud.bucket.header.url}")
    private String qCloudUrl;

    /**
     * 腾讯云工具类对象
     */
    @Autowired
    private QCloudUtil qCloudUtil;;
    /**
     * 获取普通用户数量
     * @return
     */
    @Override
    public Integer getUserCount() {
        User user = hostHolder.getUser();
        if (user.getType() == 1) {
            //从redis中获取普通用户数量
            Integer userCount = (Integer) redisTemplate.opsForValue().get("userCount");
            //如果不为空，则直接返回普通用户数量
            if (userCount != null) {
                return userCount;
            }
            //为空，则查询普通用户数量并放入redis中
            userCount = userMapper.selectUserCount();
            redisTemplate.opsForValue().set("userCount", userCount);
            return userCount;
        } else {
            //从redis中获取全部用户数量
            Integer userCountAll = (Integer) redisTemplate.opsForValue().get("userCountAll");
            //如果不为空，则直接返回普通用户数量
            if (userCountAll != null) {
                return userCountAll;
            }
            //为空，则查询全部用户数量并放入redis中
            userCountAll = userMapper.selectUserCountAll();
            redisTemplate.opsForValue().set("userCountAll", userCountAll);
            return userCountAll;
        }


    }

    /**
     * 获取封禁用户数量
     * @return
     */
    @Override
    public Integer getBanUserCount() {
        //从redis中获取封禁用户数量
        Integer userBanCount = (Integer) redisTemplate.opsForValue().get("userBanCount");
        //如果不为空，则直接返回封禁用户数量
        if (userBanCount != null) {
            return userBanCount;
        }
        //为空，则查询封禁用户数量并放入redis中
        userBanCount = userMapper.selectBanUserCount();
        redisTemplate.opsForValue().set("userBanCount", userBanCount);
        return userBanCount;
    }

    /**
     * 分页获取用户信息
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<User> getAllUser(Integer offset, Integer limit) {
        User user = hostHolder.getUser();
        return userMapper.selectAllUser(offset, limit, user.getType());
    }

    /**
     * 获取用户信息
     * @param ticket
     * @return
     */
    @Override
    public Map<String, Object> getUser(String ticket) {
        Map<String, Object> map = new HashMap<>();
        //从redis中的登录凭证汇总获取用户id
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        if (loginTicket.getStatus() == 1) {
            map.put("errorMsg","请重新登录！");
            return map;
        }
        Integer userId = loginTicket.getUserId();
        User user = userMapper.selectUserById(userId);
        String userKey = RedisKeyUtil.getUserKey(userId);
        //将获取的user信息放入redis
        redisTemplate.opsForValue().set(userKey, user);
        map.put("user",user);
        return map;
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param expiredSeconds 时间
     * @return Map集合
     */
    @Override
    public Map<String, Object> login(String username, String password, Integer expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        //空值判断
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "请输入账号!");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码为空！");
            return map;
        }

        //验证账号
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "不存在该账号！");
            return map;
        }
        if (user.getStatus() == 0) {
            map.put("usernameMsg","请先激活！");
            return map;
        }
        if (user.getType() == 0) {
            map.put("usernameMsg","非管理员！");
            return map;
        }


        //验证密码
        password = TravelUtil.md5(password + user.getSalt());
        if (!password.equals(user.getPassword())) {
            map.put("passwordMsg", "密码错误！");
            return map;
        }


        //生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(TravelUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));

        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey, loginTicket);

        map.put("ticket", loginTicket.getTicket());

        return map;
    }

    /**
     * 退出登录
     * @param ticket 登录凭证
     */
    @Override
    public void logout(String ticket) {
        //loginTicketMapper.updateStatus(ticket, 1);
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey, loginTicket);
        clearCache(loginTicket.getUserId());
    }

    @Override
    public LoginTicket findLoginTicket(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 通过id获取用户信息
     * @param userId
     * @return
     */
    @Override
    public User getUserById(Integer userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 修改用户信息
     * @param username 用户名
     * @param password 密码
     * @param headerImg 头像路径
     */
    @Override
    public void updateUser(String username, String password, String headerImg) {
        User user =  user = hostHolder.getUser();
        Integer userId = null;
        if (password != null || !password.isEmpty()) {
            password = TravelUtil.md5(password + user.getSalt());
        }
        if (username == null || username.isEmpty()) {
            username = null;
            userId = hostHolder.getUser().getId();
        }
        userMapper.updateUser(userId, username, password, headerImg);
    }

    /**
     * 数据变更时删除数据
     * @param userId
     * @return
     */
    private void clearCache(Integer userId){
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(Integer userId) {
        User user = this.getUserById(userId);
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                switch (user.getType()){
                    case 1:
                        return  AUTHORITY_ADMIN;
                    case 2:
                        return AUTHORITY_MODERATOR;
                    default:
                        return AUTHORITY_USER;
                }
            }
        });
        return list;
    }

    /**
     * 查询指定username的User对象
     * @param username 用户名
     * @return
     */
    @Override
    public Boolean isUserByUsername(String username) {
        //判断用户是否存在
        Integer isUser = userMapper.userByUsernameExist(username);
        //结果为1存在，否则不存在
        if (isUser == 1) {
            return true;
        }
        return false;
    }

    /**
     * 修改用户密码
     * @param password
     */
    @Override
    public void updatePassword(String password) {
        //获取用户信息
        User user = hostHolder.getUser();
        //对用户密码进行加密
        password = TravelUtil.md5(password + user.getSalt());
        //修改用户密码
        userMapper.updatePasswordById(user.getId(), password);
    }

    /**
     * 判断指定用户名是否存在
     * @param username
     * @return
     */
    @Override
    public Boolean isUserExist(String username) {
        if (userMapper.selectUsernameExist(username) == null) {
            return true;
        }
        return false;
    }

    /**
     * 判断指定邮箱是否存在
     * @param email
     * @return
     */
    @Override
    public Boolean isEmailExist(String email) {
        if (userMapper.selectEmailExist(email) == null) {
            return true;
        }
        return false;
    }

    @Value("${user.path.headerUrl}")
    private String headerUrl;

    /**
     * 添加用户
     * @param username 用户名
     * @param email 邮箱
     * @param password 密码
     */
    @Override
    public void addUser(String username, String email, String password) {
        //默认头像
        String headerUrl = this.headerUrl;

        //密码加密
        String salt = TravelUtil.generateUUID().substring(0 , 5);
        password = TravelUtil.md5(password + salt);

        //添加用户
        userMapper.insertUser(username, email, password, salt, headerUrl, new Date());
        //清除redis中用户数量
        redisTemplate.delete("userCountAll");
    }

    /**
     * 移出用户信息
     * @param list
     */
    @Override
    public void removeUser(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            //通过？分割字符串
            if (!str.equals("on")) {
                String[] split = str.split("\\?");
                List<String> strings = Arrays.asList(split);
                //如果分割后的字符串后面的值为2，说明当前状态为封禁，放入解封集合，否则放入封禁集合
                userMapper.deleteUserById(strings);
            }
        }
        //清除redis中用户数量
        redisTemplate.delete("userCount");
        redisTemplate.delete("userCountAll");
    }

    /**
     * 通过关键字查询用户信息
     * @param keyword 关键字
     * @return
     */
    @Override
    public List<User> getUserSearch(String keyword, String type, Integer offset, Integer limit) {
        return userMapper.selectUserByKeyword(keyword, type, offset, limit);
    }


    /**
     * 查过关键字询用户数量
     * @param keyword
     * @return
     */
    @Override
    public Integer getUserCountSearch(String keyword, String type) {
        return userMapper.selectUserCountByKeyword(keyword, type);
    }

    /**
     * 查询管理员数量
     * @return
     */
    @Override
    public Integer getUserCountAdmin(String type) {
        return userMapper.selectUserCountAdmin(type);
    }

    /**
     * 查询管理员信息
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<User> getAllUserAdmin(String type, Integer offset, Integer limit) {
        return userMapper.selectAllUserAdmin(type, offset, limit);
    }

    /**
     * 封禁-解封用户
     * @param list 用户id?status集合
     */
    @Override
    public void banUser(List<String> list) {
        //创建需要封禁的list集合老保存id
        List<String> ban = new ArrayList<>();
        //创建需要解封的list集合老保存id
        List<String> noNan = new ArrayList<>();
        //循环遍历list集合
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            //通过？分割字符串
            if (!str.equals("on")) {
                String[] split = str.split("\\?");
                //如果分割后的字符串后面的值为2，说明当前状态为封禁，放入解封集合，否则放入封禁集合
                if ("2".equals(split[1])){
                    noNan.add(split[0]);
                } else {
                    ban.add(split[0]);
                }

            }
        }

        if (ban.size() != 0) {
            //修改用户状态为封禁
            userMapper.updateUserToBanStatus(ban);
            //清除redis中封禁的数量
        }
        if(noNan.size() != 0) {
            //修改用户状态为正常状态
            userMapper.updateUserToNoBanStatus(noNan);
            //清除redis中封禁的数量
        }
        redisTemplate.delete("userBanCount");


    }

    /**
     * 获取封禁用户
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<User> getAllUserBan(Integer offset, Integer limit) {
        return userMapper.selectUserBan(offset, limit);
    }

    /**
     * 上传头像到腾讯云
     * @param headerImg 头像文件
     * @param filename 文件名
     * @param suffix 后缀
     */
    @Override
    public void uploadHeaderToQCloud(MultipartFile headerImg, String filename, String suffix) {
        //生成随机文件名
        filename = TravelUtil.generateUUID() + suffix;
        //上传腾讯云
        qCloudUtil.uploadFile(bucketName, filename, headerImg , cosRegion, secretId, secretKey);

        //更新用户头像的路径(web访问路径)
        User user = hostHolder.getUser();
        String headerUrl = qCloudUrl + "/" +  filename ;

        //修改头像路径
        userMapper.uploadHeader(user.getId(), headerUrl);
    }




}
