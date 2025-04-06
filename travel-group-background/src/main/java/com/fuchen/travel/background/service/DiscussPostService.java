package com.fuchen.travel.background.service;

import com.fuchen.travel.background.entity.DiscussPost;

import java.util.List;

/**
 * @author 伏辰
 * @date 2023/1/5
 * 帖子-service层
 */
public interface DiscussPostService {

    /**
     * 获取帖子总数
     * @param keyword 关键字
     * @return 返回贴子数量
     */
    Integer getPostCount(String keyword);

    /**
     * 获取帖子信息
     * @param sort 查询类型参数
     * @param offset 分页起始行
     * @param limit 分页条数
     * @return 返回帖子信息集合
     */
    List<DiscussPost> getPost(String sort, Integer offset, Integer limit);

    /**
     * 添加帖子
     * @param title 帖子主题
     * @param content 帖子内容
     */
    void addDiscussPost(String title, String content);

    /**
     * 获取符合关键词的帖子数量
     * @param keyword 关键词
     * @return 返回对应数量
     */
    Integer getPostCountByKeyword(String keyword);

    /**
     * 更改帖子的状态，撤销的贴子恢复正常，正常的帖子撤销
     * @param postId 需要更改状态的帖子id
     * @param status 需要更改状态的帖子的状态
     */
    void changePostStatus(String postId, String status);
}
