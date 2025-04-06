package com.fuchen.travel.mapper;

import com.fuchen.travel.entity.TravelIntroduction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Fu chen
 * @date 2023/4/1
 */
@Mapper
public interface TravelNavigationMapper {

    /**
     * 查询拼团攻略列表
     * @return 列表
     */
    List<TravelIntroduction> selectTravelNavigationList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取拼团攻略总数
     * @return
     */
    Integer selectTravelNavigationCount();

    /**
     * 获取攻略详情
     * @param introductionId 攻略id
     * @return 攻略
     */
    TravelIntroduction selectTravelIntroduciton(@Param("introductionId") String introductionId);
}
