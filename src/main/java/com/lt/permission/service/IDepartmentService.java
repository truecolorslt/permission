package com.lt.permission.service;

import java.util.List;

import com.lt.permission.dto.DepartmentDto;
import com.lt.permission.model.Department;

public interface IDepartmentService {
	/**
	 * 查询所有菜单
	 * 
	 * @return
	 */
	public List<Department> findAllDepartmentTrees();

	/**
	 * 根据id获取菜单对象
	 * 
	 * @return
	 */
	public Department getDepartment(String did);

	/**
	 * 新增部门
	 * 
	 * @return
	 */
	public int addDepartment(DepartmentDto dto);

	/**
	 * 删除部门
	 * 
	 * @param did
	 * @return
	 */
	public int deleteDepartment(String did);

	/**
	 * 修改部门
	 * 
	 * @return
	 */
	public int updateDepartment(DepartmentDto dto);
}
