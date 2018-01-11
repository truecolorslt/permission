package com.lt.permission.service;

import java.util.List;

import com.lt.permission.dto.FunctionDto;
import com.lt.permission.model.Function;

public interface IFunctionService {

	/**
	 * 查询用户具有的菜单
	 * 
	 * @param uid
	 * @return
	 */
	public List<Function> findFunctionTrees(String uid);

	/**
	 * 获取该节点下的子菜单
	 * 
	 * @param pfid
	 * @return
	 */
	public List<Function> findFunctionTreesByPfid(String pfid);

	/**
	 * 查询所有菜单
	 * 
	 * @return
	 */
	public List<Function> findAllFunctionTrees();

	/**
	 * 根据id获取菜单对象
	 * 
	 * @param fid
	 * @return
	 */
	public Function getFunction(String fid);

	/**
	 * 新增菜单
	 * 
	 * @param function
	 * @return
	 */
	public int addFunction(FunctionDto dto);

	/**
	 * 修改菜单
	 * 
	 * @param function
	 * @return
	 */
	public int updateFunction(FunctionDto dto);

	/**
	 * 删除菜单
	 * 
	 * @param fid
	 */
	public int deleteFunction(String fid);
}
