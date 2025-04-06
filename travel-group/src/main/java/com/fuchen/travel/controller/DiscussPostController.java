package com.fuchen.travel.controller;

import com.fuchen.travel.entity.*;
import com.fuchen.travel.event.EventProducer;
import com.fuchen.travel.service.*;
import com.fuchen.travel.util.TravelConstant;
import com.fuchen.travel.util.TravelUtil;
import com.fuchen.travel.util.HostHolder;
import com.fuchen.travel.util.RedisKeyUtil;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Fu chen
 * @date 2022/12/7
 * 帖子-controller层
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements TravelConstant {
	private final DiscussPostService discussPostService;
	
	private final UserService userService;
	
	private final CommentService commentService;
	
	private final LikeService likeService;
	
	private final HostHolder hostHolder;
	
	private final EventProducer eventProducer;


	private final RedisTemplate redisTemplate;

	public DiscussPostController(DiscussPostService discussPostService, UserService userService, CommentService commentService, LikeService likeService, HostHolder hostHolder, EventProducer eventProducer, RedisTemplate redisTemplate) {
		this.discussPostService = discussPostService;
		this.userService = userService;
		this.commentService = commentService;
		this.likeService = likeService;
		this.hostHolder = hostHolder;
		this.eventProducer = eventProducer;
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 发布
	 * @param title 主题
	 * @param content 正文
	 * @return 发布结果
	 */
	@PostMapping("/add")
	@ResponseBody
	public String addDiscussPost(String title, String content) {
		User user = hostHolder.getUser();
		if (user == null){
			return TravelUtil.getJsonString(403, "你还没有登录！");
		}
		DiscussPost discussPost = new DiscussPost();
		discussPost.setUserId(user.getId());
		discussPost.setTitle(title);
		discussPost.setContent(content);
		discussPost.setCreateTime(new Date());
		
		discussPostService.addDiscussPost(discussPost);
		
		//触发帖子事件
		Event event = new Event()
				.setTopic(TOPIC_PUBLISH)
				.setUserId(user.getId())
				.setEntityType(ENTITY_TYPE_POST)
				.setEntityId(discussPost.getId());
		eventProducer.fireEvent(event);

		//计算帖子分数
		String redisKey = RedisKeyUtil.getPostScoreKey();
		redisTemplate.opsForSet().add(redisKey, discussPost.getId());
		
		
		//报错的情况将来统一处理
		return TravelUtil.getJsonString(0, "发布成功！");
	}

	/**
	 * 根据帖子id获取帖子内容
	 * @param discussPostId 帖子id
	 * @param model 模型
	 * @param page 页面
	 * @return 帖子详情页面
	 */
	@GetMapping("/detail/{discussPostId}")
	public String getDiscussPost (@PathVariable("discussPostId") Integer discussPostId, Model model, Page page) {
		//帖子
		DiscussPost discussPost = discussPostService.findDiscussPost(discussPostId);
		model.addAttribute("post",discussPost);
		//作者
		User user = userService.findById(discussPost.getUserId());
		model.addAttribute("user",user);
		//点赞数量
		long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, discussPostId);
		model.addAttribute("likeCount",likeCount);
		//点赞状态
		int likeStatus = hostHolder.getUser() == null ? 0 : likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_POST, discussPostId);
		model.addAttribute("likeStatus",likeStatus);
		
		//评论分页信息
		page.setLimit(5);
		page.setPath("/discuss/detail/" + discussPostId);
		page.setRows(discussPost.getCommentCount());
		
		//评论
		List<Comment> commentList = commentService.findCommentsByEntity(ENTITY_TYPE_POST, discussPost.getId(), page.getOffset(), page.getLimit());
		List<Map<String,Object>> commentVoList = new ArrayList<>();
		if (commentList != null){
			for (Comment comment : commentList) {
				Map<String, Object> map = new HashMap<>();
				map.put("comment", comment);
				map.put("user",userService.findById(comment.getUserId()));
				//回复
				//评论数量
				likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT, comment.getId());
				map.put("likeCount",likeCount);
				//评论数量
				likeStatus = hostHolder.getUser() == null ? 0 : likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, comment.getId());
				map.put("likeStatus",likeStatus);
				
				List<Comment> replyList = commentService.findCommentsByEntity(ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
				List<Map<String, Object>> replyListVo = new ArrayList<>();
				if(replyList != null){
					for (Comment reply : replyList) {
						Map<String, Object> replyMap = new HashMap<>();
						replyMap.put("reply", reply);
						replyMap.put("user", userService.findById(reply.getUserId()));
						
						//点赞数量
						likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT, reply.getId());
						replyMap.put("likeCount",likeCount);
						//点赞数量
						likeStatus = hostHolder.getUser() == null ? 0 : likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, reply.getId());
						replyMap.put("likeStatus",likeStatus);
						
						User target = reply.getTargetId() == 0 ? null : userService.findById(reply.getTargetId());
						replyMap.put("target", target);
						
						replyListVo.add(replyMap);
					}
				}
				map.put("replys", replyListVo);
				
				//回复数量
				Integer replyCount = commentService.findCountByEntity(ENTITY_TYPE_COMMENT, comment.getId());
				map.put("replyCount", replyCount);
				
				commentVoList.add(map);
			}
		}

		model.addAttribute("comments", commentVoList);

		User userH = hostHolder.getUser();
		model.addAttribute("loginUserH",userH);

		return "/site/discuss-detail";
	}
	
	/**
	 * 置顶
	 * @param id 帖子id
	 * @return json 置顶结果
	 */
	@PostMapping("/top")
	@ResponseBody
	public String setTop(Integer id){
		discussPostService.updateType(id, 1);
		
		//触发发帖事件
		Event event = new Event()
				.setTopic(TOPIC_PUBLISH)
				.setUserId(hostHolder.getUser().getId())
				.setEntityType(ENTITY_TYPE_POST)
				.setEntityId(id);
		eventProducer.fireEvent(event);
		
		return TravelUtil.getJsonString(0);
	}
	
	/**
	 * 热点
	 * @param id 帖子id
	 * @return json，删除结果
	 */
	@PostMapping("/wonderful")
	@ResponseBody
	public String setWonderful(Integer id){
		discussPostService.updateStatus(id, 1);
		
		//触发发帖事件
		Event event = new Event()
				.setTopic(TOPIC_PUBLISH)
				.setUserId(hostHolder.getUser().getId())
				.setEntityType(ENTITY_TYPE_POST)
				.setEntityId(id);
		eventProducer.fireEvent(event);
		
		//计算帖子分数
		String redisKey = RedisKeyUtil.getPostScoreKey();
		redisTemplate.opsForSet().add(redisKey, id);
		
		
		return TravelUtil.getJsonString(0);
	}
	
	/**
	 * 删除
	 * @param id 帖子id
	 * @return json，删除结果
	 */
	@PostMapping("/delete")
	@ResponseBody
	public String setDelete(Integer id){
		discussPostService.updateStatus(id, 2);
		
		//触发发帖事件
		Event event = new Event()
				.setTopic(TOPIC_PUBLISH)
				.setUserId(hostHolder.getUser().getId())
				.setEntityType(ENTITY_TYPE_POST)
				.setEntityId(id);
		eventProducer.fireEvent(event);
		
		return TravelUtil.getJsonString(0);
	}
	
}
