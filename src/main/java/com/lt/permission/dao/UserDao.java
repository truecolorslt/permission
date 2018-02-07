package com.lt.permission.dao;

import java.util.List;
import java.util.Map;

import com.lt.permission.model.User;

public interface UserDao {
	int deleteByPrimaryKey(String uid);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(String uid);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	public List<User> findUsersByPage(Map<String, Object> map);

	public int findUsersCount(Map<String, Object> map);

	public User getUserByUsername(String username);

	public User getUserByUsernameAndPwd(Map<String, String> map);
}