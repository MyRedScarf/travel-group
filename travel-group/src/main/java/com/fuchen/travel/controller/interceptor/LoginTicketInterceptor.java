package com.fuchen.travel.controller.interceptor;

import com.fuchen.travel.entity.LoginTicket;
import com.fuchen.travel.entity.User;
import com.fuchen.travel.service.UserService;
import com.fuchen.travel.util.CookieUtil;
import com.fuchen.travel.util.HostHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Fu chen
 * @date 2022/12/4
 * 登录凭证拦截器
 */
@Component
public class LoginTicketInterceptor implements HandlerInterceptor {
	
	private final UserService userService;
	
	private final HostHolder hostHolder;

	public LoginTicketInterceptor(UserService userService, HostHolder hostHolder) {
		this.userService = userService;
		this.hostHolder = hostHolder;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//从cookie中获取数据
		String ticket = CookieUtil.getValue(request, "ticket");
		
		if (ticket != null) {
			//查询凭证
			LoginTicket loginTicket = userService.findLoginTicket(ticket);
			//检查凭证是否有效
			if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
				User user = userService.findById(loginTicket.getUserId());
				//在本次请求中持有用户
				hostHolder.setUser(user);
				//构建用户认证的结果，并存入SecurityContext， 以便于Security进行授权
				Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), userService.getAuthorities(user.getId()));
				SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
				
			}
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		User user = hostHolder.getUser();
		if(user != null && modelAndView != null) {
			modelAndView.addObject("loginUser", user);
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		hostHolder.clear();
		SecurityContextHolder.clearContext();
	}
}
