package com.fuchen.travel.background.controller;

import com.fuchen.travel.background.entity.Feedback;
import com.fuchen.travel.background.entity.Page;
import com.fuchen.travel.background.entity.User;
import com.fuchen.travel.background.service.FeedbackService;
import com.fuchen.travel.background.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2023/3/12
 * 意见反馈-controller层
 */
@Controller
@Slf4j
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private UserService userService;

    /**
     * 获取意见反馈数据
     * @param model 模型渲染
     * @param page 分页对象
     * @return 进入意见反馈页面
     */
    @GetMapping("/feedback")
    public String feedback(Model model, Page page){
        //设置分页数据
        page.setLimit(10);
        page.setRows(feedbackService.getAllCount());
        page.setPath("/feedback");
        //获取意见反馈信息
        List<Feedback> feedbacks = feedbackService.findAllFeedback(page.getOffset(), page.getLimit());
        //创建一个响应集合
        List<Map<String, Object>> feedbackList = new ArrayList<>();
        //遍历反馈信息集合
        feedbacks.stream().forEach(item -> {
            //创建一个map集合用于保存数据
            Map<String, Object> map = new HashMap<>();
            //根据反馈集合中的userId获取用户信息
            User user = userService.getUserById(item.getUserId());
            //保存到map集合中
            map.put("feedback", item);
            map.put("user", user);
            //将map集合保存到响应集合中
            feedbackList.add(map);
        });

        model.addAttribute("feedbackList", feedbackList);
        return "/pages/feedback";
    }
}
