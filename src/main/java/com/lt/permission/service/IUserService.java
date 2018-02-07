package com.lt.permission.service;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.lt.permission.dto.LoginDto;
import com.lt.permission.dto.UserDto;
import com.lt.permission.dto.UserQueryDto;
import com.lt.permission.model.User;
import com.lt.permission.vo.UserVo;

public interface IUserService {

	/**
	 * 分页查询用户
	 * 
	 * @param queryDto
	 * @return
	 */
	public UserVo findUsersByPage(UserQueryDto queryDto);

	/**
	 * 新增用户
	 * 
	 * @param dto
	 * @return
	 */
	public int addUser(UserDto dto);

	/**
	 * 验证登录信息
	 * 
	 * @param dto
	 * @return
	 */
	public JSONObject checkLogin(LoginDto dto);

	/**
	 * 根据用户帐号获取角色
	 * 
	 * @param username
	 */
	public Set<String> getRolesByUsername(String username);

	/**
	 * 根据账号获取用户信息
	 * 
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username);
}
