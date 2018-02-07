package com.lt.permission.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
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
import com.lt.permission.service.IUserService;
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
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(
				dto.getUsername(), dto.getPassword());
		token.setRememberMe(true);
		try {
			subject.login(token);
			jo.put("result", true);
			logger.info("======登陆成功=======");
		} catch (UnauthenticatedException ue) {
			logger.error("======登陆异常=======");
			jo.put("result", false);
			jo.put("msg", ue.getMessage());

		} catch (Exception e) {
			logger.error("======登陆异常=======");
			jo.put("result", false);
			jo.put("msg", e.getMessage());
		}
		// JSONObject loginJson = userService.checkLogin(dto);
		// boolean loginFlag = loginJson.getBoolean("result");
		// if (loginFlag) {
		// jo.put("result", true);
		// } else {
		// jo.put("result", false);
		// jo.put("msg", loginJson.getString("msg"));
		// }
		return jo.toString();
	}

	/**
	 * 进入首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String index() {
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
