package com.lt.permission.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lt.permission.dao.FunctionDao;
import com.lt.permission.dao.RoleDao;
import com.lt.permission.dao.UserRoleDao;
import com.lt.permission.dto.FunctionDto;
import com.lt.permission.model.Function;
import com.lt.permission.model.Role;
import com.lt.permission.service.IFunctionService;

@Service("functionService")
public class FunctionServiceImpl extends BaseServiceImpl implements
		IFunctionService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("functionDao")
	private FunctionDao functionDao;

	@Autowired
	@Qualifier("roleDao")
	private RoleDao roleDao;

	@Override
	public List<Function> findFunctionTreesByUid(String uid) {
		// 根据用户ID获取该用户拥有的角色信息
		List<Role> roleList = roleDao.getRoleByUid(uid);
		List<Function> functionList = null;
		if (roleList != null && roleList.size() > 0) {
			List<String> roleIdList = new ArrayList<String>();
			for (Role role : roleList) {
				roleIdList.add(role.getRid());
			}
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map.put("roleIdList", roleIdList);

			functionList = functionDao.findFunctionTreesByRole(map);
		}
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
	public int addFunction(FunctionDto dto) {
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
			f.setFsort(Integer.parseInt(StringUtils.isEmpty(dto.getFsort()) ? "1"
					: dto.getFsort()));
			f.setFrelation(dto.getFrelation() + "|" + fid);
			f.setCreator(dto.getOperatorName());
			f.setModifier(dto.getOperatorName());
			i = functionDao.insert(f);
		}
		return i;
	}

	@Override
	public List<Function> findFunctionTreesByPfid(String frelation) {
		frelation = frelation + "|%";
		List<Function> functionList = functionDao
				.findFunctionTreesByPfid(frelation);
		return functionList;
	}

	@Override
	public int deleteFunction(String fid) {
		return functionDao.logicDeleteFunction(fid);

	}

	@Override
	public int updateFunction(FunctionDto dto) {
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
