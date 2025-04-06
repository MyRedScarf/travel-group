package com.fuchen.travel.background.config;

import com.fuchen.travel.background.controller.interceptor.LoginRequiredInterceptor;
import com.fuchen.travel.background.controller.interceptor.LoginTicketInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 伏辰
 * @date 2022/7/4
 * 拦截器配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private LoginTicketInterceptor loginTicketInterceptor;
	
	@Autowired
	private LoginRequiredInterceptor loginRequiredInterceptor;

	

	@Override
	public void addInterceptors(InterceptorRegistry registry){

		registry.addInterceptor(loginTicketInterceptor)
				.excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/login");
		
		registry.addInterceptor(loginRequiredInterceptor)
				.excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

	}
	
	
}
