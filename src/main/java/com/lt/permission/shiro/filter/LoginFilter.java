package com.lt.permission.shiro.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lt.permission.model.User;
import com.lt.permission.shiro.token.TokenManager;
import com.lt.permission.util.ShiroFilterUtils;

/**
 * 判断登录
 * 
 * @author lantian
 * 
 */
public class LoginFilter extends AccessControlFilter {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		User token = TokenManager.getToken();
		if (null != token || isLoginRequest(request, response)) {// &&
			return Boolean.TRUE;
		}
		if (ShiroFilterUtils.isAjax(request)) {// ajax请求
			Map<String, String> resultMap = new HashMap<String, String>();
			logger.debug("当前用户没有登录，并且是Ajax请求！");
			resultMap.put("result", "300");
			resultMap.put("msg", "当前用户没有登录");// 当前用户没有登录！
			ShiroFilterUtils.out(response, resultMap);
		}
		return Boolean.FALSE;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		// 保存Request和Response 到登录后的链接
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.FALSE;
	}

}
