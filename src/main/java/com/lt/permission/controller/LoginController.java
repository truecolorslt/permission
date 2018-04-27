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
import com.lt.permission.common.ResultEnum;
import com.lt.permission.common.ResultObject;
import com.lt.permission.dto.LoginDto;
import com.lt.permission.exception.PermissionException;
import com.lt.permission.model.User;
import com.lt.permission.service.IUserService;
import com.lt.permission.shiro.token.TokenManager;
import com.lt.permission.util.MD5Util;
import com.lt.permission.util.ResultUtil;

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
	public ResultObject<User> doLogin(@RequestBody LoginDto dto) {
		// 登陆成功
		try {
			TokenManager.login(dto, false);
		} catch (IncorrectCredentialsException ice) {
			// 捕获密码错误异常
			throw new PermissionException(ResultEnum.RESULT_CODE_PASSWORD_ERROR);
		} catch (UnknownAccountException uae) {
			// 捕获未知用户名异常
			throw new PermissionException(ResultEnum.RESULT_CODE_USER_NOT_EXIST);
		} catch (ExcessiveAttemptsException eae) {
			// 捕获错误登录过多的异常
			throw new PermissionException(ResultEnum.RESULT_CODE_LOGIN_MUCH);
		} catch (AuthenticationException e) {
			throw new PermissionException(ResultEnum.RESULT_CODE_LOGIN_FAIL);
		} catch (Exception e) {
			throw new PermissionException(ResultEnum.RESULT_CODE_SYS_ERROR);
		}
		return ResultUtil.success();
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
