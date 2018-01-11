package com.lt.permission.service;

import java.util.List;

import com.lt.permission.dto.DictQueryDto;
import com.lt.permission.model.Dict;

public interface IDictService {
	/**
	 * 分页查询数据字典
	 * 
	 * @param queryDto
	 * @return
	 */
	public List<Dict> findDictsByPage(DictQueryDto queryDto);
}
