package com.fuchen.travel.service.impl;

import com.fuchen.travel.mapper.FeedbackMapper;
import com.fuchen.travel.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Fu chen
 * @date 2023/3/9
 * 意见反馈-service层-实现类
 */
@Slf4j
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;
    /**
     * 意见反馈
     * @param userId 用户id
     * @param content 反馈内容
     */
    @Override
    public void addFeedback(Integer userId, String content) {
        int row = feedbackMapper.insertFeedback(userId, content);
        if (row > 0) {
            log.info("意见保存成功!");
        } else {
            log.info("意见保存失败!");
        }
    }
}
