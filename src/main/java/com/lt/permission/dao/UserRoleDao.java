package com.lt.permission.dao;

import com.lt.permission.model.UserRole;

public interface UserRoleDao {
    int deleteByPrimaryKey(String urid);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(String urid);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}