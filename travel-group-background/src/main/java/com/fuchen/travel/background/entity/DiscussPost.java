package com.fuchen.travel.background.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author 伏辰
 * @date 2023/1/11
 * 帖子-实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DiscussPost {
    /**
     * 帖子id
     */
    private Integer id;

    /**
     * 用户id（发布者id）
     */
    private String userId;

    /**
     * 帖子主题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 帖子类型（分为管理员发布和普通用户发布）
     */
    private Integer type;

    /**
     * 帖子状态（正常状态，热点，撤销）
     */
    private Integer status;

    /**
     * 帖子发布时间
     */
    private Date createTime;

    /**
     * 帖子评论数量
     */
    private Integer commentCount;

    /**
     * 帖子分数（用于给贴子增加热点）
     */
    private Double score;

}
