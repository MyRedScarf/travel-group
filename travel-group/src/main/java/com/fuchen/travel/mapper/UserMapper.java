package com.fuchen.travel.mapper;

import com.fuchen.travel.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Fu chen
 * @date 2022/10/29
 * 用户-mapper层
 */
@Mapper
public interface UserMapper {
	
	/**
	 * 根据用户id查询User
	 * @param id 用户id
	 * @return User对象
	 */
	User selectById(Integer id);
	
	/**
	 * 根据用户名查询User
	 * @param username 用户名
	 * @return User对象
	 */
	User selectByName(String username);
	
	/**
	 * 根据用户邮箱查询User
	 * @param email 用户邮箱
	 * @return User对象
	 */
	User selectByEmail(String email);
	
	/**
	 * 添加用户
	 * @param user 需要添加的用户信息
	 * @return 添加行数
	 */
	Integer insertUser(User user);
	
	/**
	 * 修改用户状态码
	 * @param id 用户id
	 * @param status 用户的状态码
	 * @return 修改行数
	 */
	Integer updateStatus(@Param("id") Integer id, @Param("status") Integer status);
	
	/**
	 * 修改用户邮箱
	 * @param id 用户id
	 * @param headerUrl 用户头像保存的路径
	 * @return 修改行数
	 */
	Integer updateHeader(@Param("id") Integer id, @Param("headerUrl") String headerUrl);
	
	/**
	 * 修改用户密码
	 * @param id 用户id
	 * @param password 用户需要修改的密码
	 * @return 修改的行数
	 */
	Integer updatePassword(@Param("id") int id, @Param("password") String password);
}
