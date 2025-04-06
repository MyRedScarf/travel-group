package com.fuchen.travel.mapper;

import com.fuchen.travel.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Fu chen
 * @date 2022/12/30
 * 帖子-mapper层
 */
@Mapper
public interface DiscussPostMapper {
	/**
	 * 查询用户帖子
	 * @param userId 用户id
	 * @param offset 每页起始行行号
	 * @param limit 每页显示条数
	 * @param orderMode 查询方式
	 * @return 帖子的集合
	 */
	List<DiscussPost> selectDiscussPosts(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("orderMode") Integer orderMode);
	
	/**
	 * 查询用户发帖条数
	 * @param userId 用户id
	 * @return 用户发帖条数
	 */
	Integer selectDiscussPostRows(@Param("userId") Integer userId);
	
	/**
	 * 保存帖子
	 * @param discussPost 帖子的内容
	 * @return 添加的行数
	 */
	Integer insertDiscussPost(DiscussPost discussPost);
	
	/**
	 * 查询帖子详情
	 * @param id 帖子的id
	 * @return 帖子对象
	 */
	DiscussPost selectDiscussPost(@Param("id") Integer id);
	
	/**
	 * 更新帖子的数量
	 * @param id 帖子的id
	 * @param commentCount 更新comment_count数量
	 * @return 更新行数
	 */
	Integer updateCommentCount(@Param("id") Integer id, @Param("commentCount") Integer commentCount);
	
	/**
	 * 修改类型
	 * @param id 帖子id
	 * @param type 帖子类型
	 * @return 修改行数
	 */
	Integer updateType(@Param("id") Integer id, @Param("type") Integer type);
	
	/**
	 * 修改状态
	 * @param id 帖子id
	 * @param status 帖子状态
	 * @return 修改行数
	 */
	Integer updateStatus(@Param("id") Integer id, @Param("status") Integer status);
	
	/**
	 * 修改分数
	 * @param id 帖子id
	 * @param score 分数
	 * @return 修改行数
	 */
	Integer updateScore(@Param("id") Integer id, @Param("score") Double score);

	/**
	 * 根据用户id查询帖子
	 * @param id 用户id
	 * @param offset 每页起始行号
	 * @param limit 每页显示条数
	 * @return 帖子集合
	 */
	List<DiscussPost> selectDiscussPostByUserId(@Param("id") Integer id, @Param("offset") Integer offset, @Param("limit") Integer limit);

	/**
	 * 根据关键词查询帖子总数
	 * @param keyword 关键词
	 * @return 帖子数量
	 */
    Integer selectToKeywordCount(@Param("keyword") String keyword);

	/**
	 * 根据关键词查询帖子
	 * @param keyword 关键词
	 * @param offset 分页起始行
	 * @param limit 检索条数
	 * @return 帖子集合
	 */
	List<DiscussPost> selectToDiscussionPost(@Param("keyword") String keyword, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
