package com.fuchen.travel.service;

import com.fuchen.travel.entity.Scenic;

import java.util.List;

/**
 * @author Fu chen
 * @date 2022/11/27
 * 景点-service层
 */
public interface ScenicService {

    /**
     * 查找景点信息
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景点列表
     */
    List<Scenic> findAllScenic(Integer offset, Integer limit);

    /**
     * 根据景点id查询景点信息
     * @param id 景点id
     * @return 景点信息
     */
    Scenic findScenicById(Integer id);

    /**
     * 查询景点个数
     * @return 返回景点个数
     */
    Integer findScenicCountAll();

    /**
     * 获取推荐的景点
     * @return 返回推荐的景点集合
     */
    List<Scenic> getScenicRe();
}
