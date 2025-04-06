package com.fuchen.travel.service.impl;

import com.fuchen.travel.entity.DiscussPost;
import com.fuchen.travel.mapper.DiscussPostMapper;
import com.fuchen.travel.service.DiscussPostService;
import com.fuchen.travel.util.SensitiveFilter;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Fu chen
 * @date 2022/12/30
 * 帖子-service层-实现类
 */
@Slf4j
@Service
public class DiscussPostServiceImpl implements DiscussPostService {
	

	private final DiscussPostMapper discussPostMapper;
	
	private final SensitiveFilter sensitiveFilter;
	
	@Value("${caffeine.posts.max-size}")
	private int maxSize;
	
	@Value("${caffeine.posts.expire-seconds}")
	private int expireSeconds;
	
	// 帖子列表缓存
	private LoadingCache<String, List<DiscussPost>> postListCache;
	
	// 帖子总数缓存
	private LoadingCache<Integer, Integer> postRowsCache;

	public DiscussPostServiceImpl(DiscussPostMapper discussPostMapper, SensitiveFilter sensitiveFilter) {
		this.discussPostMapper = discussPostMapper;
		this.sensitiveFilter = sensitiveFilter;
	}

	@PostConstruct
	public void init() {
		// 初始化帖子列表缓存
		postListCache = Caffeine.newBuilder()
				.maximumSize(maxSize)
				.expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
				.build(new CacheLoader<String, List<DiscussPost>>() {
					@Nullable
					@Override
					public List<DiscussPost> load(@NonNull String key) throws Exception {
						if (key == null || key.length() == 0) {
							throw new IllegalArgumentException("参数错误!");
						}
						
						String[] params = key.split(":");
						if (params == null || params.length != 2) {
							throw new IllegalArgumentException("参数错误!");
						}
						
						int offset = Integer.valueOf(params[0]);
						int limit = Integer.valueOf(params[1]);
						
						// 二级缓存: Redis -> mysql
						
						log.debug("load post list from DB.");
						return discussPostMapper.selectDiscussPosts(0, offset, limit, 1);
					}
				});
		// 初始化帖子总数缓存
		postRowsCache = Caffeine.newBuilder()
				.maximumSize(maxSize)
				.expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
				.build(new CacheLoader<Integer, Integer>() {
					@Nullable
					@Override
					public Integer load(@NonNull Integer key) throws Exception {
						log.debug("load post rows from DB.");
						return discussPostMapper.selectDiscussPostRows(key);
					}
				});
	}

	/**
	 * 分页查询用户帖子
	 * @param userId 用户id
	 * @param offset 每页起始行行号
	 * @param limit 每页显示条数
	 * @param orderMode 分栏
	 * @return 帖子的集合
	 */
	@Override
	public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit, int orderMode) {
		if (userId == 0 && orderMode == 1) {
			return postListCache.get(offset + ":" + limit);
		}
		
		log.debug("load post list from DB.");
		return discussPostMapper.selectDiscussPosts(userId, offset, limit, orderMode);
	}

	/**
	 * 查询用户帖子总数
	 * @param userId 用户id
	 * @return 返回帖子数量
	 */
	@Override
	public int findDiscussPostRows(int userId) {
		if (userId == 0) {
			return postRowsCache.get(userId);
		}
		
		log.debug("load post rows from DB.");
		return discussPostMapper.selectDiscussPostRows(userId);
	}

	/**
	 * 发布的帖子
	 * @param post 帖子对象
	 */
	@Override
	public void addDiscussPost(DiscussPost post) {
		if (post == null) {
			throw new IllegalArgumentException("参数不能为空！！");
		}
		//转义HTML标记
		post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
		post.setContent(HtmlUtils.htmlEscape(post.getContent()));
		
		//过滤敏感词
		post.setTitle(sensitiveFilter.filter(post.getTitle()));
		post.setContent(sensitiveFilter.filter(post.getContent()));

		discussPostMapper.insertDiscussPost(post);

	}

	/**
	 * 查询帖子
	 * @param id 帖子的id
	 * @return 查询的结果DiscussPost对象
	 */
	@Override
	public DiscussPost findDiscussPost(Integer id) {
		return discussPostMapper.selectDiscussPost(id);
	}

	/**
	 * 修改帖子数量
	 * @param id 帖子id
	 * @param commentCount 需要修改字段的值
	 */
	@Override
	public void updateCommentCount(Integer id, Integer commentCount) {
		discussPostMapper.updateCommentCount(id, commentCount);
	}

	/**
	 * 修改类型
	 * @param id 帖子id
	 * @param type 帖子类型
	 */
	@Override
	public void updateType(Integer id, Integer type) {
		discussPostMapper.updateType(id, type);
	}

	/**
	 * 修改状态
	 * @param id 帖子id
	 * @param status 帖子状态
	 * @return 修改行数
	 */
	@Override
	public Integer updateStatus(Integer id, Integer status) {
		return discussPostMapper.updateStatus(id, status);
	}

	/**
	 * 修改分数
	 * @param id 帖子id
	 * @param score 分数
	 */
	@Override
	public void updateScore(Integer id, Double score) {
		discussPostMapper.updateScore(id, score);
	}

	/**
	 * 通过用户id进行发帖查询
	 * @param userId 用户id
	 * @param offset 每页起始行号
	 * @param limit 每页显示条数
	 * @return 帖子列表
	 */
	@Override
	public List<DiscussPost> findDiscussPostByUserId(Integer userId, Integer offset, Integer limit) {
		List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPostByUserId(userId, offset, limit);
		return discussPosts;
	}

	/**
	 * 根据关键词查询帖子总数
	 * @param keyword 关键字
	 * @return 结果数量
	 */
	@Override
	public Integer findDiscussPostToKeywordCount(String keyword) {
		return discussPostMapper.selectToKeywordCount(keyword);
	}

	/**
	 * 根据关键词分页查询帖子
	 * @param keyword 关键字
	 * @param offset 分页起始行
	 * @param limit 检索条数
	 * @return 返回帖子列表
	 */
	@Override
	public List<DiscussPost> findToDiscussPost(String keyword, Integer offset, Integer limit) {
		return discussPostMapper.selectToDiscussionPost(keyword, offset, limit);
	}
}
