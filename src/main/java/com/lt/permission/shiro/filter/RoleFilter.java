package com.lt.permission.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 角色判断校验
 * 
 * @author lantian
 * 
 */
public class RoleFilter extends AccessControlFilter {

	static final String LOGIN_URL = "/";
	static final String UNAUTHORIZED_URL = "/unauthorized.jsp";

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = getSubject(request, response);
		String[] rolesArray = (String[]) mappedValue;

		if (rolesArray == null || rolesArray.length == 0) {
			return true;
		}
		for (int i = 0; i < rolesArray.length; i++) {
			if (subject.hasRole(rolesArray[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {

		Subject subject = getSubject(request, response);
		if (subject.getPrincipal() == null) {// 表示没有登录，重定向到登录页面
			saveRequest(request);
			WebUtils.issueRedirect(request, response, LOGIN_URL);
		} else {
			if (StringUtils.hasText(UNAUTHORIZED_URL)) {// 如果有未授权页面跳转过去
				WebUtils.issueRedirect(request, response, UNAUTHORIZED_URL);
			} else {// 否则返回401未授权状态码
				WebUtils.toHttp(response).sendError(
						HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		return false;
	}

}
