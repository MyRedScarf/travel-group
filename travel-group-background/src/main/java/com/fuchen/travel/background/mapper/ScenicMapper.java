package com.fuchen.travel.background.mapper;

import com.fuchen.travel.background.entity.Scenic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 伏辰
 * @date 2023/1/5
 * 景点-mapper层
 */
@Mapper
public interface ScenicMapper {

    /**
     * 查询推荐的景点
     * @return 返回推荐的list集合
     */
    List<Scenic> selectRecommendScenic ();

    /**
     * 查询景点总数
     * @return 返回景点总数
     */
    Integer selectScenicCount ();

    /**
     * 分页查询全部景点
     * @param offset 检索起始行
     * @param limit 简述条数
     * @return 返回list集合
     */
    List<Scenic> selectScenic(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 推荐景点
     * @param scenicId 景点id
     */
    void recommend(@Param("id") Integer scenicId);

    /**
     * 移出对剑景点
     * @param scenicId 景点id
     */
    void removeRecommend(@Param("id") Integer scenicId);

    /**
     * 查询指定景点是否存在
     * @param scenicName
     * @return
     */
    Scenic selectScenicExist(@Param("scenicName") String scenicName);

    /**
     * 插入景点信息
     * @param scenic
     */
    void insertScenic(@Param("scenic") Scenic scenic);

    /**
     * 修改景点信息
     * @param scenic
     */
    void updateScenic(@Param("scenic") Scenic scenic);

    /**
     * 修改景点的状态，将其设置为删除
     * @param list
     */
    void updateScenicById(@Param("ids") List<String> list);

    /**
     * 查询推荐景点的数量
     * @return
     */
    Integer selectScenicRecommendCount();

    /**
     * 查询指定关键字的景点数量
     * @param keyword
     * @return
     */
    Integer selectCountByKeyword(@Param("keyword") String keyword);

    /**
     * 查询指定关键字景点
     * @param keyword
     * @param offset
     * @param limit
     * @return
     */
    List<Scenic> selectScenicByKeyword(@Param("keyword") String keyword,
                                       @Param("offset") Integer offset,
                                       @Param("limit") Integer limit);

    List<Scenic> selectScenicList();

    List<Scenic> selectRecommendScenicList();
}
