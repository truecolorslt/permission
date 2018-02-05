package com.lt.permission.service.impl;

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

import com.lt.permission.dao.UserDao;
import com.lt.permission.dto.UserDto;
import com.lt.permission.dto.UserQueryDto;
import com.lt.permission.model.User;
import com.lt.permission.service.IUserService;
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
		if (!StringUtils.isEmpty(queryDto.getDname())) {
			map.put("username", "%" + queryDto.getUsername() + "%");
		}
		if (!StringUtils.isEmpty(queryDto.getRealName())) {
			map.put("realName", "%" + queryDto.getRealName() + "%");
		}
		if (!StringUtils.isEmpty(queryDto.getDid())) {
			map.put("did", queryDto.getDid());
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
			u.setUsername(dto.getUsername());
			u.setPassword(dto.getPassword());
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

}
