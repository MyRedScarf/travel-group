package com.fuchen.travel.background.mapper;

import com.fuchen.travel.background.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Fu chen
 * @date 2023/3/14
 * 意见反馈-mapper层
 */
@Mapper
public interface FeedbackMapper {

    /**
     * 查询全部数据的数量
     * @return 返回总数量
     */
    Integer selectAllCount();

    /**
     * 查询全部意见反馈信息
     * @param offset 分页起始行
     * @param limit 分页显示条数
     * @return 返回Feedback集合
     */
    List<Feedback> selectAllFeedback(@Param("offset") Integer offset, @Param("limit") Integer limit);
}
