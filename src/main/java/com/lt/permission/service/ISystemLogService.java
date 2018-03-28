package com.lt.permission.service;

import com.lt.permission.dto.LogQueryDto;
import com.lt.permission.model.SystemLog;
import com.lt.permission.vo.LogVo;

public interface ISystemLogService {
	int deleteSystemLog(String id);

	int insert(SystemLog record);

	int insertTest(SystemLog record);

	SystemLog selectSystemLog(String id);

	int updateSystemLog(SystemLog record);
	/**
	 * 分页查询日志
	 * 
	 * @param queryDto
	 * @return
	 */
	public LogVo findLogsByPage(LogQueryDto queryDto);
}
