package com.lt.permission.dao;

import java.util.List;
import java.util.Map;

import com.lt.permission.model.Dict;

public interface DictDao {
	int deleteByPrimaryKey(String did);

	int insert(Dict record);

	int insertSelective(Dict record);

	Dict selectByPrimaryKey(String did);

	int updateByPrimaryKeySelective(Dict record);

	int updateByPrimaryKey(Dict record);

	public List<Dict> findDictsByPage(Map<String, Object> map);

	public int findDictsCount(Map<String, Object> map);
}