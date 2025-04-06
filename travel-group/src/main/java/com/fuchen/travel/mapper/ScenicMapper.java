package com.fuchen.travel.mapper;


import com.fuchen.travel.entity.Scenic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Fu chen
 * @date 2022/11/26
 * 景点-mapper层
 */
@Mapper
public interface ScenicMapper {

    /**
     * 查询所有景点信息
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景点信息集合
     */
    List<Scenic> selectAllScenic(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 根据id查询景点信息
     * @param id 景点id
     * @return 景点信息
     */
    Scenic selectScenicById(@Param("id") Integer id);

    /**
     * 查询所有景点的个数
     * @return 景点个数
     */
    Integer selectScenicCountAll();


    /**
     * 通过景区名称查询景点信息
     * @param scenicName 景区名称
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景区信息集合
     */
    List<Scenic> selectScenicByScenicName(@Param("scenicName") String scenicName, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 通过关键字查询景点数量
     * @param keyword 关键字
     * @return 景点数量
     */
    Integer selectToScenicCount(@Param("keyword") String keyword);

    /**
     * 查询推荐的景点
     * @return 景点信息集合
     */
    List<Scenic> selectScenicRe();
}
