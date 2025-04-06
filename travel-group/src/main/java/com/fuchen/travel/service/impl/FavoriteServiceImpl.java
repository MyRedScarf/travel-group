package com.fuchen.travel.service.impl;

import com.fuchen.travel.entity.Favorite;
import com.fuchen.travel.entity.Scenic;
import com.fuchen.travel.mapper.FavoriteMapper;
import com.fuchen.travel.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Fu chen
 * @date 2022/12/4
 * 收藏-service层-实现类
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public FavoriteServiceImpl(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    /**
     * 添加收藏
     * @param userId 用户id
     * @param scenicId 景点id
     * @param createTime 创建时间
     */
    @Override
    public void addCollection(Integer userId, Integer scenicId, Date createTime) {
        favoriteMapper.insertCollection(userId, scenicId, createTime);
    }

    /**
     * 查询用户收藏指定的景点
     * @param userId 用户id
     * @param scenicId 景点id
     * @return 景点信息
     */
    @Override
    public Favorite findByUserIdAndScenicId(Integer userId, Integer scenicId) {
        return favoriteMapper.selectByUserIdAndScenicId(userId, scenicId);
    }

    /**
     * 取消收藏
     * @param userId 用户id
     * @param scenicId 景点id
     */
    @Override
    public void removeCollection(Integer userId, Integer scenicId) {
        favoriteMapper.deleteCollection(userId, scenicId);
    }

    /**
     * 获取收藏次数
     * @param scenicId 景点id
     * @return 收藏次数
     */
    @Override
    public Integer getCollectionCount(Integer scenicId) {
        return favoriteMapper.selectCollectionCount(scenicId);
    }

    /**
     * 查询收藏次数大的四个收藏信息
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景点列表
     */
    @Override
    public List<Scenic> findCollectionByCount(Integer offset, Integer limit) {
        return favoriteMapper.selectCollectionByCount(offset, limit);
    }

    /**
     * 查询用户的收藏景点信息
     * @param userId 用户id
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景点列表
     */
    @Override
    public List<Scenic> findCollectionAllByUserId(Integer userId, Integer offset, Integer limit) {
        return favoriteMapper.selectCollectionAllByUserId(userId, offset, limit);
    }

    /**
     * 查询用户收藏景点的个数
     * @param userId 用户id
     * @return 返回用户收藏景点的个数
     */
    @Override
    public Integer findCollectionCountByUserId(Integer userId) {
        return favoriteMapper.selectCollectionCountByUserId(userId);
    }

    /**
     * 查询所有用户的收藏景点的个数
     * @return 返回景点被收藏的总次数
     */
    @Override
    public Integer findCollectionCountAll() {
        return favoriteMapper.selectCollectionCountAll();
    }
}
