package com.fuchen.travel.background.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author 伏辰
 * @Date 2023/1/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    /**
     * 用户id
     */
    private Integer id;


    private Integer number;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码加密盐值
     */
    private String salt;

    /**
     * 注册时提供的邮箱
     */
    private String email;


    /**
     * 类型
     */
    private Integer type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 激活码
     */
    private String activationCode;

    /**
     * 头像路径
     */
    private String headerUrl;

    /**
     * 上次操作时间
     */
    private Date createTime;
}
