package com.fuchen.travel.service.impl;

import com.fuchen.travel.entity.Message;
import com.fuchen.travel.mapper.MessageMapper;
import com.fuchen.travel.service.MessageService;
import com.fuchen.travel.util.SensitiveFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author Fu chen
 * @date 2022/12/14
 * 私信-service层-实现类
 */
@Service
public class MessageServiceImpl implements MessageService {
	
	private final MessageMapper messageMapper;
	
	private final SensitiveFilter sensitiveFilter;

	public MessageServiceImpl(MessageMapper messageMapper, SensitiveFilter sensitiveFilter) {
		this.messageMapper = messageMapper;
		this.sensitiveFilter = sensitiveFilter;
	}

	/**
	 * 查询当前用户的会话列表，针对每个会话只返回最新的私信
	 * @param userId 当前用户的id
	 * @param offset 当前页的起始行
	 * @param limit 当前页的显示条数
	 * @return Message的List集合
	 */
	@Override
	public List<Message> findConversations(Integer userId, Integer offset, Integer limit) {
		return messageMapper.selectConversations(userId, offset, limit);
	}

	/**
	 * 查询用户的会话数量
	 * @param userId 当前用户id
	 * @return 查询数量
	 */
	@Override
	public Integer findConversationCount(Integer userId) {
		return messageMapper.selectConversationCount(userId);
	}

	/**
	 * 查询某个会话所包含的私信列表
	 * @param conversationId 私信id
	 * @param offset 当前起始行号
	 * @param limit 当前显示条数
	 * @return Message的List集合
	 */
	@Override
	public List<Message> findLetters(String conversationId, Integer offset, Integer limit) {
		return messageMapper.selectLetters(conversationId, offset, limit);
	}

	/**
	 * 查询某个会话所包含的私信数量
	 * @param conversationId 私信id
	 * @return 私信数量
	 */
	@Override
	public Integer findLetterCount(String conversationId) {
		return messageMapper.selectLetterCount(conversationId);
	}

	/**
	 * 查询用户私信未读数量
	 * @param userId 当前用户id
	 * @param conversationId  私信id
	 * @return 未读私信数量
	 */
	@Override
	public Integer findLetterUnreadCount(Integer userId, String conversationId) {
		return messageMapper.selectLetterUnreadCount(userId, conversationId);
	}

	/**
	 * 新增消息
	 * @param message 需要新增的消息实体
	 */
	@Override
	public void addMessage(Message message) {
		message.setContent(HtmlUtils.htmlEscape(message.getContent()));
		message.setContent(sensitiveFilter.filter(message.getContent()));
		messageMapper.insertMessage(message);
	}

	/**
	 * 修改消息的状态
	 * @param ids id的List集合
	 */
	@Override
	public void readMessage(List<Integer> ids) {
		messageMapper.updateStatus(ids, 1);
	}

	/**
	 * 查询某个主题下的最新通知
	 * @param userId 用户id
	 * @param topic 主题
	 * @return 返回最新通知信息
	 */
	@Override
	public Message findLatestNotice(Integer userId, String topic) {
		return messageMapper.selectLatestNotice(userId, topic);
	}

	/**
	 * 查询某个主题所包含的通知数量
	 * @param userId 用户id
	 * @param topic 主题
	 * @return 返回通知数量
	 */
	@Override
	public Integer findNoticeCount(Integer userId, String topic) {
		return messageMapper.selectNoticeCount(userId, topic);
	}

	/**
	 * 查询未读消息数量
	 * @param userId 用户id
	 * @param topic 主题
	 * @return 返回未读消息数量
	 */
	@Override
	public Integer findNoticeUnreadCount(Integer userId, String topic) {
		return messageMapper.selectNoticeUnreadCount(userId, topic);
	}

	/**
	 * 查询某个主题中包含的通知列表
	 * @param userId 用户id
	 * @param topic 主题
	 * @param offset 分页起始行
	 * @param limit 检索条数
	 * @return 返回通知信息集合
	 */
	@Override
	public List<Message> findNotices(Integer userId, String topic, Integer offset, Integer limit) {
		return messageMapper.selectNotices(userId, topic, offset, limit);
	}
}
