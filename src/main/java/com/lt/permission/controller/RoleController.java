package com.lt.permission.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 角色控制器
 * 
 * @author lantian
 * 
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 进入角色管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/roleMgt")
	public String roleMgt() {
		return "/role/role_mgt";
	}
}
