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

import com.alibaba.fastjson.JSONObject;
import com.lt.permission.dto.DictQueryDto;
import com.lt.permission.model.Dict;
import com.lt.permission.service.IDictService;
import com.lt.permission.vo.DictVo;

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
		JSONObject jo = new JSONObject();
		try {
			DictQueryDto queryDto = new DictQueryDto(page, rows);
			queryDto.setDname(dname);
			queryDto.setDcode(dcode);

			DictVo dictVo = dictService.findDictsByPage(queryDto);
			if (dictVo != null) {
				List<Dict> dictList = (List<Dict>) dictVo.getResultList();
				Integer dictCount = dictVo.getTotalCount();

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

					jo.put("rows", this.toJSONArray(mapList));
					// json中代表页码总数
					jo.put("total",
							dictCount % Integer.parseInt(rows) == 0 ? dictCount
									/ Integer.parseInt(rows) : 1 + dictCount
									/ Integer.parseInt(rows));
					// json中代表当前页码
					jo.put("page", page);
					// json中代表数据行总数
					jo.put("records", dictCount);
				} else {
					jo.put("total","0");
					jo.put("page", "1");
					jo.put("records", "0");
					jo.put("rows", "");
				}
				dictsJson = this.toJSONObject(jo).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictsJson;
	}
}
