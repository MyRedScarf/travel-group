package com.fuchen.travel.background.mapper;

import com.fuchen.travel.background.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author 伏辰
 * @Date 2023/1/5
 * 用户-mapper层
 */
@Mapper
public interface UserMapper {

    /**
     * 查询用户数量，不包括管理员
     * @return
     */
    Integer selectUserCount();

    /**
     * 查询全部用户数量
     * @return
     */
    Integer selectUserCountAll();

    /**
     * 查询封禁用户数量
     * @return
     */
    Integer selectBanUserCount();

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return User对象
     */
    User selectByName(@Param("username") String username);

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return
     */
    User selectUserById(@Param("userId") Integer userId);

    /**
     * 分页查询用户信息
     * @param offset 起始行
     * @param limit 查询条数
     * @return
     */
    List<User> selectAllUser(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("type") Integer type);

    /**
     * 更改用户数据
     * @param username 用户名
     * @param password 密码
     * @param headerImg 头像路径
     */
    void updateUser(@Param("id") Integer id, @Param("username") String username, @Param("password") String password,
                    @Param("headerUrl") String headerImg);

    /**
     * 查询当前用户是否存在
     * @param username 用户名
     * @return
     */
    Integer userByUsernameExist(@Param("username") String username);

    /**
     * 根据用户id，修改用户密码
     * @param id 用户id
     * @param password 密码
     */
    void updatePasswordById(@Param("id") Integer id, @Param("password")String password);

    /**
     * 修改用户头像
     * @param id 用户id
     * @param headerUrl 头像路径
     */
    void uploadHeader(@Param("id") Integer id, @Param("headerUrl") String headerUrl);

    /**
     * 查询指定用户名是否存在
     * @return
     */
    Integer selectUsernameExist(@Param("username") String username);

    /**
     * 查询指定邮箱是否存在
     * @param email
     * @return
     */
    Integer selectEmailExist(String email);

    /**
     * 插入用户信息
     * @param username
     * @param email
     * @param password
     * @param salt
     * @param headerUrl
     * @param createTime
     */
    void insertUser(@Param("username") String username, @Param("email") String email,
                    @Param("password") String password, @Param("salt") String salt,
                    @Param("headerUrl") String headerUrl, @Param("createTime") Date createTime);


    /**
     * 删除用户信息
     * @param ids 用户的id集合
     */
    void deleteUserById(@Param("ids") List<String> ids);

    /**
     * 通过关键字查询用户
     * @param keyword
     * @return
     */
    List<User> selectUserByKeyword(@Param("keyword") String keyword, @Param("type") String type,
                                   @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 模糊查询用户数量
     * @param keyword
     * @return
     */
    Integer selectUserCountByKeyword(@Param("keyword")String keyword, @Param("type") String type);

    /**
     * 查询管理员数量
     * @return
     */
    Integer selectUserCountAdmin(@Param("type") String type);

    /**
     * 查询管理员信息
     * @param offset
     * @param limit
     * @return
     */
    List<User> selectAllUserAdmin(@Param("type") String type, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 修改用户状态为封禁
     * @param ids 用户id集合
     */
    void updateUserToBanStatus(@Param("ids") List<String> ids);

    /**
     * 查询封禁用户
     * @param offset
     * @param limit
     * @return
     */
    List<User> selectUserBan(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 修改用户状态为正常
     * @param ids 用户id集合
     */
    void updateUserToNoBanStatus(@Param("ids")List<String> ids);
}
