package com.fuchen.travel.service;

import com.mysql.cj.log.Log;

import java.util.List;
import java.util.Map;

/**
 * @author Fu chen
 * @date 2022/12/18
 * 关注-service层
 */
public interface FollowService {
	
	/**
	 * 关注
	 * @param userId 用户id
	 * @param entityType 实体类型
	 * @param entityId 实体id
	 */
	void follow(Integer userId, Integer entityType, Integer entityId);
	
	/**
	 * 取消关注
	 * @param userId 用户id
	 * @param entityType 实体类型
	 * @param entityId 实体id
	 */
	void unfollow(Integer userId, Integer entityType, Integer entityId);
	
	/**
	 * 查询关注的实体数量
	 * @param userId 用户id
	 * @param entityType 实体
	 * @return 返关注的实体类数量
	 */
	long findFolloweeCount(Integer userId, Integer entityType);
	
	/**
	 * 查询实体的粉丝数量
	 * @param entityType 实体类型
	 * @param entityId 实体id
	 * @return 返回粉丝数量
	 */
	long findFollowerCount(Integer entityType, Integer entityId);
	
	/**
	 * 查询当前用户的关注状态
	 * @param userId 用户id
	 * @param entityType 实体类型
	 * @param entityId 实体id
	 * @return 返回用户是否关注了实体
	 */
	Boolean hasFollowed(Integer userId, Integer entityType, Integer entityId);
	
	/**
	 * 查询某用户关注的人
	 * @param userId 某用户的id
	 * @param offset 起始行
	 * @param limit 显示条数
	 * @return 返回用户关注的其他用户集合
	 */
	List<Map<String, Object>> findFollowees (Integer userId, Integer offset, Integer limit);
	
	/**
	 * 查询某用户的粉丝
	 * @param userId 某用户的id
	 * @param offset 起始行
	 * @param limit 显示条数
	 * @return 返回用户的粉丝集合
	 */
	List<Map<String, Object>> findFollowers (Integer userId, Integer offset, Integer limit);
	
}
