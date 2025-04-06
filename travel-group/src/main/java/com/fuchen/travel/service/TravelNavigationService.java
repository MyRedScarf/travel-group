package com.fuchen.travel.service;


import com.fuchen.travel.entity.TravelIntroduction;

import java.util.List;

public interface TravelNavigationService {

    /**
     * 获取拼团攻略列表
     * @return 列表
     */
    List<TravelIntroduction> getTravelNavigationList(Integer offset, Integer limit);

    /**
     * 获取拼团攻略总数
     * @return 总数
     */
    Integer getTravelNavigationCount();

    /**
     * 获取攻略详情
     * @param introductionId 攻略id
     * @return 攻略
     */
    TravelIntroduction getTravelIntroduction(String introductionId);
}
