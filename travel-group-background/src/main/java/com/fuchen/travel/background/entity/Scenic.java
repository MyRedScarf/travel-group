package com.fuchen.travel.background.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author 伏辰
 * @date 2023/1/5
 * 景点-实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Scenic {
    /**
     * 景点id
     */
    private Integer id;

    /**
     * 景点名称
     */
    private String scenicName;

    /**
     * 景点简介
     */
    private String introduce;

    /**
     * 景点说明
     */
    private String content;

    /**
     * 旅游须知
     */
    private String notice;

    /**
     * 景点图片路径
     */
    private String imageUrl;

    /**
     * 景点价格
     */
    private Double price;

    /**
     * 景点经营者
     */
    private String merchant;

    /**
     * 景点联系电话
     */
    private String phone;

    /**
     * 景点地址
     */
    private String address;

    /**
     * 景点创建时间
     */
    private Date createTime;

}
