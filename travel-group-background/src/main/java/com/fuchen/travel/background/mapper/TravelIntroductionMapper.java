package com.fuchen.travel.background.mapper;

import com.fuchen.travel.background.entity.TravelIntroduction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TravelIntroductionMapper {

    /**
     * 发布攻略
     * @param travelIntroduction 攻略实体信息
     * @return 插入行数
     */
    Integer insertIntroduction(@Param("introduction") TravelIntroduction travelIntroduction);

    /**
     * 查询攻略列表
     * @param offset 分页起始行
     * @param limit 分页显示条数
     * @return 攻略实体集合
     */
    List<TravelIntroduction> selectIntroductionList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 查询攻略数量
     * @return 数量
     */
    Integer selectIntroductionCount();

    /**
     * 查询攻略详情
     * @param id 攻略id
     * @return 攻略实体
     */
    TravelIntroduction selectIntroductionDetails(@Param("id") Integer id);

    /**
     * 删除攻略
     * @param id 攻略id
     * @return 删除条数
     */
    Integer updateById(@Param("id") Integer id);
}
