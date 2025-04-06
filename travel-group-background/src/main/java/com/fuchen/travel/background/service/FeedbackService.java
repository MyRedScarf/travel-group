package com.fuchen.travel.background.service;

import com.fuchen.travel.background.entity.Feedback;

import java.util.List;

/**
 * @author Fu chen
 * @date 2023/3/14
 * 意见反馈-service层
 */
public interface FeedbackService {

    /**
     * 获取意见反馈全部数据的总数量
     * @return 返回总数量
     */
    Integer getAllCount();

    /**
     * 查找全部的意见反馈信息
     * @param offset 分页起始行
     * @param limit 分页条数
     * @return 返回一个Feedback集合
     */
    List<Feedback> findAllFeedback(Integer offset, Integer limit);
}
