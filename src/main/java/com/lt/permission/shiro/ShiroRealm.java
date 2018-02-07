package com.lt.permission.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lt.permission.model.User;
import com.lt.permission.service.IUserService;

public class ShiroRealm extends AuthorizingRealm {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IUserService userService;

	/**
	 * 用户授权认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {
		logger.info("======用户授权认证======");
		String userName = principalCollection.getPrimaryPrincipal().toString();
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setRoles(userService
				.getRolesByUsername(userName));
		return simpleAuthorizationInfo;
	}

	/**
	 * 用户登陆认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		logger.info("======用户登陆认证======");
		String userName = authenticationToken.getPrincipal().toString();
		User user = userService.getUserByUsername(userName);
		if (user != null) {
			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
					user.getUsername(), user.getPassword(), this.getName());
			return authenticationInfo;
		} else {
			throw new UnauthenticatedException("帐号密码错误");
		}
	}
}
