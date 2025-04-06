package com.fuchen.travel.service;

/**
 * @author Fu chen
 * @date 2023/3/9
 * 意见反馈-service层
 */
public interface FeedbackService {

    /**
     * 意见反馈
     * @param userId 用户id
     * @param content 反馈内容
     */
    void addFeedback(Integer userId, String content);
}
