package com.fuchen.travel.background.controller.advice;


import com.fuchen.travel.background.util.TravelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 伏辰
 * @date 2022/7/14
 * 异常通知
 */
@ControllerAdvice(annotations = Controller.class)   //全局Controller配置
public class ExceptionAdvice {
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);
	@ExceptionHandler(Exception.class)  //异常处理类
	public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.error("服务器发生异常：" + e.getMessage());
		for (StackTraceElement element : e.getStackTrace()) {
			log.error(element.toString());
		}
		
		String requestHeader = request.getHeader("x-requested-with");
		//异步请求
		if ("XMLHttpRequest".equals(requestHeader)) {
			response.setContentType("application/json;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(TravelUtil.getJsonString(1, "服务器异常!"));
		} else {
		    response.sendRedirect(request.getContextPath() + "/error");
		}
	}
}
