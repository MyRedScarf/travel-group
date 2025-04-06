package com.fuchen.travel.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Fu chen
 * @date 2022/12/12
 */
@Data
@ToString
public class Comment {
	private int id;
	private int userId;
	private int entityType;
	private int entityId;
	private int targetId;
	private String content;
	private int status;
	private Date createTime;
}
