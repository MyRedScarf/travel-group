package com.fuchen.travel.service.impl;

import com.fuchen.travel.entity.TravelIntroduction;
import com.fuchen.travel.mapper.TravelNavigationMapper;
import com.fuchen.travel.service.TravelNavigationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TravelNavigationServiceImpl implements TravelNavigationService {

    @Resource
    private TravelNavigationMapper travelNavigationMapper;

    /**
     * 获取拼团导航列表
     * @return 列表
     */
    @Override
    public List<TravelIntroduction> getTravelNavigationList(Integer offset, Integer limit) {
        return travelNavigationMapper.selectTravelNavigationList(offset, limit);
    }

    /**
     * 获取拼团攻略总数
     * @return 总数
     */
    @Override
    public Integer getTravelNavigationCount() {
        return travelNavigationMapper.selectTravelNavigationCount();
    }

    /**
     * 获取攻略详情
     * @param introductionId 攻略id
     * @return 攻略
     */
    @Override
    public TravelIntroduction getTravelIntroduction(String introductionId) {
        return travelNavigationMapper.selectTravelIntroduciton(introductionId);
    }
}
