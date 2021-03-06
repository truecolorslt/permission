package com.lt.permission.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lt.permission.model.Role;
import com.lt.permission.model.User;

public interface RoleDao {
	int deleteByPrimaryKey(String rid);

	int insert(Role record);

	int insertSelective(Role record);

	Role selectByPrimaryKey(String rid);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);

	public List<Role> findRolesByPage(Map<String, Object> map);

	public int findRolesCount(Map<String, Object> map);

	public int logicDeleteRole(String rid);
	
	public List<Role> getRoleByUid(String uid);
	
	public List<Role> getNonRoleByUid(String uid);
	
	public List<Role> getRolesByUsername(String username);

	public List<Role> getRolesByFid(String fid);
	
	
}