package com.fuchen.travel.service;

import com.fuchen.travel.entity.Scenic;

import java.util.List;

/**
 * @author Fu chen
 * @date 2022/12/28
 * 搜索-service层
 */
public interface SearchService {

    /**
     * 通过景区名称查找景点
     * @param scenicName 景点名称
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景点信息集合
     */
    List<Scenic> findToScenic (String scenicName, Integer offset, Integer limit);

    /**
     * 根据关键字查询景点数量
     * @param keyword 关键词
     * @return 景点数量
     */
    Integer findToScenicCount(String keyword);
}
