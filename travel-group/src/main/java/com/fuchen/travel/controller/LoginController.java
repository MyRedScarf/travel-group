package com.fuchen.travel.controller;

import com.fuchen.travel.entity.User;
import com.fuchen.travel.service.UserService;
import com.fuchen.travel.util.TravelConstant;
import com.fuchen.travel.util.TravelUtil;
import com.fuchen.travel.util.RedisKeyUtil;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Fu chen
 * @date 2022/11/2
 * 登录-controller层
 */
@Slf4j
@Controller
public class LoginController implements TravelConstant {

	@Resource
	private UserService userService;

	@Resource
	private Producer kaptchaProducer;

	@Resource
	private RedisTemplate redisTemplate;

	@Value("${server.servlet.context-path}")
	private String contextPath;


	/**
	 * 前往注册页面
	 * @return 注册页面
	 */
	@GetMapping("/register")
	public String getRegisterPage(){
		return "/site/register";
	}
	
	/**
	 * 前往登录页面
	 * @return 登录页面
	 */
	@GetMapping("/login")
	public String getLoginPage(){
		return "/site/login";
	}
	
	/**
	 * 接收用户注册请求
	 * @param model 页面模型
	 * @param user User对象
	 * @return 相应页面
	 */
	@PostMapping("/register")
	public String register(Model model, User user){
		Map<String, Object> map = userService.register(user);
		
		if (map == null || map.isEmpty()) {
			model.addAttribute("msg","注册成功，请前往邮箱进行激活~~~");
			model.addAttribute("target","/index");
			return "/site/operate-result";
		} else {
			model.addAttribute("usernameMsg", map.get("usernameMsg"));
			model.addAttribute("passwordMsg", map.get("passwordMsg"));
			model.addAttribute("emailMsg", map.get("emailMsg"));
			return "/site/register";
		}
	}
	
	/**
	 * 用户激活请求
	 * @param model 模型
	 * @param userId 用户id
	 * @param code 激活码
	 * @return 激活结果页面
	 */
	@GetMapping("/activation/{userId}/{code}")
	public String activation(Model model, @PathVariable("userId") Integer userId, @PathVariable("code") String code){
		Integer result = userService.activation(userId, code);
		if (result == ACTIVATION_SUCCESS) {
			model.addAttribute("msg","激活成功！！！");
			model.addAttribute("target","/login");
		} else if (result == ACTIVATION_REPEAT) {
			model.addAttribute("msg","无效操作，请勿重复激活！！！");
			model.addAttribute("target","/index");
		} else {
			model.addAttribute("msg","激活失败,激活码有误！！！");
			model.addAttribute("target","/index");
		}
		return "/site/operate-result";
	}
	
	/**
	 * 验证码图片
	 * @param response 响应体
	 * @param session 用于将验证码存入session
	 */
	@GetMapping("/kaptcha")
	public void getKaptcha(HttpServletResponse response, HttpSession session){
		//生成验证码
		String text = kaptchaProducer.createText();
		BufferedImage image = kaptchaProducer.createImage(text);
		
		//验证码归属
		String kaptchaOwner = TravelUtil.generateUUID();
		Cookie cookie = new Cookie("kaptchaOwner", kaptchaOwner);
		cookie.setMaxAge(60);
		cookie.setPath(contextPath);
		response.addCookie(cookie);
		//将验证码存入Redis
		String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
		redisTemplate.opsForValue().set(redisKey, text, 60, TimeUnit.SECONDS);
		
		//将图片输出到浏览器
		response.setContentType("image/png");
		try {
			OutputStream outputStream = response.getOutputStream();
			ImageIO.write(image, "png", outputStream);
		} catch (IOException e) {
			log.error("响应验证码失败" + e.getMessage());
		}
	}

	/**
	 * 登录
	 * @param username 用户名
	 * @param password 密码
	 * @param code 验证码
	 * @param rememberMe 记住我
	 * @param model 模型
	 * @param response 响应体
	 * @param kaptchaOwner 验证码
	 * @return 登录成功进入index页面，否则返回login页面
	 */
	@PostMapping("/login")
	public String login(String username, String password, String code, boolean rememberMe, Model model, /*HttpSession session,*/ HttpServletResponse response, @CookieValue("kaptchaOwner") String kaptchaOwner){
		
		String kaptcha = null;
		if (StringUtils.isNotBlank(kaptchaOwner)) {
			String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
			kaptcha = (String)redisTemplate.opsForValue().get(redisKey);
		}
		
		if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
			model.addAttribute("codeMsg", "验证码错误！");
			return "/site/login";
		}
		
		//检查账号、密码
		int expireSeconds = rememberMe ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
		Map<String, Object> map = userService.login(username, password, expireSeconds);
		if (map.containsKey("ticket")) {
			Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
			cookie.setPath(contextPath);
			cookie.setMaxAge(expireSeconds);
			response.addCookie(cookie);
			return "redirect:/index";
		} else {
			model.addAttribute("usernameMsg", map.get("usernameMsg"));
			model.addAttribute("passwordMsg", map.get("passwordMsg"));
			return "/site/login";
		}
	}

	/**
	 * 退出登录
	 * @param ticket cookie
	 * @return 重定向到login页面
	 */
	@GetMapping("/logout")
	public String logout(@CookieValue("ticket") String ticket){
		userService.logout(ticket);
		SecurityContextHolder.clearContext();
		return "redirect:/login";
	}


	/**
	 * 进入forget页面
	 * @return 返回forget页面
	 */
	@GetMapping("/forget")
	public String getForget() {
		return "/site/forget";
	}


	/**
	 * 发送邮箱验证码
	 */
	@PostMapping("/verificationCode")
	@ResponseBody
	public void getVerificationCode(Model model, String email) {

		if (!(email == null || email.isEmpty())) {
			userService.verificationCode(email);
			model.addAttribute("msg", "验证码已发送，请前往邮箱查看~~~");
		} else {
			model.addAttribute("msg", "邮箱为空！");
		}
	}

	/**
	 * 重置密码
	 * @param email 邮箱
	 * @param code 验证码
	 * @param password 新密码
	 */
	@PostMapping("/resetPassword")
	public String resetPassword(Model model, String email, String code, String password){

		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("重置密码：email不允许空！");
		}
		if (code == null || code.isEmpty()) {
			throw new IllegalArgumentException("重置密码：code不允许空！");
		}
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("重置密码：password不允许空！");
		}

		String verificationCode = redisTemplate.opsForValue().get("verificationCode").toString();
		if (!verificationCode.equals(code)) {
			model.addAttribute("msg", "验证码错误！");
		}
		User user = userService.updatePassword(email, password);


		return "/site/login";
	}

}
