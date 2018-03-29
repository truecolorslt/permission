package com.lt.permission.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lt.permission.model.User;
import com.lt.permission.service.IUserService;
import com.lt.permission.shiro.token.ShiroToken;

public class ShiroRealm extends AuthorizingRealm {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ShiroRealm() {
		super();
	}

	@Autowired
	private IUserService userService;

	/**
	 * 用户登陆认证(提供账户信息返回认证信息)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		// principals：身份，即主体的标识属性,一般是用户名 / 密码 / 手机号。
		// credentials：证明 / 凭证，即只有主体知道的安全值，如密码 / 数字证书等。
		ShiroToken token = (ShiroToken) authcToken;
		String username = token.getUsername();
		String password = token.getPwd();
		// 判断用户账号是否存在
		User userExist = userService.getUserByUsername(username);
		if (userExist != null) {
			// 判断用户账号密码是否有效
			User userValid = userService.getUserByUsernameAndPwd(username,
					password);
			if (userValid != null) {
				// 设置session
				// Session session = SecurityUtils.getSubject().getSession();
				// session.setAttribute(username, userValid);

				AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
						userValid, password, this.getName());
				return authenticationInfo;
			} else {
				// 密码错误
				throw new IncorrectCredentialsException();
			}
		} else {
			// 用户账号不存在
			throw new UnknownAccountException();
		}
	}

	/**
	 * 用户授权认证(提供用户信息返回权限信息)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {
		String userName = principalCollection.getPrimaryPrincipal().toString();
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setRoles(userService
				.getRolesByUsername(userName));
		return simpleAuthorizationInfo;
	}

}
