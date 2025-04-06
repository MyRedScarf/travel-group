package com.fuchen.travel.background.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 伏辰
 * @date 2022/7/4
 */
@Data
@ToString
public class LoginTicket {
	private Integer id;
	private Integer userId;
	private String ticket;
	private Integer status;
	private Date expired;
}
