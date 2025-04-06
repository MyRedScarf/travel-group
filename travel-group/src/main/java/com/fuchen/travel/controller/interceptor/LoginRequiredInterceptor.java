package com.fuchen.travel.controller.interceptor;

import com.fuchen.travel.annotation.LoginRequired;
import com.fuchen.travel.util.HostHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Fu chen
 * @date 2022/12/6
 * 页面拦截器
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
	
	private final HostHolder hostHolder;

	public LoginRequiredInterceptor(HostHolder hostHolder) {
		this.hostHolder = hostHolder;
	}

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
