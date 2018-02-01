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

import com.lt.permission.dao.DictDao;
import com.lt.permission.dto.DictDto;
import com.lt.permission.dto.DictQueryDto;
import com.lt.permission.model.Dict;
import com.lt.permission.service.IDictService;
import com.lt.permission.vo.DictVo;

@Service("dictService")
public class DictServiceImpl extends BaseServiceImpl implements IDictService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("dictDao")
	private DictDao dictDao;

	@Override
	public DictVo findDictsByPage(DictQueryDto queryDto) {
		DictVo dictVo = new DictVo();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page",
				(queryDto.getCurrentPage() - 1) * queryDto.getPageSize());
		map.put("pageSize", queryDto.getPageSize());
		if (!StringUtils.isEmpty(queryDto.getDcode())) {
			map.put("dcode", queryDto.getDcode());
		}
		if (!StringUtils.isEmpty(queryDto.getDname())) {
			map.put("dname", "%" + queryDto.getDname() + "%");
		}

		List<Dict> dictList = dictDao.findDictsByPage(map);
		Integer dictCount = dictDao.findDictsCount(map);
		dictVo.setResultList(dictList);
		dictVo.setTotalCount(dictCount);
		return dictVo;
	}

	@Override
	public int deleteDict(String did) {
		return dictDao.logicDeleteDict(did);
	}

	@Override
	public int updateDict(DictDto dto) {
		int i = 0;
		if (dto != null) {
			// 封装dict对象
			Dict d = new Dict();
			d.setDid(dto.getDid());
			d.setDname(dto.getDname());
			d.setRemark(dto.getRemark());
			d.setModifier(dto.getOperatorName());
			i = dictDao.updateByPrimaryKeySelective(d);
		}
		return i;
	}

	@Override
	public int addDict(DictDto dto) {
		int i = 0;
		if (dto != null) {
			// 封装Dict对象
			Dict d = new Dict();
			String did = UUID.randomUUID().toString().toLowerCase();
			d.setDid(did);
			d.setDname(dto.getDname());
			d.setDcode(dto.getDcode());
			d.setRemark(dto.getRemark());
			d.setCreator(dto.getOperatorName());
			d.setModifier(dto.getOperatorName());
			i = dictDao.insert(d);
		}
		return i;
	}

	@Override
	public Dict getDictByCode(String dcode) {
		return dictDao.getDictByCode(dcode);
	}

	@Override
	public Dict getDictByDid(String did) {
		return dictDao.selectByPrimaryKey(did);
	}
}
