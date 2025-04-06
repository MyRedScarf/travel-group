package com.fuchen.travel.service;

import com.fuchen.travel.entity.LoginTicket;
import com.fuchen.travel.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Map;

/**
 * @author Fu chen
 * @date 2022/12/30
 * 用户-service层
 */
public interface UserService {
	/**
	 * 通过用户id查询用户信息
	 * @param id 用户id
	 * @return User对象
	 */
	User findById(Integer id);
	
	/**
	 * 用户注册
	 * @param user 接受一个User对象
	 * @return 返回一个map集合
	 */
	Map<String , Object> register(User user);
	
	/**
	 * 激活
	 * @param userId 用户id
	 * @param code 激活码
	 * @return 激活状态码
	 */
	Integer activation(Integer userId, String code);
	
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
	 * @param ticket 凭证
	 * @return 登录凭证
	 */
	LoginTicket findLoginTicket(String ticket);
	
	/**
	 * 修改header
	 * @param userId 用户id
	 * @param header 头像路径
	 */
	void updateHeader(Integer userId, String header);
	
	/**
	 * 通过用户名查询用户信息
	 * @param username 用户名
	 * @return 用户信息
	 */
	User findUserByName(String username);


	/**
	 * 发送邮箱验证码
	 * @param email 邮箱
	 */
	void verificationCode(String email);


	/**
	 * 根据邮箱修改密码
	 * @param email 邮箱
	 * @param password 需要修改的密码
	 * @return User对象
	 */
	User updatePassword(String email, String password);

	/**
	 * 根据userId修改密码
	 * @param user 用户
	 */
	void updatePasswordByUserId(User user);

	/**
	 * 集合..
	 * @param userId 用户id
	 * @return 集合
	 */
	Collection<? extends GrantedAuthority> getAuthorities(Integer userId);

	/**
	 * 上传头像到腾讯云
	 * @param headerImg 头像文件
	 * @param filename 文件名
	 * @param suffix 文件名后缀
	 */
    void uploadHeaderToQCloud(MultipartFile headerImg, String filename, String suffix);
}
