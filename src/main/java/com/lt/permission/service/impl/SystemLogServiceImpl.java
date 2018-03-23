package com.lt.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lt.permission.dao.SystemLogDao;
import com.lt.permission.model.SystemLog;
import com.lt.permission.service.ISystemLogService;

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

}
