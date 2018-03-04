package com.lt.permission.dao;

import java.util.List;

import com.lt.permission.model.Department;

public interface DepartmentDao {
    int deleteByPrimaryKey(String did);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(String did);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
    
    public List<Department> getAllDepartment();

	public int logicDeleteDepartment(String did);
}