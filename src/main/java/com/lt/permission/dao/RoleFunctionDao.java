package com.lt.permission.dao;

import com.lt.permission.model.RoleFunction;

public interface RoleFunctionDao {
    int deleteByPrimaryKey(String rfid);

    int insert(RoleFunction record);

    int insertSelective(RoleFunction record);

    RoleFunction selectByPrimaryKey(String rfid);

    int updateByPrimaryKeySelective(RoleFunction record);

    int updateByPrimaryKey(RoleFunction record);
}