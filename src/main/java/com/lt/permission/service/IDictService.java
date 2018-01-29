package com.lt.permission.service;

import com.lt.permission.dto.DictQueryDto;
import com.lt.permission.vo.DictVo;

public interface IDictService {
	/**
	 * 分页查询数据字典
	 * 
	 * @param queryDto
	 * @return
	 */
	public DictVo findDictsByPage(DictQueryDto queryDto);

	/**
	 * 删除数据字典
	 * 
	 * @param did
	 */
	public int deleteDict(String did);
}
