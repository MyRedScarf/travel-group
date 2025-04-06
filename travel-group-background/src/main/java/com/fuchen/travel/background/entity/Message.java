package com.fuchen.travel.background.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author 伏辰
 * @date 2023/1/11
 * 消息-实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    /**
     * 消息id
     */
    private Integer id;
    /**
     * 发送者id
     */
    private Integer fromId;
    /**
     * 接受者id
     */
    private Integer toId;
    /**
     * 会话id
     */
    private String conversationId;
    /**
     * 内容
     */
    private String content;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
}
