package com.lt.permission.service;

import java.util.Set;

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
	public User getUserByUsernameAndPwd(String username, String password);

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

	/**
	 * 逻辑删除用户
	 * 
	 * @param uid
	 * @return
	 */
	public int deleteUser(String uid);

	/**
	 * 编辑用户
	 * 
	 * @param dto
	 * @return
	 */
	public int updateUser(UserDto dto);

	/**
	 * 给用户设置角色
	 * 
	 * @param uid
	 * @param rids
	 * @return
	 */
	public int setUserRole(String uid, String[] rids);
}
