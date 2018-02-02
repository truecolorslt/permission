package com.lt.permission.service;

import java.util.List;

import com.lt.permission.dto.DictDto;
import com.lt.permission.dto.DictQueryDto;
import com.lt.permission.model.Dict;
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

	/**
	 * 修改数据字典
	 * 
	 * @param dto
	 * @return
	 */
	public int updateDict(DictDto dto);

	/**
	 * 新增数据字典
	 * 
	 * @param dto
	 * @return
	 */
	public int addDict(DictDto dto);

	/**
	 * 根据编码获取字典对象
	 * 
	 * @param dcode
	 * @return
	 */
	public Dict getDictByCode(String dcode);

	/**
	 * 根据id获取字典对象
	 * 
	 * @param did
	 * @return
	 */
	public Dict getDictByDid(String did);

	/**
	 * 根据父id查询子集合
	 * 
	 * @param pdid
	 * @return
	 */
	public List<Dict> getDictsByPdid(DictQueryDto queryDto);

	/**
	 * 新增数据字典属性
	 * 
	 * @param dto
	 * @return
	 */
	public int addDictDetail(DictDto dto);
}
