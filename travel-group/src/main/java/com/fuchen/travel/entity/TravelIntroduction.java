package com.fuchen.travel.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Fu chen
 * @date 2023/4/1
 */
@Data
@ToString
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
