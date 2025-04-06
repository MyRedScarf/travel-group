package com.fuchen.travel.controller;

import com.fuchen.travel.entity.DiscussPost;
import com.fuchen.travel.entity.Page;
import com.fuchen.travel.entity.Scenic;
import com.fuchen.travel.service.*;
import com.fuchen.travel.util.TravelConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fu chen
 * @date 2022/7/21
 * 搜索-controller层
 */
@Controller
public class SearchController implements TravelConstant {

	@Resource
	private UserService userService;

	@Resource
	private LikeService likeService;

	@Resource
	private SearchService searchService;

	@Resource
	private FavoriteService favoriteService;

	@Resource
	private DiscussPostService discussPostService;

	/**
	 * 搜索帖子
	 * @param keyword 关键字
	 * @param model 模型
	 * @param page 页面
	 * @return 返回搜索结果页面
	 */
	@GetMapping("/group/search")
	public String searchPost(String keyword, Model model, Page page) {

		//搜索帖子
		List<DiscussPost> searchResult = discussPostService.findToDiscussPost(keyword, page.getOffset(), page.getLimit());
		//分页信息
		page.setPath("/group/search?keyword=" + keyword);
		page.setRows(searchResult == null ? 0 : (int) searchResult.size());


		//聚合数据
		List<Map<String, Object>> discussPosts = new ArrayList<>();
		if (searchResult != null) {
			for (DiscussPost post : searchResult) {
				Map<String, Object> map = new HashMap<>();
				//帖子
				map.put("post", post);
				//作者
				map.put("user", userService.findById(post.getUserId()));
				//点赞数量
				map.put("likeCount", likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId()));
				discussPosts.add(map);
			}
		}
		model.addAttribute("discussPosts", discussPosts);
		model.addAttribute("keyword", keyword);

		return "/site/group-search";
	}

	/**
	 * 搜索景点
	 * @param keyword 关键字
	 * @param model 模型
	 * @param page 页面
	 * @return 返回搜索的景点列表页面
	 */
	@GetMapping("/scenic-list/search")
	public String searchScenic(String keyword, Model model, Page page) {

		page.setLimit(5);

		List<Scenic> searchResult = searchService.findToScenic(keyword, page.getCurrent() - 1, page.getLimit());

		page.setPath("/scenic-list/search?keyword=" + keyword);
		page.setRows(searchResult == null ? 0 : (int) searchResult.size());

		//聚合数据
		List<Map<String, Object>> toScenicList = new ArrayList<>();
		if (searchResult != null) {
			for (Scenic scenic : searchResult) {
				Map<String, Object> map = new HashMap<>();
				if (scenic.getIntroduce().length() > 48) {
					scenic.setIntroduce(scenic.getIntroduce().substring(0, 47) + "...");
				}
				if (scenic.getContent().length() > 40) {
					scenic.setContent(scenic.getContent().substring(0, 39) + "...");
				}
				//景区
				map.put("scenic", scenic);
				toScenicList.add(map);
			}
		}
		model.addAttribute("SearchScenicResult", toScenicList);
		model.addAttribute("scenicName", keyword);
		model.addAttribute("SearchScenicCount", searchResult == null ? 0 : (int) searchResult.size());

		//获取热门景点
		List<Scenic> scenicConllectionList = favoriteService.findCollectionByCount(0, 4);
		List<Map<String, Object>> scenicListByCount = new ArrayList<>();

		if (scenicConllectionList != null && scenicConllectionList.size() > 0) {
			for (Scenic scenic : scenicConllectionList) {
				Map<String, Object> map = new HashMap<>();
				map.put("scenic", scenic);
				scenicListByCount.add(map);
			}
		}

		model.addAttribute("scenicsHot", scenicListByCount);
		return "/site/search-scenic-list";
	}
}
