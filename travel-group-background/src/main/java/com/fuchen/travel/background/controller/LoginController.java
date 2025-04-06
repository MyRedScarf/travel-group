package com.fuchen.travel.background.controller;

import com.fuchen.travel.background.service.UserService;
import com.fuchen.travel.background.util.TravelConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author 伏辰
 * @Date 2023/1/5
 */
@Controller
public class LoginController implements TravelConstant {

    @Autowired
    private UserService userService;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @GetMapping("/login")
    public String login() {
        return "/pages/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/pages/register";
    }

    @PostMapping("/login")
    public String login(String username, String password, boolean rememberMe, Model model, HttpServletResponse response) {

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
            return "/pages/login";
        }
    }

    /**
     * 退出登录
     * @param ticket cookie
     * @return
     */
    @GetMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }
}
