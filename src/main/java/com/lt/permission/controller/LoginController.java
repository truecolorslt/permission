package com.lt.permission.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 登录逻辑控制器
 * 
 * @author lantian
 * 
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

	@RequestMapping(value = "/doLogin")
	@ResponseBody
	public String doLogin() {
		JSONObject jo = new JSONObject();
		jo.put("result", true);
		return jo.toString();
	}
}
