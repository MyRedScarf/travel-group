package com.fuchen.travel.controller;

import com.fuchen.travel.entity.DiscussPost;
import com.fuchen.travel.entity.Page;
import com.fuchen.travel.entity.User;
import com.fuchen.travel.service.DiscussPostService;
import com.fuchen.travel.service.LikeService;
import com.fuchen.travel.service.UserService;
import com.fuchen.travel.util.TravelConstant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fu chen
 * @date 2022/12/30
 * 首页-controller层
 */
@Controller
public class GroupController implements TravelConstant {

	@Resource
	public UserService userService;

	@Resource
	public DiscussPostService discussPostService;

	@Resource
	private LikeService likeService;


	/**
	 * 前往拼团，处理帖子显示数据
	 * @param model 模型
	 * @param page 页面
	 * @return 返回拼团帖子页面
	 */
	@GetMapping("/group")
	public String getIndexPage(Model model, Page page , @RequestParam(name = "orderMode", defaultValue = "0") int orderMode){
		//SpringMVC会自动实例化Model和Page,并将Page注入Model
		page.setRows(discussPostService.findDiscussPostRows(0));
		page.setPath("/group?orderMode=" +  orderMode);
		
		List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit(), orderMode);
		List<Map<String, Object>> discussPosts = new ArrayList<>();
		if (list != null && list.size()>0) {
		    for (DiscussPost post : list) {
		        Map<String, Object> map = new HashMap<>();
				map.put("post", post);
			    User user = userService.findById(post.getUserId());
			    map.put("user", user);
			
			    long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
				map.put("likeCount", likeCount);
			    discussPosts.add(map);
		    }
		}
		model.addAttribute("discussPosts", discussPosts);
		model.addAttribute("orderMode", orderMode);
		return "/site/group";
	}

}
