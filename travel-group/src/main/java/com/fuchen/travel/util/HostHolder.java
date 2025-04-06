package com.fuchen.travel.util;

import com.fuchen.travel.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author Fu chen
 * @date 2022/12/4
 * 持有用户信息，用于代替session对象
 */
@Component
public class HostHolder {

	private final ThreadLocal<User> user = new ThreadLocal<>();
	
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
