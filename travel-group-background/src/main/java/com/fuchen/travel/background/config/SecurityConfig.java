package com.fuchen.travel.background.config;

import com.fuchen.travel.background.util.TravelConstant;
import com.fuchen.travel.background.util.TravelUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 伏辰
 * @date 2022/7/22
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements TravelConstant {
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//授权
		http.authorizeRequests()
				.antMatchers()
				.hasAnyAuthority(AUTHORITY_ADMIN)
				.anyRequest().permitAll()
				.and().csrf().disable();
		//权限不够时处理
		http.exceptionHandling()
				.authenticationEntryPoint(new AuthenticationEntryPoint() {
					//没有登录
					@Override
					public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
						String xRequestWith = request.getHeader("x-requested-with");
						if ("XMLHttpRequest".equals(xRequestWith)) {
							response.setContentType("application/plain;charset=utf-8");
							response.getWriter().write(TravelUtil.getJsonString(1, "您还没有登录!"));
						} else {
							response.sendRedirect(request.getContextPath() + "/index");
						}
					}
				})
				.accessDeniedHandler(new AccessDeniedHandler() {
					//权限不足
					@Override
					public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
						String xRequestWith = request.getHeader("x-requested-with");
						if ("XMLHttpRequest".equals(xRequestWith)) {
							response.setContentType("application/plain;charset=utf-8");
							response.getWriter().write(TravelUtil.getJsonString(1, "您没有访问此功能的权限!"));
						} else {
							response.sendRedirect(request.getContextPath() + "/index");
						}
					}
				});
		//Security底层默认会拦截/logout请求，进行退出处理
		//覆盖其默认的逻辑，才能执行执行我们自己的退出代码
		http.logout().logoutUrl("/securitylogout");
	}
}
