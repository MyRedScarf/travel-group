package com.fuchen.travel.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Fu chen
 * @date 2023/3/9
 * 意见反馈-mapper层
 */
@Mapper
public interface FeedbackMapper {

    /**
     * 添加意见反馈信息
     * @param userId 用户id
     * @param content 反馈内容
     * @return 数据修改条数
     */
    int insertFeedback(@Param("userId") Integer userId, @Param("content") String content);
}
