package com.fuchen.travel.controller.interceptor;

import com.fuchen.travel.entity.User;
import com.fuchen.travel.service.MessageService;
import com.fuchen.travel.util.HostHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Fu chen
 * @date 2022/12/20
 * 消息拦截器
 */
@Component
public class MessageInterceptor implements HandlerInterceptor {
	private final HostHolder hostHolder;
	
	private final MessageService messageService;

	public MessageInterceptor(HostHolder hostHolder, MessageService messageService) {
		this.hostHolder = hostHolder;
		this.messageService = messageService;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		User user = hostHolder.getUser();
		if (user != null && modelAndView != null) {
			Integer letterUnreadCount = messageService.findLetterUnreadCount(user.getId(), null);
			Integer noticeUnreadCount = messageService.findNoticeUnreadCount(user.getId(), null);
			modelAndView.addObject("allUnreadCount", letterUnreadCount + noticeUnreadCount);
		}
	}
}
