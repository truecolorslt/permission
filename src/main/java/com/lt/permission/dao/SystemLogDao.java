package com.lt.permission.dao;

import com.lt.permission.model.SystemLog;

public interface SystemLogDao {
	int deleteByPrimaryKey(String lid);

	int insert(SystemLog record);

	int insertSelective(SystemLog record);

	SystemLog selectByPrimaryKey(String lid);

	int updateByPrimaryKeySelective(SystemLog record);

	int updateByPrimaryKey(SystemLog record);
}