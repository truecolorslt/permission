package com.lt.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lt.permission.dto.DictQueryDto;
import com.lt.permission.model.Dict;
import com.lt.permission.service.IDictService;

/**
 * 数据字典控制器
 * 
 * @author lantian
 * 
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDictService dictService;

	/**
	 * 进入数据字典管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dictMgt")
	public String roleMgt() {
		return "/dict/dict_mgt";
	}

	@RequestMapping(value = "/findDicts")
	@ResponseBody
	public String findDicts(
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "rows", defaultValue = "10") String rows,
			@RequestParam(value = "dname", required = false) String dname,
			@RequestParam(value = "dcode", required = false) String dcode) {
		String dictsJson = "";
		try {
			DictQueryDto queryDto = new DictQueryDto(page, rows);
			queryDto.setDname(dname);
			queryDto.setDcode(dcode);

			List<Dict> dictList = dictService.findDictsByPage(queryDto);
			List<Map<String, Object>> mapList = null;
			if (dictList != null && dictList.size() > 0) {
				mapList = new ArrayList<Map<String, Object>>();

				for (Dict d : dictList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("did", d.getDid());
					map.put("dname", d.getDname());
					map.put("dcode", d.getDcode());
					map.put("remark", d.getRemark());
					map.put("modifiedTime", d.getModifiedTime());
					map.put("modifier", d.getModifier());
					mapList.add(map);
				}
				dictsJson = this.toJSONArray(mapList).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictsJson;

	}
}
