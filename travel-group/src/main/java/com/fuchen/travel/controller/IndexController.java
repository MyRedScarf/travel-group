package com.fuchen.travel.controller;

import com.fuchen.travel.entity.Scenic;
import com.fuchen.travel.service.FavoriteService;
import com.fuchen.travel.service.ScenicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 * @author Fu chen
 * @date 2022/11/10
 * 首页-controller层
 */
@Slf4j
@Controller
public class IndexController {

    private final FavoriteService favoriteService;

    private final ScenicService scenicService;

    private final RedisTemplate redisTemplate;

    public IndexController(FavoriteService favoriteService, ScenicService scenicService, RedisTemplate redisTemplate) {
        this.favoriteService = favoriteService;
        this.scenicService = scenicService;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/")
    public String root(){
        return "forward:/index";
    }

    /**
     * 访问首页
     * @param model 模型渲染
     * @return 返回首页页面
     */
    @GetMapping("/index")
    public String index (Model model){

        //根据景点收藏次数获取景点集合，只取前四个景点信息
        List<Scenic> scenicConllectionList = favoriteService.findCollectionByCount(0, 4);

        List<Map<String, Object>> scenicListByCount = new ArrayList<>();
        //遍历整个集合，将集合中景点放入map中
        if (scenicConllectionList != null && scenicConllectionList.size() > 0) {
            for (Scenic scenic : scenicConllectionList) {
                Map<String, Object> map = new HashMap<>();
                if (scenic.getIntroduce().length() > 33) {
                    scenic.setIntroduce(scenic.getIntroduce().substring(0, 32) + "...");
                }
                map.put("scenic", scenic);
                scenicListByCount.add(map);
            }
        }
        model.addAttribute("scenicConllectionList", scenicListByCount);

        //查询推荐景点
        List<Scenic> scenicRe = scenicService.getScenicRe();
        List<Map<String, Object>> scenicListByRe = new ArrayList<>();
        //遍历整个集合，将集合中景点放入map中
        if (scenicRe != null && scenicRe.size() > 0) {
            for (Scenic scenic : scenicRe) {
                Map<String, Object> map = new HashMap<>();
                if (scenic.getIntroduce().length() > 44) {
                    scenic.setIntroduce(scenic.getIntroduce().substring(0, 43) + "...");
                }
                map.put("scenic", scenic);
                scenicListByRe.add(map);
            }
        }
        model.addAttribute("scenicListByRe", scenicListByRe);
        return "/index";
    }

    /**
     * 服务器异常-返回500
     * @return 服务器异常返回500页面
     */
    @GetMapping("/error")
    public String getErrorPage(){
        return "/error/500";
    }

    /**
     * 未找到异常-返回404
     * @return 服务器未找到页面返回404页面
     */
    @GetMapping("/denied")
    public String getDeniedPage(){
        return "/error/404";
    }
}
