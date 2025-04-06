package com.fuchen.travel.background.service;

import com.fuchen.travel.background.entity.Message;

import java.util.List;

/**
 * @author 伏辰
 * @date 2023/1/12
 * 消息-service层
 */
public interface MessageService {

    /**
     * 获取消息数量
     * @param status 状态
     * @return 返回消息数量
     *
     */
    Integer getMessageCount(String status);

    /**
     * 查询消息信息
     * @param status 状态
     * @param offset 查询起始行
     * @param limit 查询条数
     * @return 返回消息列表
     */
    List<Message> getMessage(String status, Integer offset, Integer limit);

    /**
     * 修改消息状态
     * @param id 消息id
     * @param oldStatus 当前消息的状态
     */
    void changeStatus(String id, String oldStatus);

    /**
     * 发送邮件
     * @param email 邮箱
     * @param title 主题
     * @param content 内容
     */
    void sendEmail(String email, String title, String content);
}
