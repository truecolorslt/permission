package com.lt.permission.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lt.permission.dao.FunctionDao;
import com.lt.permission.dto.function.FunctionAddDto;
import com.lt.permission.model.Function;
import com.lt.permission.service.IFunctionService;

@Service("functionService")
public class FunctionServiceImpl extends BaseServiceImpl implements
		IFunctionService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("functionDao")
	private FunctionDao functionDao;

	@Override
	public List<Function> findFunctionTrees(String uid) {
		List<Function> functionList = functionDao.findFunctionTrees();
		return functionList;
	}

	@Override
	public List<Function> findAllFunctionTrees() {
		List<Function> functionList = functionDao.findFunctionTrees();
		return functionList;
	}

	@Override
	public Function getFunction(String fid) {
		Function f = functionDao.selectByPrimaryKey(fid);
		return f;
	}

	@Override
	public int addFunction(FunctionAddDto dto) {
		int i = 0;
		if (dto != null) {
			// 封装function对象
			Function f = new Function();
			String fid = UUID.randomUUID().toString().toLowerCase();
			f.setFid(fid);
			f.setFname(dto.getFname());
			f.setFcode(dto.getFcode());
			f.setPfid(dto.getPfid());
			f.setFicon(dto.getFicon());
			f.setFurl(dto.getFurl());
			f.setFsort(Integer.parseInt(dto.getFsort() == null ? "1" : dto
					.getFsort()));
			f.setFrelation(dto.getFrelation() + "|" + fid);
			f.setCreator(dto.getOperatorName());
			f.setModifier(dto.getOperatorName());
			i = functionDao.insert(f);
		}
		return i;
	}

	@Override
	public List<Function> findFunctionTreesByPfid(String pfid) {
		pfid = pfid + "|%";
		List<Function> functionList = functionDao.findFunctionTreesByPfid(pfid);
		return functionList;
	}

	@Override
	public int deleteFunction(String fid) {
		return functionDao.logicDeleteFunction(fid);

	}

	@Override
	public int updateFunction(FunctionAddDto dto) {
		int i = 0;
		if (dto != null) {
			// 封装function对象
			Function f = new Function();
			f.setFid(dto.getFid());
			f.setFname(dto.getFname());
			f.setFcode(dto.getFcode());
			f.setFicon(dto.getFicon());
			f.setFurl(dto.getFurl());
			f.setFsort(Integer.parseInt(dto.getFsort() == null ? "1" : dto
					.getFsort()));
			f.setModifier(dto.getOperatorName());
			i = functionDao.updateByPrimaryKeySelective(f);
		}
		return i;
	}
}
