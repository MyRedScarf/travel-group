package com.fuchen.travel.service.impl;

import com.fuchen.travel.service.LikeService;
import com.fuchen.travel.util.RedisKeyUtil;
//import jdk.dynalink.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @author Fu chen
 * @date 2022/12/16
 * 点赞-service层-实现类
 */
@Service
public class LikeServiceImpl implements LikeService {
	private final RedisTemplate redisTemplate;

	public LikeServiceImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 点赞
	 * @param userId 谁点的赞
	 * @param entityType 点赞的实体
	 * @param entityId 实体的id
	 * @param entityUserId 实体UserId
	 */
	@Override
	public void like(Integer userId, Integer entityType, Integer entityId, Integer entityUserId) {
		redisTemplate.execute(new SessionCallback() {
			@Override
			public Object execute(RedisOperations redisOperations) throws DataAccessException {
				String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
				String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);
				Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
				redisOperations.multi();
				if (isMember) {
					redisOperations.opsForSet().remove(entityLikeKey, userId);
					redisOperations.opsForValue().decrement(userLikeKey);
				} else {
					redisOperations.opsForSet().add(entityLikeKey, userId);
					redisOperations.opsForValue().increment(userLikeKey);
				}
				return redisOperations.exec();
			}
		});
	}

	/**
	 * 某实体点赞数量
	 * @param entityType 点赞的实体
	 * @param entityId 实体的id
	 * @return 返回点赞数量
	 */
	@Override
	public long findEntityLikeCount(Integer entityType, Integer entityId){
		String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
		return redisTemplate.opsForSet().size(entityLikeKey);
	}

	/**
	 * 查询某人对某实体的点赞状态
	 * @param userId 某人id
	 * @param entityType 某实体
	 * @param entityId 某实体id
	 * @return 返回点赞状态
	 */
	@Override
	public Integer findEntityLikeStatus(Integer userId, Integer entityType, Integer entityId) {
		String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
		return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
	}

	/**
	 * 查询某个用户的赞
	 * @param userId 用户的id
	 * @return 返回某个用户获得到的赞
	 */
	@Override
	public Integer findUserLikeCount(Integer userId) {
		String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
		Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
		if (count == null) {
		    return 0;
		}
		return count;
	}
}
