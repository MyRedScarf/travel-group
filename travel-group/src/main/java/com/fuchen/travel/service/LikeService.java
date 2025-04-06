package com.fuchen.travel.service;

/**
 * @author Fu chen
 * @date 2022/12/16
 * 点赞-service层
 */
public interface LikeService {
	
	/**
	 * 点赞
	 * @param userId 谁点的赞
	 * @param entityType 点赞的实体
	 * @param entityId 实体的id
	 * @param entityUserId 实体UserId
	 */
	void like(Integer userId, Integer entityType, Integer entityId, Integer entityUserId);
	
	/**
	 * 某实体点赞数量
	 * @param entityType 点赞的实体
	 * @param entityId 实体的id
	 * @return 返回点赞数量
	 */
	long findEntityLikeCount(Integer entityType, Integer entityId);
	
	/**
	 * 查询某人对某实体的点赞状态
	 * @param userId 某人id
	 * @param entityType 某实体
	 * @param entityId 某实体id
	 * @return 返回点赞状态
	 */
	Integer findEntityLikeStatus(Integer userId, Integer entityType, Integer entityId);
	
	/**
	 * 查询某个用户的赞
	 * @param userId 用户的id
	 * @return 返回某个用户获得到的赞
	 */
	Integer findUserLikeCount(Integer userId);
	
	
}
