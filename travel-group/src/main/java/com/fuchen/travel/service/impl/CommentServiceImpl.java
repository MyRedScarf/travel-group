package com.fuchen.travel.service.impl;

import com.fuchen.travel.entity.Comment;
import com.fuchen.travel.mapper.CommentMapper;
import com.fuchen.travel.service.CommentService;
import com.fuchen.travel.service.DiscussPostService;
import com.fuchen.travel.util.TravelConstant;
import com.fuchen.travel.util.SensitiveFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author Fu chen
 * @date 2022/12/12
 * 评论-service层-实现类
 */
@Service
public class CommentServiceImpl implements CommentService, TravelConstant {
	
	private final CommentMapper commentMapper;
	
	private final SensitiveFilter sensitiveFilter;
	
	private final DiscussPostService discussPostService;

	public CommentServiceImpl(CommentMapper commentMapper, SensitiveFilter sensitiveFilter, DiscussPostService discussPostService) {
		this.commentMapper = commentMapper;
		this.sensitiveFilter = sensitiveFilter;
		this.discussPostService = discussPostService;
	}


	/**
	 * 根据用户id查询评论集合
	 * @param userId 用户id
	 * @param offset 分页起始行
	 * @param limit 检索条数
	 * @return 返回评论集合
	 */
	@Override
	public List<Comment> findCommentByUserId(Integer userId, Integer offset, Integer limit) {
		return commentMapper.selectCommentByUserId(userId, offset, limit);
	}

	/**
	 * 查询用户的评论数量
	 * @param userId 用户id
	 * @return 返回用户评论数量
	 */
	@Override
	public Integer findCommentByUserIdCount(Integer userId) {
		return commentMapper.selectCommentByUserIdCount(userId);
	}

	/**
	 * 查询评论的集合
	 * @param entityType 评论的类型
	 * @param entityId 评论id
	 * @param offset 每页起始行号
	 * @param limit 每页显示条数
	 * @return 返回评论集合
	 */
	@Override
	public List<Comment> findCommentsByEntity(Integer entityType, Integer entityId, Integer offset, Integer limit) {
		return commentMapper.selectCommentByEntity(entityType, entityId, offset, limit);
	}

	/**
	 * 查询评论总数
	 * @param entityType 评论类型
	 * @param entityId 评论id
	 * @return 返回总数
	 */
	@Override
	public Integer findCountByEntity(Integer entityType, Integer entityId) {
		return commentMapper.selectCountByEntity(entityType, entityId);
	}

	/**
	 * 添加帖子
	 * @param comment 需要添加帖子的实体
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void addComment(Comment comment) {
		//过滤敏感词
		comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
		comment.setContent(sensitiveFilter.filter(comment.getContent()));
		
		//添加评论
		int row = commentMapper.insertComment(comment);
		
		//更新帖子的评论数量
		if (comment.getEntityType() == ENTITY_TYPE_POST) {
			int count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
			discussPostService.updateCommentCount(comment.getId(), count);
		}
	}

	/**
	 * 根据id查询comment
	 * @param id 评论id
	 * @return 返回评论信息
	 */
	@Override
	public Comment findCommentById(Integer id) {
		return commentMapper.selectCommentById(id);
	}
}
