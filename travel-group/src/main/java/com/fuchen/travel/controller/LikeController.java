package com.fuchen.travel.controller;

import com.fuchen.travel.entity.Event;
import com.fuchen.travel.entity.User;
import com.fuchen.travel.event.EventProducer;
import com.fuchen.travel.service.LikeService;
import com.fuchen.travel.util.TravelConstant;
import com.fuchen.travel.util.TravelUtil;
import com.fuchen.travel.util.HostHolder;
import com.fuchen.travel.util.RedisKeyUtil;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author Fu chen
 * @date 2022/12/17
 * 点赞-controller层
 */
@Controller
public class LikeController implements TravelConstant {
	private final LikeService likeService;
	
	private final HostHolder hostHolder;
	
	private final EventProducer eventProducer;
	
	private final RedisTemplate redisTemplate;

	public LikeController(LikeService likeService, HostHolder hostHolder, EventProducer eventProducer, RedisTemplate redisTemplate) {
		this.likeService = likeService;
		this.hostHolder = hostHolder;
		this.eventProducer = eventProducer;
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 点赞
	 * @param entityType 实体类型
	 * @param entityId 实体di
	 * @param entityUserId 实体用户id
	 * @param postId 帖子id
	 * @return json，返回点赞信息
	 */
	@PostMapping("/like")
	@ResponseBody
	public String like (Integer entityType, Integer entityId, Integer entityUserId, Integer postId) {
		User user = hostHolder.getUser();
		//点赞
		likeService.like(user.getId(), entityType, entityId, entityUserId);
		//点赞数量
		long likeCount = likeService.findEntityLikeCount(entityType, entityId);
		//状态
		Integer status = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);
		HashMap<String, Object> map = new HashMap<>();
		map.put("likeCount", likeCount);
		map.put("likeStatus", status);
		
		//触发点赞事件
		if (status == 1) {
			Event event = new Event()
					.setTopic(TOPIC_LIKE)
					.setUserId(hostHolder.getUser().getId())
					.setEntityType(entityType)
					.setEntityId(entityId)
					.setEntityUserId(entityUserId)
					.setData("postId", postId);
			eventProducer.fireEvent(event);
		}
		
		if (entityType == ENTITY_TYPE_POST) {
			//计算帖子分数
			String redisKey = RedisKeyUtil.getPostScoreKey();

			redisTemplate.opsForSet().add(redisKey, postId);
		}
		
		return TravelUtil.getJsonString(0, "null", map);
	}
}
