package com.fuchen.travel.background.service;

import com.fuchen.travel.background.entity.LoginTicket;
import com.fuchen.travel.background.entity.Scenic;
import com.fuchen.travel.background.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author 伏辰
 * @Date 2023/1/5
 * 用户-service层
 */
public interface UserService {

    /**
     * 获取普通用户数量
     * @return
     */
    Integer getUserCount();

    /**
     * 获取封禁用户数量
     * @return
     */
    Integer getBanUserCount();

    /**
     * 分页获取用户信息
     * @param offset
     * @param limit
     * @return
     */
    List<User> getAllUser(Integer offset, Integer limit);

    /**
     * 获取用户信息
     * @param ticket
     * @return
     */
    Map<String, Object> getUser(String ticket);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param expiredSeconds 时间
     * @return Map集合
     */
    Map<String, Object> login(String username, String password, Integer expiredSeconds);

    /**
     * 退出登录
     * @param ticket 登录凭证
     */
    void logout(String ticket);

    /**
     * 查询登录凭证
     * @param ticket
     * @return
     */
    LoginTicket findLoginTicket(String ticket);

    /**
     * 通过id获取用户信息
     * @param userId
     * @return
     */
    User getUserById(Integer userId);


    /**
     * 修改用户信息
     * @param username 用户名
     * @param password 密码
     * @param headerImg 头像路径
     */
    void updateUser(String username, String password, String headerImg);



    Collection<? extends GrantedAuthority> getAuthorities(Integer userId);


    /**
     * 查询指定username的User对象
     * @param username 用户名
     * @return
     */
    Boolean isUserByUsername(String username);

    /**
     * 修改用户密码
     * @param password 密码
     */
    void updatePassword(String password);


    /**
     * 判断指定用户名是否存在
     * @param username
     * @return
     */
    Boolean isUserExist(String username);

    /**
     * 判断指定邮箱是否存在
     * @param email
     * @return
     */
    Boolean isEmailExist(String email);

    /**
     * 添加用户
     * @param username 用户名
     * @param email 邮箱
     * @param password 密码
     */
    void addUser(String username, String email, String password);

    /**
     * 上传头像到腾讯云
     * @param headerImg
     * @param filename
     * @param suffix
     */
    void uploadHeaderToQCloud(MultipartFile headerImg, String filename, String suffix);

    /**
     * 移出用户信息
     * @param list
     */
    void removeUser(List<String> list);


    /**
     * 通过关键字查询用户信息
     * @param keyword 关键字
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return
     */
    List<User> getUserSearch(String keyword, String type, Integer offset, Integer limit);

    /**
     * 查过关键字询用户数量
     * @param keyword
     * @return
     */
    Integer getUserCountSearch(String keyword, String type);

    /**
     * 查询管理员数量
     * @return
     */
    Integer getUserCountAdmin(String type);

    /**
     * 查询管理员信息
     * @param offset
     * @param limit
     * @return
     */
    List<User> getAllUserAdmin(String type, Integer offset, Integer limit);

    /**
     * 封禁-解封用户
     * @param list 用户id?status集合
     */
    void banUser(List<String> list);

    /**
     * 获取封禁用户
     * @param offset
     * @param limit
     * @return
     */
    List<User> getAllUserBan(Integer offset, Integer limit);
}
