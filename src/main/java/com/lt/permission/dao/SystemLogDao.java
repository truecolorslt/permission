package com.lt.permission.dao;

import java.util.List;
import java.util.Map;

import com.lt.permission.model.Dict;
import com.lt.permission.model.SystemLog;

public interface SystemLogDao {
	int deleteByPrimaryKey(String lid);

	int insert(SystemLog record);

	int insertSelective(SystemLog record);

	SystemLog selectByPrimaryKey(String lid);

	int updateByPrimaryKeySelective(SystemLog record);

	int updateByPrimaryKey(SystemLog record);

	public List<SystemLog> findLogsByPage(Map<String, Object> map);

	public int findLogsCount(Map<String, Object> map);
}