package com.fuchen.travel.background.service.impl;

import com.fuchen.travel.background.entity.Message;
import com.fuchen.travel.background.entity.User;
import com.fuchen.travel.background.mapper.MessageMapper;
import com.fuchen.travel.background.service.MessageService;
import com.fuchen.travel.background.util.HostHolder;
import com.fuchen.travel.background.util.MailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

/**
 * @author 伏辰
 * @date 2023/1/12
 * 消息-service层-实现类
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 获取消息数量
     * @param status 状态
     * @return 返回消息数量
     *
     */
    @Override
    public Integer getMessageCount(String status) {
        User user = hostHolder.getUser();
        Integer i = null;
        if (status == null) {
        } else {
            i = Integer.parseInt(status);
        }

        return messageMapper.selectMessageCount( i , user.getId());
    }

    /**
     * 查询消息信息
     * @param status 状态
     * @param offset 查询起始行
     * @param limit 查询条数
     * @return 返回消息列表
     */
    @Override
    public List<Message> getMessage(String status, Integer offset, Integer limit) {
        User user = hostHolder.getUser();
        Integer i = null;
        if (status == null) {

        } else {
            i = Integer.parseInt(status);
        }
        return messageMapper.selectMessageList(i, user.getId(), offset, limit);
    }

    /**
     * 修改消息状态
     * @param id 消息id
     * @param oldStatus 当前消息的状态
     */
    @Override
    public void changeStatus(String id, String oldStatus) {
        //将字符串转换为int类型
        int status = Integer.parseInt(oldStatus);
        //帕努单当前状态的值，如果为1，将其修改为0；否则修改为1,1为已读，0为未读
        if (status == 1) {
            status = 0;
        } else {
            status = 1;
        }
        //修改消息状态
        messageMapper.updateStatus(Integer.parseInt(id), status);
    }

    /**
     * 发送邮件
     * @param email 邮箱
     * @param title 主题
     * @param content 内容
     */
    @Override
    public void sendEmail(String email, String title, String content) {
        Context context = new Context();
        context.setVariable("email", email);
        context.setVariable("title", title);
        context.setVariable("content", content);
        String contentEnd = templateEngine.process("/mail/mail", context);
        mailClient.sendMail(email, title, contentEnd);
    }
}
