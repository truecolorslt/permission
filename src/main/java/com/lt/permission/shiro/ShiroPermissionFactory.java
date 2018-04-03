package com.lt.permission.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.lt.permission.service.IRoleService;

public class ShiroPermissionFactory extends ShiroFilterFactoryBean {
	/** 记录配置中的过滤链 */
	public static String definition = "";
	@Autowired
	IRoleService roleServiceImpl;

	/**
	 * 初始化设置过滤链
	 */
	@Override
	public void setFilterChainDefinitions(String definitions) {
		definition = definitions;// 记录配置的静态过滤链
		// 可从数据库读取后，添加至过滤链

		super.setFilterChainDefinitions(definitions);
	}

}
