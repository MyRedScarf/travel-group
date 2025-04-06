package com.fuchen.travel.controller;

import com.fuchen.travel.entity.Comment;
import com.fuchen.travel.entity.DiscussPost;
import com.fuchen.travel.entity.Event;
import com.fuchen.travel.entity.User;
import com.fuchen.travel.event.EventProducer;
import com.fuchen.travel.service.CommentService;
import com.fuchen.travel.service.DiscussPostService;
import com.fuchen.travel.util.TravelConstant;
import com.fuchen.travel.util.HostHolder;
import com.fuchen.travel.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Fu chen
 * @date 2022/12/13
 * 评论-controller层
 */
@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController implements TravelConstant {

	@Resource
	private CommentService commentService;

	@Resource
	private DiscussPostService discussPostService;

	@Resource
	private HostHolder hostHolder;

	@Resource
	private EventProducer eventProducer;

	@Resource
	private RedisTemplate redisTemplate;

	/**
	 * 添加帖子评论
	 * @param discussPostId 帖子id
	 * @param comment 评论内容
	 * @return 重定向到当前帖子页面
	 */
	@PostMapping("/add/{discussPostId}")
	public String addComment(@PathVariable("discussPostId")int discussPostId, Comment comment, DiscussPost discussPost){

		if (comment == null) {
			throw new IllegalArgumentException("帖子为空");
		}

		if (discussPost == null) {
			throw new IllegalArgumentException("无帖子回复数量");
		}
		User user = hostHolder.getUser();

		comment.setUserId(user.getId());
		comment.setStatus(0);
		comment.setCreateTime(new Date());
		commentService.addComment(comment);
		
		//触发评论事件
		Event event = new Event()
				.setTopic(TOPIC_COMMENT)
				.setUserId(hostHolder.getUser().getId())
				.setEntityType(comment.getEntityType())
				.setEntityId(comment.getEntityId())
				.setData("postId", discussPostId);
		
		if (comment.getEntityType() == ENTITY_TYPE_POST) {
			//评论
			DiscussPost target = discussPostService.findDiscussPost(comment.getEntityId());
			event.setEntityUserId(target.getUserId());
			eventProducer.fireEvent(event);
			discussPostService.updateCommentCount(discussPostId, discussPost.getCommentCount() + 1);
			log.info(discussPost.toString());
		} else if (comment.getEntityType() == ENTITY_TYPE_COMMENT){
			//回复
			Comment target = commentService.findCommentById(comment.getEntityId());
			event.setEntityUserId(target.getUserId());
			eventProducer.fireEvent(event);
		}
		//触发帖子事件
		if (comment.getEntityType() == ENTITY_TYPE_POST) {
			event = new Event()
					.setTopic(TOPIC_PUBLISH)
					.setUserId(comment.getUserId())
					.setEntityType(ENTITY_TYPE_POST)
					.setEntityId(discussPostId);
			eventProducer.fireEvent(event);
			
			String redisKey = RedisKeyUtil.getPostScoreKey();
			redisTemplate.opsForSet().add(redisKey, discussPostId);
		}
		
		return "redirect:/discuss/detail/" + discussPostId;
	}

	@PostMapping("/addScenicComment")
	public String addScenicComment(Comment comment){
		if (!ObjectUtils.isEmpty(comment)) {
			comment.setUserId(hostHolder.getUser().getId());
			comment.setCreateTime(new Date());
			commentService.addComment(comment);
			return "redirect:/scenic-detail/" + comment.getEntityId();
		}
		return "/index";
	}
	
}
