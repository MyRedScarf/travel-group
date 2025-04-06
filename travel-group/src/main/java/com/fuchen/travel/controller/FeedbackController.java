package com.fuchen.travel.controller;

import com.fuchen.travel.service.FeedbackService;
import com.fuchen.travel.util.TravelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Fu chen
 * @date 2023/3/9
 * 意见反馈-controller层
 */
@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/feedback")
    public String feedback(String userId, String feedbackContent){
        if (userId.isEmpty()) {
            return TravelUtil.getJsonString(1, "请登录!");
        }
        if (feedbackContent.isEmpty()) {
            return TravelUtil.getJsonString(1, "请输入反馈内容!");
        }
        feedbackService.addFeedback(Integer.parseInt(userId), feedbackContent);
        return "redirect:/index";
    }

}
