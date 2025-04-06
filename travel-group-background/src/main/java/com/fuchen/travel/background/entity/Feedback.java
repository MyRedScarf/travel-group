package com.fuchen.travel.background.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Fu chen
 * @date 2023/3/14
 * 意见反馈-实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Feedback {
    /**
     * 意见反馈id
     */
    private Integer id;

    /**
     * 反馈用户id
     */
    private Integer userId;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;
}
