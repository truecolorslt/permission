package com.lt.permission.service;

import com.lt.permission.model.SystemLog;

public interface ISystemLogService {
	int deleteSystemLog(String id);

	int insert(SystemLog record);

	int insertTest(SystemLog record);

	SystemLog selectSystemLog(String id);

	int updateSystemLog(SystemLog record);
}
