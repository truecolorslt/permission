package com.lt.permission.service;

import java.util.List;
import java.util.Map;

import com.lt.permission.dto.RoleDto;
import com.lt.permission.dto.RoleQueryDto;
import com.lt.permission.model.RoleFunction;
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

	/**
	 * 给角色设置权限
	 * 
	 * @param rid
	 * @param fids
	 * @return
	 */
	public int setFunctionForRole(String rid, String[] fids);

	/**
	 * 获取角色对应的菜单
	 * 
	 * @param rid
	 * @return
	 */
	public List<Map<String,Object>> getFunctionByRole(String rid);
}
