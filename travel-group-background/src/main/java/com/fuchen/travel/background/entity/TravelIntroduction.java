package com.fuchen.travel.background.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TravelIntroduction {
    private Integer id;
    private Integer scenicId;
    private String title;
    private String fontImage;
    private String notice;
    private String content;
    private Integer isDeleted;
    private Date createTime;
}
