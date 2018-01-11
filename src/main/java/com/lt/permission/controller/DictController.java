package com.lt.permission.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 数据字典控制器
 * 
 * @author lantian
 * 
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 进入数据字典管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dictMgt")
	public String roleMgt() {
		return "/dict/dict_mgt";
	}
}
