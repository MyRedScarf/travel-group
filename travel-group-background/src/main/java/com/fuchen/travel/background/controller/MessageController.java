package com.fuchen.travel.background.controller;

import com.fuchen.travel.background.entity.Message;
import com.fuchen.travel.background.entity.Page;
import com.fuchen.travel.background.entity.User;
import com.fuchen.travel.background.service.MessageService;
import com.fuchen.travel.background.service.UserService;
import com.fuchen.travel.background.util.HostHolder;
import com.fuchen.travel.background.util.TravelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 伏辰
 * @date 2023/1/5
 * 消息-controller层
 */
@Controller
public class MessageController {

    @Resource
    private MessageService messageService;

    @Resource
    private UserService userService;

    @Resource
    private HostHolder hostHolder;

    /**
     * 进入消息页面
     * @param model 模板渲染
     * @param page 分页工具
     * @return 返回message页面
     */
    @GetMapping("/message")
    public String getMessage(String status, Model model, Page page) {
        //获取私信信息数量
        Integer messageCount = messageService.getMessageCount(status);
        page.setLimit(10);
        if (status == null) {
            page.setPath("/message");
        } else {
            page.setPath("/message?status=" + status);
        }

        page.setRows(messageCount);

        List<Message> messages = messageService.getMessage(status,page.getOffset(),page.getLimit());

        List<Map<String, Object>> messageList = new ArrayList<>();

        if (messages.size() > 0) {
            for (int i = 0; i < messages.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("message", messages.get(i));
                User user = userService.getUserById(messages.get(i).getFromId());
                map.put("user", user);
                messageList.add(map);
            }
        }

        model.addAttribute("messageList", messageList);

        return "/pages/message";
    }

    /**
     * 修改消息状态
     * @param status 当前消息状态
     * @param id 当前消息id
     * @return 返回json
     */
    @ResponseBody
    @PostMapping("/updateMessageStatus")
    public String updateMessageStatus(String status, String id) {
        //修改消息状态
        messageService.changeStatus(id, status);
        //返回修改成功的json
        if (Integer.parseInt(status) == 1) {
            return TravelUtil.getJsonString(0, "OK");
        } else {
            return TravelUtil.getJsonString(1, "OK");
        }

    }

    @GetMapping("/send-email")
    public String getSendEmail(Model model, String email) {
        if (email == null) {
            return "/pages/send-email";
        }
        if (!email.isEmpty()) {
            model.addAttribute("email", email);
        }
        return "/pages/send-email";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(Model model, String email, String title, String content) {
        if (email == null || email.isEmpty()) {
            model.addAttribute("emailMsg", "请填写邮箱!");
            return "/pages/send-email";
        }
        if (title == null || title.isEmpty()) {
            model.addAttribute("titleMsg", "请填写主题!");
            return "/pages/send-email";
        }
        if (content == null || content.isEmpty()) {
            model.addAttribute("contentMsg", "请填写内容!");
            return "/pages/send-email";
        }
        messageService.sendEmail(email, title, content);

        return "/pages/send-email";
    }




}
