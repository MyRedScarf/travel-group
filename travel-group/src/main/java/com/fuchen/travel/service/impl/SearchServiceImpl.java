package com.fuchen.travel.service.impl;

import com.fuchen.travel.entity.Scenic;
import com.fuchen.travel.mapper.ScenicMapper;
import com.fuchen.travel.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fu chen
 * @date 2022/12/28
 * 搜索-service层-实现类
 */
@Service
public class SearchServiceImpl implements SearchService {

    private final ScenicMapper scenicMapper;

    public SearchServiceImpl(ScenicMapper scenicMapper) {
        this.scenicMapper = scenicMapper;
    }

    /**
     * 通过景区名称查找景点
     * @param scenicName 景点名称
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景点信息集合
     */
    @Override
    public List<Scenic> findToScenic(String scenicName, Integer offset, Integer limit) {
        //调用持久层根据名称查询景区信息的集合
        return scenicMapper.selectScenicByScenicName(scenicName, offset, limit);
    }

    /**
     * 根据关键字查询景点数量
     * @param keyword 关键词
     * @return 景点数量
     */
    @Override
    public Integer findToScenicCount(String keyword) {
        return scenicMapper.selectToScenicCount(keyword);
    }
}
