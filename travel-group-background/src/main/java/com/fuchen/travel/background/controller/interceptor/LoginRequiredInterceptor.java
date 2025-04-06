package com.fuchen.travel.background.controller.interceptor;


import com.fuchen.travel.background.annotation.LoginRequired;
import com.fuchen.travel.background.util.HostHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author 伏辰
 * @date 2022/7/6
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

	@Resource
	private HostHolder hostHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			LoginRequired annotation = method.getAnnotation(LoginRequired.class);
			if (annotation != null && hostHolder.getUser() == null) {
				response.sendRedirect(request.getContextPath() + "/login");
				return false;
			}
		}
		return true;
	}
}
