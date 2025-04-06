package com.fuchen.travel.background.service.impl;

import com.fuchen.travel.background.service.LikeService;
import com.fuchen.travel.background.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 伏辰
 * @date 2023/1/11
 * 赞-service层-实现类
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 某实体点赞数量
     * @param entityType 点赞的实体
     * @param entityId 实体的id
     * @return 赞数量
     */
    @Override
    public long findEntityLikeCount(Integer entityType, Integer entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }
}
