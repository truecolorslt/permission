package com.lt.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lt.permission.dao.RoleDao;
import com.lt.permission.dao.RoleFunctionDao;
import com.lt.permission.dto.RoleDto;
import com.lt.permission.dto.RoleQueryDto;
import com.lt.permission.model.Role;
import com.lt.permission.model.RoleFunction;
import com.lt.permission.service.IRoleService;
import com.lt.permission.vo.RoleVo;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements IRoleService {

	@Autowired
	@Qualifier("roleDao")
	private RoleDao roleDao;

	@Autowired
	@Qualifier("roleFunctionDao")
	private RoleFunctionDao roleFunctionDao;

	@Override
	public RoleVo findRolesByPage(RoleQueryDto queryDto) {
		RoleVo vo = new RoleVo();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		if (!StringUtils.isEmpty(queryDto.getRname())) {
			map.put("rname", "%" + queryDto.getRname() + "%");
		}
		if (!StringUtils.isEmpty(queryDto.getRcode())) {
			map.put("rcode", "%" + queryDto.getRcode() + "%");
		}

		List<Role> list = roleDao.findRolesByPage(map);
		Integer count = roleDao.findRolesCount(map);
		vo.setResultList(list);
		vo.setTotalCount(count);
		return vo;
	}

	@Override
	public int addRole(RoleDto dto) {
		int i = 0;
		if (dto != null) {
			// 封装Role对象
			Role r = new Role();
			String rid = UUID.randomUUID().toString().toLowerCase();
			r.setRid(rid);
			r.setRname(dto.getRname());
			r.setRcode(dto.getRcode());
			r.setRemark(dto.getRemark());
			r.setCreator(dto.getOperatorName());
			r.setModifier(dto.getOperatorName());
			i = roleDao.insert(r);
		}
		return i;
	}

	@Override
	public int updateRole(RoleDto dto) {
		int i = 0;
		if (dto != null) {
			// 封装role对象
			Role r = new Role();
			r.setRid(dto.getRid());
			r.setRname(dto.getRname());
			r.setRcode(dto.getRcode());
			r.setRemark(dto.getRemark());
			r.setModifier(dto.getOperatorName());
			i = roleDao.updateByPrimaryKeySelective(r);
		}
		return i;
	}

	@Override
	public int deleteRole(String rid) {
		return roleDao.logicDeleteRole(rid);
	}

	@Override
	public int setFunctionForRole(String rid, String[] fids) {
		if (fids != null && fids.length >= 0) {
			roleFunctionDao.deleteByRid(rid);
			int i = 0;
			for (String fid : fids) {
				RoleFunction rf = new RoleFunction();
				String rfid = UUID.randomUUID().toString().toLowerCase();
				rf.setRfid(rfid);
				rf.setFid(fid);
				rf.setRid(rid);
				i += roleFunctionDao.insert(rf);
			}
			return i;
		} else {
			return 0;
		}
	}

	@Override
	public List<Map<String, Object>> getFunctionByRole(String rid) {
		return roleFunctionDao.getRoleFunctionByRole(rid);
	}

	@Override
	public List<Role> getRoleByUid(String uid, Boolean hasRole) {
		List<Role> roleList = null;
		if (hasRole) {
			roleList = roleDao.getRoleByUid(uid);
		} else {
			roleList = roleDao.getNonRoleByUid(uid);
		}
		return roleList;
	}

	@Override
	public List<Role> getRolesByUsername(String username) {
		return roleDao.getRolesByUsername(username);
	}

	@Override
	public List<Role> getRolesByFid(String fid) {
		return roleDao.getRolesByFid(fid);
	}

}
