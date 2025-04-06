package com.fuchen.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fu chen
 * @date 2023/2/28
 * 意见反馈-实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    /**
     * 意见反馈id
     */
    int id;

    /**
     * 用户id
     */
    int userId;

    /**
     * 反馈内容
     */
    String content;

}
