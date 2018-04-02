package com.lt.permission.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lt.permission.annotation.Log;
import com.lt.permission.common.DictConstants;
import com.lt.permission.dto.LoginDto;
import com.lt.permission.model.User;
import com.lt.permission.service.IUserService;
import com.lt.permission.shiro.token.TokenManager;
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

	/**
	 * 用户登录
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/doLogin")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_LOGIN, logDesc = "用户登录")
	@ResponseBody
	public String doLogin(@RequestBody LoginDto dto) {
		// dto.setPassword(MD5Util.getMD5(dto.getPassword()));
		JSONObject jo = new JSONObject();
		// jo.put("result", true);
		try {
			// 登陆成功
			TokenManager.login(dto, false);
			jo.put("result", true);
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

	/**
	 * 进入登录画面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login() {
		return "/login.jsp";
	}
}
