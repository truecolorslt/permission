package com.lt.permission.shiro.token;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.lt.permission.dto.LoginDto;
import com.lt.permission.model.User;
import com.lt.permission.shiro.ShiroRealm;
import com.lt.permission.util.SpringContextUtil;

/**
 * Shiro管理下的Token工具类
 * 
 * @author lantian
 * 
 */
public class TokenManager {
	// 用户登录管理
	public static final ShiroRealm realm = SpringContextUtil.getBean(
			"shiroRealm", ShiroRealm.class);

	/**
	 * 获取当前登录的用户User对象
	 * 
	 * @return
	 */
	public static User getToken() {
		User token = null;
		try {
			token = (User) SecurityUtils.getSubject().getPrincipal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	/**
	 * 登录
	 * 
	 * @param user
	 * @param rememberMe
	 * @return
	 */
	public static User login(LoginDto user, Boolean rememberMe) {
		ShiroToken token = new ShiroToken(user.getUsername(),
				user.getPassword());
		token.setRememberMe(rememberMe);
		SecurityUtils.getSubject().login(token);
		return getToken();
	}

	/**
	 * 获取当前用户的Session
	 * 
	 * @return
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 把值放入到当前登录用户的Session里
	 * 
	 * @param key
	 * @param value
	 */
	public static void setVal2Session(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 从当前登录用户的Session里取值
	 * 
	 * @param key
	 * @return
	 */
	public static Object getVal2Session(Object key) {
		return getSession().getAttribute(key);
	}
}
