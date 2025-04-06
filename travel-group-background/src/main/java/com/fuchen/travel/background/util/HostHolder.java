package com.fuchen.travel.background.util;

import com.fuchen.travel.background.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author 伏辰
 * @date 2022/7/4
 * 持有用户信息，用于代替session对象
 */
@Component
public class HostHolder {

	private ThreadLocal<User> user = new ThreadLocal<>();
	
	public void setUser(User user){
		this.user.set(user);
	}
	
	public User getUser() {
	    return user.get();
	}
	
	public void clear() {
	    user.remove();
	}
}
