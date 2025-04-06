package com.fuchen.travel.mapper;

import com.fuchen.travel.entity.Favorite;
import com.fuchen.travel.entity.Scenic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Fu chen
 * @date 2022/12/4
 * 收藏景点-mapper层
 */
@Mapper
public interface FavoriteMapper {

    /**
     * 用户收藏景点
     * @param userId 用户id
     * @param scenicId 景点id
     * @param createTime 收藏时间
     * @return 插入条数
     */
    Integer insertCollection(@Param("userId") Integer userId, @Param("scenicId") Integer scenicId, @Param("createTime")Date createTime);


    /**
     * 查询用户指定的景点
     * @param userId 用户id
     * @param scenicId 景点id
     * @return 景点信息
     */
    Favorite selectByUserIdAndScenicId(@Param("userId") Integer userId, @Param("scenicId") Integer scenicId);

    /**
     * 删除用户指定景点收藏信息
     * @param userId 用户id
     * @param scenicId 景点id
     * @return 删除信息
     */
    Integer deleteCollection(@Param("userId") Integer userId, @Param("scenicId") Integer scenicId);

    /**
     * 查询景点收藏次数
     * @param scenicId 景点id
     * @return 景点的收藏次数
     */
    Integer selectCollectionCount(@Param("scenicId") Integer scenicId);

    /**
     * 查询收藏次数大的四个收藏信息
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景点集合
     */
    List<Scenic> selectCollectionByCount(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 查询用户的收藏景点信息
     * @param userId 用户id
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景点集合
     */
    List<Scenic> selectCollectionAllByUserId(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 查询用户收藏景点的个数
     * @param userId 用户id
     * @return 用户收藏景点的个数
     */
    Integer selectCollectionCountByUserId(@Param("userId") Integer userId);

    /**
     * 查询所有用户收藏景点个数
     * @return 返回所有用户收藏景点的个数
     */
    Integer selectCollectionCountAll();
}
