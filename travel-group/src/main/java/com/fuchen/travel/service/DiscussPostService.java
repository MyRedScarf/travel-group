package com.fuchen.travel.service;

import com.fuchen.travel.entity.DiscussPost;

import java.util.List;

/**
 * @author Fu chen
 * @date 2022/12/30
 * 帖子-service层
 */

public interface DiscussPostService {
	
	/**
	 * 分页查询用户帖子
	 * @param userId 用户id
	 * @param offset 每页起始行行号
	 * @param limit 每页显示条数
	 * @param orderMode 分栏
	 * @return 帖子的集合
	 */
	List<DiscussPost> findDiscussPosts(int userId, int offset, int limit, int orderMode);
	
	/**
	 * 查询用户帖子总数
	 * @param userId 用户id
	 * @return 返回帖子数量
	 */
	int findDiscussPostRows(int userId);
	
	/**
	 * 发布的帖子
	 * @param post 帖子对象
	 */
	void addDiscussPost(DiscussPost post);
	
	/**
	 * 查询帖子
	 * @param id 帖子的id
	 * @return 查询的结果DiscussPost对象
	 */
	DiscussPost findDiscussPost(Integer id);
	
	/**
	 * 修改帖子数量
	 * @param id 帖子id
	 * @param commentCount 需要修改字段的值
	 */
	void updateCommentCount(Integer id, Integer commentCount);
	
	/**
	 * 修改类型
	 * @param id 帖子id
	 * @param type 帖子类型
	 */
	void updateType(Integer id, Integer type);
	
	/**
	 * 修改状态
	 * @param id 帖子id
	 * @param status 帖子状态
	 * @return 修改行数
	 */
	Integer updateStatus(Integer id, Integer status);
	
	/**
	 * 修改分数
	 * @param id 帖子id
	 * @param score 分数
	 */
	void updateScore(Integer id, Double score);

	/**
	 * 通过用户id进行发帖查询
	 * @param userId 用户id
	 * @param offset 每页起始行号
	 * @param limit 每页显示条数
	 * @return 帖子列表
	 */
	List<DiscussPost> findDiscussPostByUserId (Integer userId, Integer offset, Integer limit);

	/**
	 * 根据关键词查询帖子总数
	 * @param keyword 关键字
	 * @return 结果数量
	 */
    Integer findDiscussPostToKeywordCount(String keyword);


	/**
	 * 根据关键词分页查询帖子
	 * @param keyword 关键字
	 * @param offset 分页起始行
	 * @param limit 检索条数
	 * @return 返回帖子列表
	 */
	List<DiscussPost> findToDiscussPost(String keyword, Integer offset, Integer limit);
}
