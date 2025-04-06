package com.fuchen.travel.background.controller;

import com.fuchen.travel.background.entity.DiscussPost;
import com.fuchen.travel.background.entity.Page;
import com.fuchen.travel.background.entity.User;
import com.fuchen.travel.background.service.DiscussPostService;
import com.fuchen.travel.background.service.LikeService;
import com.fuchen.travel.background.service.UserService;
import com.fuchen.travel.background.util.TravelConstant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  伏辰
 * @date 2023/1/5
 * 帖子管理-controller层
 */
@Controller
public class DiscussPostController implements TravelConstant {

    private final DiscussPostService discussPostService;

    private final UserService userService;

    private final LikeService likeService;

    public DiscussPostController(DiscussPostService discussPostService, UserService userService, LikeService likeService) {
        this.discussPostService = discussPostService;
        this.userService = userService;
        this.likeService = likeService;
    }


    /**
     * 进入帖子页面
     * @param model 模板渲染
     * @param page 分页
     * @param keyword 关键字,用于判断获取哪种帖子数据
     * @return 返回帖子管理页面
     */
    @GetMapping("/discuss-post-control")
    public String discussPost(Model model, Page page, String keyword){

        //获取帖子数量
        Integer postCount = discussPostService.getPostCount(keyword);
        //设置分页数据
        page.setLimit(10);
        page.setPath("/discuss-post-control");
        page.setRows(postCount);
        System.out.println(postCount);
        //keyword不为空，则说明有条件获取数据，应重新设置分页路径
        if (keyword != null){
            page.setPath("/discuss-post-control?keyword=" + keyword);
        }
        //分页获取帖子内容
        List<DiscussPost> discussPosts = discussPostService.getPost(keyword, page.getOffset(), page.getLimit());
        //创建list集合存放map集合，将贴子的数据放入map集合中
        List<Map<String, Object>> postList = new ArrayList<>();
        if(discussPosts.size() > 0) {
            for (DiscussPost discussPost : discussPosts) {
                Map<String, Object> map = new HashMap<>(4);
                map.put("post", discussPost);
                User user = userService.getUserById(Integer.parseInt(discussPost.getUserId()));
                map.put("user",user);
                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, discussPost.getId());
                map.put("likeCount", likeCount);
                postList.add(map);
            }
        }
        //添加到模板渲染
        model.addAttribute("postList", postList);

        //返回帖子管理页面
        return "/pages/discuss-post";
    }

    /**
     * 发布帖子
     * @param title 帖子主题
     * @param content 帖子内容
     * @return 重定向到帖子管理页面
     */
    @PostMapping("/addPost")
    public String addPost(String title, String content){
        //添加帖子
        discussPostService.addDiscussPost(title, content);
        //重定向到管理页面
        return "redirect:/discuss-post-control";
    }

    /**
     * 通过关键词获取帖子信息
     * @param keyword 关键词
     * @param model 渲染模板
     * @param page 分页工具
     * @return 进入帖子管理页面
     */
    @GetMapping("/discuss-post-control/search")
    public String postSearch(String keyword, Model model, Page page) {
        //获取帖子数量
        Integer postCount = discussPostService.getPostCountByKeyword(keyword);
        //设置分页数据
        page.setLimit(10);
        page.setPath("/discuss-post-control/search?keyword=" + keyword);
        page.setRows(postCount);
        //分页获取帖子内容
        List<DiscussPost> discussPosts = discussPostService.getPost(keyword, page.getOffset(), page.getLimit());
        //创建list集合存放map集合，将贴子的数据放入map集合中
        List<Map<String, Object>> postList = new ArrayList<>();
        if(discussPosts.size() > 0) {
            for (DiscussPost discussPost : discussPosts) {
                Map<String, Object> map = new HashMap<>(4);
                map.put("post", discussPost);
                User user = userService.getUserById(Integer.parseInt(discussPost.getUserId()));
                map.put("user",user);
                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, discussPost.getId());
                map.put("likeCount", likeCount);
                postList.add(map);
            }
        }
        //添加到模板渲染
        model.addAttribute("postList", postList);
        //返回帖子管理页面
        return "/pages/discuss-post";
    }

    /**
     * 修改帖子状态
     * @param postId 帖子id
     * @param status 当前帖子的状态
     * @return 重定向到帖子管理页面
     */
    @PostMapping("/postStatus")
    public String removePost(String postId, String status) {
        //修改帖子状态
        discussPostService.changePostStatus(postId, status);
        //重定向到帖子管理页面
        return "redirect:/discuss-post-control";
    }


}
