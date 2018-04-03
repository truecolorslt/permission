package com.lt.permission.dao;

import java.util.List;
import java.util.Map;

import com.lt.permission.model.Function;

public interface FunctionDao {
	public int deleteByPrimaryKey(String fid);

	public int insert(Function record);

	public int insertSelective(Function record);

	public Function selectByPrimaryKey(String fid);

	public int updateByPrimaryKeySelective(Function record);

	public int updateByPrimaryKey(Function record);

	public List<Function> findFunctionTrees();

	public List<Function> findFunctionTreesByPfid(String frelation);

	public int logicDeleteFunction(String fid);

	public List<Function> findFunctionTreesByRole(Map<String, List<String>> map);

	public List<Function> getFunctionHasUrl();
	

}