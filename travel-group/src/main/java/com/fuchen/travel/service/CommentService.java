package com.fuchen.travel.service;

import com.fuchen.travel.entity.Comment;

import java.util.List;

/**
 * @author Fu chen
 * @date 2022/12/12
 * 评论-service层
 */
public interface CommentService {

	/**
	 * 根据用户id查询评论集合
	 * @param userId 用户id
	 * @param offset 分页起始行
	 * @param limit 检索条数
	 * @return 返回评论集合
	 */
	List<Comment> findCommentByUserId(Integer userId, Integer offset, Integer limit);

	/**
	 * 查询用户的评论数量
	 * @param userId 用户id
	 * @return 返回用户评论数量
	 */
	Integer findCommentByUserIdCount(Integer userId);

	/**
	 * 查询评论的集合
	 * @param entityType 评论的类型
	 * @param entityId 评论id
	 * @param offset 每页起始行号
	 * @param limit 每页显示条数
	 * @return 返回评论集合
	 */
	List<Comment> findCommentsByEntity(Integer entityType, Integer entityId, Integer offset, Integer limit);
	
	/**
	 * 查询评论总数
	 * @param entityType 评论类型
	 * @param entityId 评论id
	 * @return 返回总数
	 */
	Integer findCountByEntity(Integer entityType, Integer entityId);
	
	/**
	 * 添加帖子
	 * @param comment 需要添加帖子的实体
	 */
	void addComment(Comment comment);
	
	/**
	 * 根据id查询comment
	 * @param id 评论id
	 * @return 返回评论信息
	 */
	Comment findCommentById(Integer id);

}
