package com.lt.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lt.permission.dto.DictDto;
import com.lt.permission.dto.DictQueryDto;
import com.lt.permission.model.Dict;
import com.lt.permission.service.IDictService;
import com.lt.permission.util.DateUtil;
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
						map.put("modifiedTime", DateUtil.formatDate(
								d.getModifiedTime(), "yyyy-MM-dd HH:mm:ss"));
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
					jo.put("total", "0");
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

	@RequestMapping(value = "/deleteDict")
	@ResponseBody
	public String deleteDict(
			@RequestParam(value = "did", required = true) String did) {
		String rtnStr = "";
		try {
			int i = dictService.deleteDict(did);
			JSONObject jo = new JSONObject();
			if (i > 0) {
				jo.put("result", true);
			} else {
				jo.put("result", false);
			}
			rtnStr = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnStr;
	}

	@RequestMapping(value = "/editDict")
	@ResponseBody
	public String editDict(
			@RequestParam(value = "did", required = true) String did,
			@RequestParam(value = "dname", required = true) String dname,
			@RequestParam(value = "remark", required = false) String remark) {
		String rtnStr = "";
		try {
			JSONObject jo = new JSONObject();
			Dict dict = dictService.getDictByDid(did);
			if (dict != null && dict.getDname().equals(dname)
					&& dict.getRemark().equals(remark)) {
				jo.put("result", true);
			} else {
				DictDto dto = new DictDto();
				dto.setDid(did);
				dto.setDname(dname);
				dto.setRemark(remark);
				int i = dictService.updateDict(dto);
				if (i > 0) {
					jo.put("result", true);
				} else {
					jo.put("result", false);
				}
			}
			rtnStr = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnStr;
	}

	@RequestMapping(value = "/addDict")
	@ResponseBody
	public String addDict(@RequestBody DictDto dto) {
		String rtnStr = "";
		try {
			int i = dictService.addDict(dto);
			JSONObject jo = new JSONObject();
			if (i > 0) {
				jo.put("result", true);
			} else {
				jo.put("result", false);
			}
			rtnStr = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnStr;
	}

	/**
	 * Ajax请求校验编码是否合法。
	 */
	@RequestMapping(value = "/checkDcode")
	@ResponseBody
	public boolean checkDcode(
			@RequestParam(value = "dcode", required = true) String dcode) {
		Dict dict = dictService.getDictByCode(dcode);
		if (dict != null) {
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/findDictDetails")
	@ResponseBody
	public String findDictDetails(
			@RequestParam(value = "did", required = true) String did) {
		String dictsJson = "";
		JSONObject jo = new JSONObject();
		try {
			DictQueryDto queryDto = new DictQueryDto();
			queryDto.setDid(did);

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
						map.put("modifiedTime", DateUtil.formatDate(
								d.getModifiedTime(), "yyyy-MM-dd HH:mm:ss"));
						map.put("modifier", d.getModifier());
						mapList.add(map);
					}

					jo.put("rows", this.toJSONArray(mapList));
				}
				dictsJson = this.toJSONObject(jo).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictsJson;
	}

}
