package com.fuchen.travel.service;


import com.fuchen.travel.entity.Favorite;
import com.fuchen.travel.entity.Scenic;

import java.util.Date;
import java.util.List;

/**
 * @author Fu chen
 * @date 2022/12/4
 * 收藏-service层
 */
public interface FavoriteService {

    /**
     * 添加收藏
     * @param userId 用户id
     * @param scenicId 景点id
     * @param createTime 创建时间
     */
    void addCollection(Integer userId, Integer scenicId, Date createTime);

    /**
     * 查询用户收藏指定的景点
     * @param userId 用户id
     * @param scenicId 景点id
     * @return 景点信息
     */
    Favorite findByUserIdAndScenicId(Integer userId, Integer scenicId);

    /**
     * 取消收藏
     * @param userId 用户id
     * @param scenicId 景点id
     */
    void removeCollection(Integer userId, Integer scenicId);

    /**
     * 获取收藏次数
     * @param scenicId 景点id
     * @return 收藏次数
     */
    Integer getCollectionCount(Integer scenicId);

    /**
     * 查询收藏次数大的四个收藏信息
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景点列表
     */
    List<Scenic> findCollectionByCount(Integer offset, Integer limit);

    /**
     * 查询用户的收藏景点信息
     * @param userId 用户id
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景点列表
     */
    List<Scenic> findCollectionAllByUserId(Integer userId, Integer offset, Integer limit);

    /**
     * 查询用户收藏景点的个数
     * @param userId 用户id
     * @return 返回用户收藏景点的个数
     */
    Integer findCollectionCountByUserId(Integer userId);

    /**
     * 查询所有用户的收藏景点的个数
     * @return 返回景点被收藏的总次数
     */
    Integer findCollectionCountAll();
}
