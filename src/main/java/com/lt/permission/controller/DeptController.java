package com.lt.permission.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 部门控制器
 * 
 * @author lantian
 * 
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 进入部门管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deptMgt")
	public String roleMgt() {
		return "/dept/dept_mgt";
	}
}
