package com.fuchen.travel.background.controller;

import com.fuchen.travel.background.entity.LoginTicket;
import com.fuchen.travel.background.entity.Scenic;
import com.fuchen.travel.background.service.DiscussPostService;
import com.fuchen.travel.background.service.ScenicService;
import com.fuchen.travel.background.service.UserService;
import com.fuchen.travel.background.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

/**
 * @Author 伏辰
 * @Date 2023/1/3
 * 首页-controller层
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    private ScenicService scenicService;

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 访问/index前往index页面
     * @param model 模板
     * @return
     */
    @GetMapping("/index")
    public String index(Model model, @CookieValue("ticket") String ticket){
        //获取推荐的景点集合
        List<Scenic> scenicList = scenicService.findRecommendScenic();
        //创建一个list集合，用于保存数据
        List<Map<String, Scenic>> recommendScenicList = new ArrayList<>();
        //循环遍历景点集合，将每个景点对象都放入map集合中，之后放入到创建的list集合中保存数据
        for (int i = 0; i < scenicList.size(); i++) {
            Map<String, Scenic> map = new HashMap<>();
            map.put("scenicList", scenicList.get(i));
            recommendScenicList.add(map);
        }
        LoginTicket loginTicket = userService.findLoginTicket(ticket);
        if (StringUtils.isEmpty(ticket) || loginTicket.getStatus() == 1) {
            return "pages/login";
        }

        List<Scenic> serviceScenicList = scenicService.getScenicList();
        List<Scenic> recommendServiceScenicList = scenicService.getRecommendScenicList();

        model.addAttribute("serviceScenicList", serviceScenicList);

        model.addAttribute("recommendServiceScenicList", recommendServiceScenicList);

        model.addAttribute("recommendScenicList", recommendScenicList);

        model.addAttribute("recommendScenicList", recommendScenicList);
        //将今日时期，用户数量，景点数量，帖子数量，封禁用户放入model中
        model.addAttribute("nowDate", new Date());
        model.addAttribute("userCount", userService.getUserCount());
        model.addAttribute("scenicCount", scenicService.getScenicCount());
        model.addAttribute("discussPostCount", discussPostService.getPostCount(null));
        model.addAttribute("banUserCount", userService.getBanUserCount());

        //推荐景点数量
        model.addAttribute("scenicRecommendCount", scenicService.getScenicRecommendCount());

        return "/index";
    }
}
