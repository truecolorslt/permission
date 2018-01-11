package com.lt.permission.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lt.permission.dao.DictDao;
import com.lt.permission.dao.FunctionDao;
import com.lt.permission.dto.DictQueryDto;
import com.lt.permission.model.Dict;
import com.lt.permission.service.IDictService;

@Service("dictService")
public class DictServiceImpl extends BaseServiceImpl implements IDictService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("dictDao")
	private DictDao dictDao;

	@Override
	public List<Dict> findDictsByPage(DictQueryDto queryDto) {
		Dict dict = new Dict();
		Integer currentPage = queryDto.getCurrentPage();
		Integer pageSize = queryDto.getPageSize();
		return dictDao.findDictsByPage(dict, currentPage, pageSize);
	}

}
