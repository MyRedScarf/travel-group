package com.fuchen.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Fu chen
 * @date 2022/12/4
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Favorite {
    private Integer id;
    private Integer userId;
    private Integer scenicId;
    private Date createTime;
}
