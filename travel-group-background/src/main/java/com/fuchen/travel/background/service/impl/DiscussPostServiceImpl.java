package com.fuchen.travel.background.service.impl;

import com.fuchen.travel.background.entity.DiscussPost;
import com.fuchen.travel.background.entity.User;
import com.fuchen.travel.background.mapper.DiscussPostMapper;
import com.fuchen.travel.background.service.DiscussPostService;
import com.fuchen.travel.background.util.HostHolder;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 伏辰
 * @date 2023/1/5
 * 帖子-service层-实现类
 */
@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    private final DiscussPostMapper discussPostMapper;


    public DiscussPostServiceImpl(DiscussPostMapper discussPostMapper, RedisTemplate redisTemplate, HostHolder hostHolder) {
        this.discussPostMapper = discussPostMapper;
        this.redisTemplate = redisTemplate;
        this.hostHolder = hostHolder;
    }

    private final RedisTemplate redisTemplate;

    private final HostHolder hostHolder;

    /**
     * 获取帖子总数
     * @return 返回帖子数量
     */
    @Override
    public Integer getPostCount(String keyword) {
        String admin = "admin";
        String cancel = "cancel";
        //判断字符串keyword的值，如果与admin相等，查询管理员发帖数量;
        //如果与cancel相等，查询撤销的帖子数量
        //否则查询所有未撤销的帖子数量
        if (admin.equals(keyword)) {
            return discussPostMapper.selectPostCountToAdmin();
        } if (cancel.equals(keyword)) {
            return discussPostMapper.selectPostCountToCancel();
        } else {
            //首选从redis中查询帖子总数
            Integer postCount = (Integer) redisTemplate.opsForValue().get("postCount");
            //如果查询结果不为空，则直接返回当前数据
            if (postCount != null) {
                return postCount;
            }
            //为空，则进入数据库重新查询后返回数据
            return discussPostMapper.selectPostCount();
        }

    }

    /**
     * 获取帖子信息
     * @param keyword 查询类型参数
     * @param offset 分页起始行
     * @param limit 分页条数
     * @return 返回帖子信息集合
     * */
    @Override
    public List<DiscussPost> getPost(String keyword,Integer offset, Integer limit) {
        //创建字符串，用于与传入的sort比较
        String id = "id";
        String time = "time";
        String admin = "admin";
        String cancel = "cancel";
        //用于接收查询后的帖子列表
        List<DiscussPost> discussPosts = null;
        //判断，需要按照哪种类型查询，与id相等表示按照id正序排序查询;
        //与time相等表示按照 时间 倒序排序查询;
        //与admin相等表示只查询管理员发布的帖子，按照身份级别和时间倒序排序;
        //与cancel相等表示查询撤销的帖子，按照身份级别、时间倒序排序;
        //与空字符串相等表示查询发布的帖子，按照身份级别、状态、时间倒序排序;
        //否则，通过关键字直接查询，按照身份级别和时间倒序排序;
        if (id.equals(keyword)) {
            discussPosts = discussPostMapper.selectPostSortById(offset, limit);
        } else if (time.equals(keyword)) {
            discussPosts = discussPostMapper.selectPostSortByTime(offset, limit);
        } else if (admin.equals(keyword)) {
            discussPosts = discussPostMapper.selectPostToAdmin(offset, limit);
        } else if(cancel.equals(keyword)) {
            discussPosts = discussPostMapper.selectPostCancel(offset, limit);
        } else if ("".equals(keyword) || keyword == null){
            discussPosts = discussPostMapper.selectPost(offset, limit);
        } else {
            discussPosts = discussPostMapper.selectPostByKeyword(keyword, offset, limit);
        }
        //返回查询的帖子信息
        return discussPosts;
    }

    /**
     * 添加帖子
     * @param title 帖子主题
     * @param content 帖子内容
     */
    @Override
    public void addDiscussPost(String title, String content) {
        //获取当前登录的user信息
        User user = hostHolder.getUser();
        //创建discussPost对象，将数据存入
        DiscussPost discussPost = new DiscussPost();
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setUserId(String.valueOf(user.getId()));
        discussPost.setType(user.getType());
        discussPost.setCreateTime(new Date());
        //插入新帖子
        discussPostMapper.insertDiscussPost(discussPost);
        //清除redis中的帖子数量
        redisTemplate.delete("postCount");
    }

    /**
     * 获取符合关键词的帖子数量
     * @param keyword 关键词
     * @return 返回对应数量
     */
    @Override
    public Integer getPostCountByKeyword(String keyword) {
        return discussPostMapper.selectPostCountByKeyWord(keyword);
    }

    /**
     * 更改帖子的状态，撤销的贴子恢复正常，正常的帖子撤销
     * @param postId 需要更改状态的帖子id
     * @param status 需要更改状态的帖子的状态
     */
    @Override
    public void changePostStatus(String postId, String status) {
        discussPostMapper.updatePostStatus(postId, status);
    }
}
