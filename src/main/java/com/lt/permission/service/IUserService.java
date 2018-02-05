package com.lt.permission.service;

import com.lt.permission.dto.UserDto;
import com.lt.permission.dto.UserQueryDto;
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
}
