package com.fuchen.travel.service;

import com.fuchen.travel.entity.Message;

import java.util.List;

/**
 * @author Fu chen
 * @date 2022/12/14
 * 私信-service层
 */
public interface MessageService {
	
	/**
	 * 查询当前用户的会话列表，针对每个会话只返回最新的私信
	 * @param userId 当前用户的id
	 * @param offset 当前页的起始行
	 * @param limit 当前页的显示条数
	 * @return Message的List集合
	 */
	List<Message> findConversations(Integer userId, Integer offset, Integer limit);
	
	/**
	 * 查询用户的会话数量
	 * @param userId 当前用户id
	 * @return 查询数量
	 */
	Integer findConversationCount(Integer userId);
	
	/**
	 * 查询某个会话所包含的私信列表
	 * @param conversationId 私信id
	 * @param offset 当前起始行号
	 * @param limit 当前显示条数
	 * @return Message的List集合
	 */
	List<Message> findLetters(String conversationId, Integer offset, Integer limit);
	
	/**
	 * 查询某个会话所包含的私信数量
	 * @param conversationId 私信id
	 * @return 私信数量
	 */
	Integer findLetterCount(String conversationId);
	
	/**
	 * 查询用户私信未读数量
	 * @param userId 当前用户id
	 * @param conversationId  私信id
	 * @return 未读私信数量
	 */
	Integer findLetterUnreadCount(Integer userId, String conversationId);
	
	/**
	 * 新增消息
	 * @param message 需要新增的消息实体
	 */
	void addMessage(Message message);
	
	/**
	 * 修改消息的状态
	 * @param ids id的List集合
	 */
	void readMessage(List<Integer> ids);
	
	/**
	 * 查询某个主题下的最新通知
	 * @param userId 用户id
	 * @param topic 主题
	 * @return 返回最新通知信息
	 */
	Message findLatestNotice(Integer userId, String topic);
	
	/**
	 * 查询某个主题所包含的通知数量
	 * @param userId 用户id
	 * @param topic 主题
	 * @return 返回通知数量
	 */
	Integer findNoticeCount(Integer userId, String topic);
	
	/**
	 * 查询未读消息数量
	 * @param userId 用户id
	 * @param topic 主题
	 * @return 返回未读消息数量
	 */
	Integer findNoticeUnreadCount(Integer userId, String topic);
	
	/**
	 * 查询某个主题中包含的通知列表
	 * @param userId 用户id
	 * @param topic 主题
	 * @param offset 分页起始行
	 * @param limit 检索条数
	 * @return 返回通知信息集合
	 */
	List<Message> findNotices(Integer userId, String topic, Integer offset, Integer limit);
}
