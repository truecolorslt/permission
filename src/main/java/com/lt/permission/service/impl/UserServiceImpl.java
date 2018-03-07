package com.lt.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lt.permission.dao.UserDao;
import com.lt.permission.dto.UserDto;
import com.lt.permission.dto.UserQueryDto;
import com.lt.permission.model.User;
import com.lt.permission.service.IUserService;
import com.lt.permission.util.MD5Util;
import com.lt.permission.vo.UserVo;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements IUserService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Override
	public UserVo findUsersByPage(UserQueryDto queryDto) {
		UserVo userVo = new UserVo();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		if (!StringUtils.isEmpty(queryDto.getUsername())) {
			map.put("username", "%" + queryDto.getUsername() + "%");
		}
		if (!StringUtils.isEmpty(queryDto.getRealName())) {
			map.put("realName", "%" + queryDto.getRealName() + "%");
		}
		if (queryDto.getDidList() != null && queryDto.getDidList().size() > 0) {
			map.put("didList", queryDto.getDidList());
		}

		List<User> userList = userDao.findUsersByPage(map);
		Integer userCount = userDao.findUsersCount(map);
		userVo.setResultList(userList);
		userVo.setTotalCount(userCount);
		return userVo;
	}

	@Override
	public int addUser(UserDto dto) {
		int i = 0;
		if (dto != null) {
			// 封装User对象
			User u = new User();
			String uid = UUID.randomUUID().toString().toLowerCase();
			u.setUid(uid);
			u.setDid(dto.getDid());
			u.setDname(dto.getDname());
			u.setUsername(dto.getUsername());
			u.setPassword(MD5Util.getMD5(dto.getPassword()));
			u.setRealName(dto.getRealName());
			u.setRemark(dto.getRemark());
			u.setNickName(dto.getNickName());
			u.setSex(dto.getSex());
			u.setCreator(dto.getOperatorName());
			u.setModifier(dto.getOperatorName());
			i = userDao.insert(u);
		}
		return i;
	}

	@Override
	public User getUserByUsernameAndPwd(String username, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		User user = userDao.getUserByUsernameAndPwd(map);
		return user;
	}

	@Override
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	public Set<String> getRolesByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteUser(String uid) {
		return userDao.logicDeleteUser(uid);
	}

	@Override
	public int updateUser(UserDto dto) {
		int i = 0;
		if (dto != null) {
			// 封装user对象
			User u = new User();
			u.setUid(dto.getUid());
			u.setRealName(dto.getRealName());
			u.setDname(dto.getDname());
			u.setDid(dto.getDid());
			u.setNickName(dto.getNickName());
			u.setSex(dto.getSex());
			u.setRemark(dto.getRemark());
			u.setModifier(dto.getOperatorName());
			i = userDao.updateByPrimaryKeySelective(u);
		}
		return i;
	}
}
