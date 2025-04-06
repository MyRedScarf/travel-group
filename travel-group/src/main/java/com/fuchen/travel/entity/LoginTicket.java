package com.fuchen.travel.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Fu chen
 * @date 2022/12/4
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
