package com.lt.permission.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lt.permission.dto.LoginDto;
import com.lt.permission.model.User;
import com.lt.permission.service.IUserService;
import com.lt.permission.shiro.token.ShiroToken;
import com.lt.permission.util.MD5Util;

/**
 * 登录逻辑控制器
 * 
 * @author lantian
 * 
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/doLogin")
	@ResponseBody
	public String doLogin(@RequestBody LoginDto dto) {
		dto.setPassword(MD5Util.getMD5(dto.getPassword()));
		JSONObject jo = new JSONObject();
		// 获取Subject单例对象
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			// 使用用户的登录信息创建令牌
			ShiroToken token = new ShiroToken(dto.getUsername(),
					dto.getPassword());
			// token.setRememberMe(false);
			try {
				// 登陆
				subject.login(token);
				jo.put("result", true);
				// 登陆成功
				User user = userService.getUserByUsername(dto.getUsername());
				subject.getSession().setAttribute("user", user);
			} catch (IncorrectCredentialsException ice) {
				// 捕获密码错误异常
				jo.put("result", false);
				jo.put("msg", "密码错误！");
			} catch (UnknownAccountException uae) {
				// 捕获未知用户名异常
				jo.put("result", false);
				jo.put("msg", "用户帐号不存在！");
			} catch (ExcessiveAttemptsException eae) {
				// 捕获错误登录过多的异常
				jo.put("result", false);
				jo.put("msg", "用户帐号登录过多！");
			} catch (AuthenticationException e) {
				jo.put("result", false);
				jo.put("msg", "身份验证失败！");
			} catch (Exception e) {
				jo.put("result", false);
				jo.put("msg", e.getMessage());
			}
		} else {
			jo.put("result", true);
		}

		return jo.toString();
	}

	/**
	 * 进入首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String main() {
		return "/main";
	}

	/**
	 * 进入欢迎页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/welcome")
	public String welcome() {
		return "/welcome";
	}

}
