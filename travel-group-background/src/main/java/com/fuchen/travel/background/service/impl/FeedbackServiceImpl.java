package com.fuchen.travel.background.service.impl;

import com.fuchen.travel.background.entity.Feedback;
import com.fuchen.travel.background.mapper.FeedbackMapper;
import com.fuchen.travel.background.service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Fu chen
 * @date 2023/3/14
 * 意见反馈-service层-实现类
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;

    /**
     * 获取意见反馈全部数据的总数量
     * @return 返回总数量
     */
    @Override
    public Integer getAllCount() {
        return feedbackMapper.selectAllCount();
    }

    /**
     * 查找全部的意见反馈信息
     * @param offset 分页起始行
     * @param limit 分页条数
     * @return 返回一个Feedback集合
     */
    @Override
    public List<Feedback> findAllFeedback(Integer offset, Integer limit) {
        return feedbackMapper.selectAllFeedback(offset, limit);
    }
}
