package com.lt.permission.service;

import com.lt.permission.dto.RoleDto;
import com.lt.permission.dto.RoleQueryDto;
import com.lt.permission.vo.RoleVo;

public interface IRoleService {

	/**
	 * 分页查询用户
	 * 
	 * @param queryDto
	 * @return
	 */
	public RoleVo findRolesByPage(RoleQueryDto queryDto);

	/**
	 * 新增
	 * 
	 * @param dto
	 * @return
	 */
	public int addRole(RoleDto dto);

	/**
	 * 编辑
	 * 
	 * @param dto
	 * @return
	 */
	public int updateRole(RoleDto dto);

	/**
	 * 逻辑删除
	 * 
	 * @param uid
	 * @return
	 */
	public int deleteRole(String rid);
}
