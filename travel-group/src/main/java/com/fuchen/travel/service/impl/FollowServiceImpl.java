package com.fuchen.travel.service.impl;

import com.fuchen.travel.entity.User;
import com.fuchen.travel.service.FollowService;
import com.fuchen.travel.service.UserService;
import com.fuchen.travel.util.TravelConstant;
import com.fuchen.travel.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Fu chen
 * @date 2022/12/18
 * 关注-service层-实现类
 */
@Service
public class FollowServiceImpl implements FollowService, TravelConstant {
	
	private final RedisTemplate redisTemplate;
	
	private final UserService userService;

	public FollowServiceImpl(RedisTemplate redisTemplate, UserService userService) {
		this.redisTemplate = redisTemplate;
		this.userService = userService;
	}

	/**
	 * 关注
	 * @param userId 用户id
	 * @param entityType 实体类型
	 * @param entityId 实体id
	 */
	@Override
	public void follow(Integer userId, Integer entityType, Integer entityId) {
		redisTemplate.execute(new SessionCallback() {
			@Override
			public Object execute(RedisOperations redisOperations) throws DataAccessException {
				String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
				String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
				
				redisOperations.multi();
				
				redisOperations.opsForZSet().add(followeeKey, entityId, System.currentTimeMillis());
				redisOperations.opsForZSet().add(followerKey, userId, System.currentTimeMillis());
				return redisOperations.exec();
			}
		});
	}

	/**
	 * 取消关注
	 * @param userId 用户id
	 * @param entityType 实体类型
	 * @param entityId 实体id
	 */
	@Override
	public void unfollow(Integer userId, Integer entityType, Integer entityId) {
		redisTemplate.execute(new SessionCallback() {
			@Override
			public Object execute(RedisOperations redisOperations) throws DataAccessException {
				String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
				String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
				
				redisOperations.multi();
				
				redisOperations.opsForZSet().remove(followeeKey, entityId);
				redisOperations.opsForZSet().remove(followerKey, userId);
				return redisOperations.exec();
			}
		});
	}

	/**
	 * 查询关注的实体数量
	 * @param userId 用户id
	 * @param entityType 实体
	 * @return 返关注的实体类数量
	 */
	@Override
	public long findFolloweeCount(Integer userId, Integer entityType) {
		String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
		return redisTemplate.opsForZSet().zCard(followeeKey);
	}

	/**
	 * 查询实体的粉丝数量
	 * @param entityType 实体类型
	 * @param entityId 实体id
	 * @return 返回粉丝数量
	 */
	@Override
	public long findFollowerCount(Integer entityType, Integer entityId) {
		String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
		return redisTemplate.opsForZSet().zCard(followerKey);
	}

	/**
	 * 查询当前用户的关注状态
	 * @param userId 用户id
	 * @param entityType 实体类型
	 * @param entityId 实体id
	 * @return 返回用户是否关注了实体
	 */
	@Override
	public Boolean hasFollowed(Integer userId, Integer entityType, Integer entityId) {
		String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
		if (redisTemplate.opsForZSet().score(followeeKey, entityId) != null) {
			return true;
		}
		return false;
	}

	/**
	 * 查询某用户关注的人
	 * @param userId 某用户的id
	 * @param offset 起始行
	 * @param limit 显示条数
	 * @return 返回用户关注的其他用户集合
	 */
	@Override
	public List<Map<String, Object>> findFollowees(Integer userId, Integer offset, Integer limit) {
		String followeeKey = RedisKeyUtil.getFolloweeKey(userId, ENTITY_TYPE_USER);
		Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followeeKey, offset, offset + limit - 1);
		
		if (targetIds == null && targetIds.size() ==  0) {
		    return  null;
		}
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (Integer targetId : targetIds) {
			Map<String, Object> map = new HashMap<>();
			User user = userService.findById(targetId);
			Double score = redisTemplate.opsForZSet().score(followeeKey, targetId);
			map.put("user", user);
			map.put("followTime", new Date(score.longValue()));
			
			list.add(map);
		}
		
		return list;
	}

	/**
	 * 查询某用户的粉丝
	 * @param userId 某用户的id
	 * @param offset 起始行
	 * @param limit 显示条数
	 * @return 返回用户的粉丝集合
	 */
	@Override
	public List<Map<String, Object>> findFollowers(Integer userId, Integer offset, Integer limit) {
		String followerKey = RedisKeyUtil.getFollowerKey(ENTITY_TYPE_USER, userId);
		Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followerKey, offset, offset + limit - 1);
		
		if (targetIds == null && targetIds.size() == 0) {
			return  null;
		}
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (Integer targetId : targetIds) {
			Map<String, Object> map = new HashMap<>();
			User user = userService.findById(targetId);
			Double score = redisTemplate.opsForZSet().score(followerKey, targetId);
			map.put("user", user);
			map.put("followTime", new Date(score.longValue()));
			
			list.add(map);
		}
		
		return list;
	}
}
