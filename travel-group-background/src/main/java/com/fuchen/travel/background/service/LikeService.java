package com.fuchen.travel.background.service;

/**
 * @author 伏辰
 * @date 2023/1/11
 * 赞-service层
 */
public interface LikeService {

    /**
     * 某实体点赞数量
     * @param entityType 点赞的实体
     * @param entityId 实体的id
     * @return 赞数量
     */
    long findEntityLikeCount(Integer entityType, Integer entityId);
}
