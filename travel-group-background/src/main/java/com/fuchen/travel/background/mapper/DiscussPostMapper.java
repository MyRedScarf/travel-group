package com.fuchen.travel.background.mapper;

import com.fuchen.travel.background.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 伏辰
 * @date 2023/1/5
 * 帖子-mapper层
 */
@Mapper
public interface DiscussPostMapper {

    /**
     * 查询帖子总数
     * @return 帖子数量
     */
    Integer selectPostCount ();

    /**
     * 查询帖子信息
     * @param offset 分页起始行
     * @param limit 分页条数
     * @return 返回帖子信息的集合
     */
    List<DiscussPost> selectPost(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 插入新帖子信息
     * @param discussPost 帖子对象信息
     */
    void insertDiscussPost(@Param("post") DiscussPost discussPost);

    /**
     * 查询帖子信息，按照id排序
     * @param offset 分页起始行
     * @param limit 分页条数
     * @return 返回帖子信息集合
     */
    List<DiscussPost> selectPostSortById(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 查询帖子信息，按照时间排序
     * @param offset 分页起始行
     * @param limit 分页条数
     * @return 返回帖子信息集合
     */
    List<DiscussPost> selectPostSortByTime(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 查询管理员发布的帖子信息
     * @param offset 分页起始行
     *      * @param limit 分页条数
     * @return 返回管理员发布帖子信息集合
     */
    List<DiscussPost> selectPostToAdmin(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 查询符合关键词的帖子数量
     * @param keyword 关键词
     * @return 对应数量
     */
    Integer selectPostCountByKeyWord(@Param("keyword") String keyword);

    /**
     * 查询符合关键词的帖子信息
     * @param keyword 关键词
     * @param offset 分页起始行
     * @param limit 分页条数
     * @return 帖子信息集合
     */
    List<DiscussPost> selectPostByKeyword(@Param("keyword") String keyword,@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 修改帖子状态，撤销帖子转正常，否则相反
     * @param postId 帖子id
     * @param status 帖子当前状态
     */
    void updatePostStatus(@Param("id") String postId, @Param("status") String status);

    /**
     * 查询撤销的帖子
     * @param offset 分页起始行
     * @param limit 分页条数
     * @return 帖子信息集合
     */
    List<DiscussPost> selectPostCancel(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 查询管理员发帖数量
     * @return 发帖数量
     */
    Integer selectPostCountToAdmin();

    /**
     * 查询撤销帖子数量
     * @return 撤销帖子数量
     */
    Integer selectPostCountToCancel();
}
