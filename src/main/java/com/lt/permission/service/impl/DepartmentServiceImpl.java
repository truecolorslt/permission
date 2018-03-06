package com.lt.permission.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lt.permission.dao.DepartmentDao;
import com.lt.permission.dto.DepartmentDto;
import com.lt.permission.model.Department;
import com.lt.permission.service.IDepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl extends BaseServiceImpl implements
		IDepartmentService {

	@Autowired
	@Qualifier("departmentDao")
	private DepartmentDao departmentDao;

	@Override
	public List<Department> findAllDepartmentTrees() {
		return departmentDao.getAllDepartment();
	}

	@Override
	public Department getDepartment(String did) {
		Department d = departmentDao.selectByPrimaryKey(did);
		return d;
	}

	@Override
	public int addDepartment(DepartmentDto dto) {
		int i = 0;
		if (dto != null) {
			// 封装Department对象
			Department d = new Department();
			String did = UUID.randomUUID().toString().toLowerCase();
			d.setDid(did);
			d.setDname(dto.getDname());
			d.setDcode(dto.getDcode());
			d.setPdid(dto.getPdid());
			d.setDsort(Integer.parseInt(dto.getDsort() == null ? "1" : dto
					.getDsort()));
			d.setDrelation(dto.getDrelation() + "|" + did);
			d.setCreator(dto.getOperatorName());
			d.setModifier(dto.getOperatorName());
			i = departmentDao.insert(d);
		}
		return i;
	}

	@Override
	public int deleteDepartment(String did) {
		return departmentDao.logicDeleteDepartment(did);
	}

	@Override
	public int updateDepartment(DepartmentDto dto) {
		int i = 0;
		if (dto != null) {
			// 封装Department对象
			Department d = new Department();
			d.setDid(dto.getDid());
			d.setDname(dto.getDname());
			d.setDcode(dto.getDcode());
			d.setDsort(Integer.parseInt(dto.getDsort() == null ? "1" : dto
					.getDsort()));
			d.setModifier(dto.getOperatorName());
			i = departmentDao.updateByPrimaryKeySelective(d);
		}
		return i;
	}

	@Override
	public List<Department> findDepartmentTreesByPdid(String pdid,
			boolean isAllChild) {
		List<Department> deptList = null;
		if (isAllChild) {
			// 查询所有子部门列表
			Department pdept = departmentDao.selectByPrimaryKey(pdid);
			if (pdept != null) {
				String drelation = pdept.getDrelation();
				drelation = drelation + "|%";
				deptList = departmentDao.getDeptsByDrelation(drelation);
			}
		} else {
			// 查询下一级子部门列表
			deptList = departmentDao.getDeptsByPdid(pdid);
		}
		return deptList;
	}
}
