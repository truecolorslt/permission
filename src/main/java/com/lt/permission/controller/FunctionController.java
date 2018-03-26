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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lt.permission.annotation.Log;
import com.lt.permission.common.DictConstants;
import com.lt.permission.dto.FunctionDto;
import com.lt.permission.model.Function;
import com.lt.permission.service.IFunctionService;

/**
 * 功能菜单控制器
 * 
 * @author lantian
 * 
 */
@Controller
@RequestMapping("/function")
public class FunctionController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IFunctionService functionService;

	/**
	 * 获取用户所拥有的功能菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findUserFunctionTrees")
	@ResponseBody
	public String findUserFunctionTrees() {
		String treesJson = "";
		try {
			List<Function> functionList = functionService
					.findFunctionTrees(null);
			List<Map<String, Object>> mapList = null;
			if (functionList != null && functionList.size() > 0) {
				mapList = new ArrayList<Map<String, Object>>();
				for (Function f : functionList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("F_ModuleId", f.getFid());
					map.put("F_ParentId", f.getPfid());
					map.put("F_EnCode", f.getFcode());
					map.put("F_FullName", f.getFname());
					map.put("F_Icon", f.getFicon());
					map.put("F_UrlAddress", f.getFurl());
					// map.put("F_Target", "expand");
					// map.put("F_IsMenu", 0);
					// map.put("F_AllowExpand", 1);
					// map.put("F_IsPublic", 0);
					// map.put("F_AllowEdit", null);
					// map.put("F_AllowDelete", null);
					// map.put("F_SortCode", 1);
					// map.put("F_DeleteMark", 0);
					// map.put("F_EnabledMark", 1);
					// map.put("F_Description", null);
					// map.put("F_CreateDate", null);
					// map.put("F_CreateUserId", null);
					// map.put("F_CreateUserName", null);
					// map.put("F_ModifyDate", "2015-11-17 11,22,46");
					// map.put("F_ModifyUserId", "System");
					// map.put("F_ModifyUserName", "超级管理员");
					mapList.add(map);
				}
				treesJson = this.toJSONArray(mapList).toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return treesJson;
	}

	/**
	 * 获取所有功能菜单树
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findFunctionTrees")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_OPT, logDesc = "查询功能列表")
	@ResponseBody
	public String findFunctionTrees() {
		String treesJson = "";
		try {
			List<Function> functionList = functionService
					.findAllFunctionTrees();
			List<Map<String, Object>> mapList = null;
			mapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put("id", "0");
			rootMap.put("pId", null);
			rootMap.put("name", "当前系统");
			rootMap.put("open", true);
			rootMap.put("nocheck", true);
			mapList.add(rootMap);
			if (functionList != null && functionList.size() > 0) {
				for (Function f : functionList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", f.getFid());
					map.put("pId", f.getPfid());
					map.put("name", f.getFname());
					if ("0".equals(f.getPfid())) {
						map.put("open", true);
					}
					mapList.add(map);
				}
			}
			treesJson = this.toJSONArray(mapList).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return treesJson;
	}

	/**
	 * 获取菜单明细
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getFunction")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_OPT, logDesc = "查询功能明细")
	@ResponseBody
	public String getFunction(@RequestBody String param) {
		String rtnStr = "";
		JSONObject jo = new JSONObject();

		// 如果页面传的是json字符串，用下列方式解析
		Map<String, Object> m = (Map<String, Object>) jo.parse(param);
		// string转map
		JSONObject parseObject = jo.parseObject(param); // string转json

		// 如果页面传的是json数组字符串，用下列方式解析
		// List<Map> parseArray = jo.parseArray(param, Map.class);
		// System.out.println(parseArray); // string转list
		//
		// JSONArray parseArray2 = jo.parseArray(param);
		// System.out.println(parseArray2);

		String fid = parseObject.getString("fid");
		try {
			Function f = functionService.getFunction(fid);
			if (f != null) {
				rtnStr = this.toJSONObject(f).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnStr;
	}

	/**
	 * 新增菜单
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/addFunction")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_OPT, logDesc = "新增功能信息")
	@ResponseBody
	public String addFunction(@RequestBody FunctionDto dto) {
		String rtnStr = "";
		try {
			// 获取父级节点信息
			if ("0".equals(dto.getPfid())) {
				dto.setFrelation("0");
			} else {
				Function pf = functionService.getFunction(dto.getPfid());
				dto.setFrelation(pf.getFrelation());
			}

			int i = functionService.addFunction(dto);
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
	 * 修改菜单
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/updateFunction")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_OPT, logDesc = "修改功能信息")
	@ResponseBody
	public String updateFunction(@RequestBody FunctionDto dto) {
		String rtnStr = "";
		try {
			int i = functionService.updateFunction(dto);
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
	 * 删除菜单
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/deleteFunction")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_OPT, logDesc = "删除功能信息")
	@ResponseBody
	public String deleteFunction(@RequestBody String param) {
		String rtnStr = "";
		JSONObject jo = new JSONObject();

		// 如果页面传的是json字符串，用下列方式解析
		Map<String, Object> m = (Map<String, Object>) jo.parse(param);
		// string转map
		JSONObject parseObject = jo.parseObject(param); // string转json

		String fid = parseObject.getString("fid");
		try {
			int i = functionService.deleteFunction(fid);
			JSONObject jo1 = new JSONObject();
			if (i > 0) {
				jo1.put("result", true);
			} else {
				jo1.put("result", false);
			}
			rtnStr = jo1.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnStr;
	}

	/**
	 * 根据父节点，获取所有子节点菜单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findFunctionTreesByPfid")
	@ResponseBody
	public String findFunctionTreesByPfid(String id) {
		String treesJson = "";
		try {
			String frelation = "";
			if ("0".equals(id)) {
				frelation = "0";
			} else {
				Function curFunc = functionService.getFunction(id);
				frelation = curFunc.getFrelation();
			}

			List<Function> functionList = functionService
					.findFunctionTreesByPfid(frelation);

			List<Map<String, Object>> mapList = null;
			if (functionList != null && functionList.size() > 0) {
				mapList = new ArrayList<Map<String, Object>>();
				for (Function f : functionList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", f.getFid());
					map.put("pId", f.getPfid());
					map.put("name", f.getFname());
					mapList.add(map);
				}
				treesJson = this.toJSONArray(mapList).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return treesJson;
	}

	/**
	 * 进入功能菜单管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/functionMgt")
	public String functionMgt() {
		return "/function/function_mgt";
	}
}
