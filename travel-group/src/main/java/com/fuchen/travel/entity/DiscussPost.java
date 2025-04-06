package com.fuchen.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import java.util.Date;

/**
 * @author Fu chen
 * @date 2022/11/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiscussPost {
	@Id
	private Integer id;

	private int userId;

	private String title;

	private String content;

	private int type;

	private int status;

	private Date createTime;

	private int commentCount;

	private Double score;
}
