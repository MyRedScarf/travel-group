package com.fuchen.travel.config;

import com.fuchen.travel.controller.interceptor.LoginRequiredInterceptor;
import com.fuchen.travel.controller.interceptor.LoginTicketInterceptor;
import com.fuchen.travel.controller.interceptor.MessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Fu chen
 * @date 2022/12/4
 * 拦截器配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	

	@Autowired
	private LoginTicketInterceptor loginTicketInterceptor;

	@Autowired
	private LoginRequiredInterceptor loginRequiredInterceptor;
	
	@Autowired
	private MessageInterceptor messageInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){

		registry.addInterceptor(loginTicketInterceptor)
				.excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");
		
		registry.addInterceptor(loginRequiredInterceptor)
				.excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");
		
		registry.addInterceptor(messageInterceptor)
				.excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

	}
	
	
}
