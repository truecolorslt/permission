package com.lt.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lt.permission.dao.SystemLogDao;
import com.lt.permission.dto.LogQueryDto;
import com.lt.permission.model.SystemLog;
import com.lt.permission.service.ISystemLogService;
import com.lt.permission.vo.LogVo;

@Service("systemLogService")
public class SystemLogServiceImpl extends BaseServiceImpl implements
		ISystemLogService {

	@Autowired
	@Qualifier("systemLogDao")
	private SystemLogDao systemLogDao;

	@Override
	public int deleteSystemLog(String id) {
		return systemLogDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SystemLog record) {
		int i = 0;
		try {
			i = systemLogDao.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public int insertTest(SystemLog record) {
		return systemLogDao.insert(record);
	}

	@Override
	public SystemLog selectSystemLog(String id) {
		return systemLogDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateSystemLog(SystemLog record) {
		return systemLogDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public LogVo findLogsByPage(LogQueryDto queryDto) {
		LogVo logVo = new LogVo();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		if (!StringUtils.isEmpty(queryDto.getLogType())) {
			map.put("logType", queryDto.getLogType());
		}
		if (!StringUtils.isEmpty(queryDto.getCreator())) {
			map.put("creator", "%" + queryDto.getCreator() + "%");
		}
		if (!StringUtils.isEmpty(queryDto.getStartDate())) {
			map.put("startDate", queryDto.getStartDate() + " 00:00:00");
		}
		if (!StringUtils.isEmpty(queryDto.getEndDate())) {
			map.put("endDate", queryDto.getEndDate() + " 23:59:59");
		}

		List<SystemLog> list = systemLogDao.findLogsByPage(map);
		Integer dictCount = systemLogDao.findLogsCount(map);
		logVo.setResultList(list);
		logVo.setTotalCount(dictCount);
		return logVo;
	}

}
