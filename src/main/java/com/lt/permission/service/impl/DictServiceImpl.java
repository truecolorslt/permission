package com.lt.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lt.permission.dao.DictDao;
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
}
