package com.fuchen.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

/**
 * @author Fu chen
 * @date 2022/11/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Scenic {

    private Integer id;

    private String scenicName;

    private String introduce;

    private String content;

    private String imageUrl;

    private String notice;

    private Double price;

    private String merchant;

    private String phone;

    private String address;

    private Date createTime;

    private Integer status;

}
