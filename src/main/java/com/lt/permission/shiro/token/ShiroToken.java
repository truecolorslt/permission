package com.lt.permission.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroToken extends UsernamePasswordToken implements
		java.io.Serializable {

	public ShiroToken(String username, String pwd) {
		super(username, pwd);
		this.pwd = pwd;
	}

	/** 登录密码[字符串类型] 因为父类是char[] ] **/
	private String pwd;

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
