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
import com.lt.permission.annotation.Log;
import com.lt.permission.common.DictConstants;
import com.lt.permission.common.ResultEnum;
import com.lt.permission.common.ResultObject;
import com.lt.permission.dto.FunctionDto;
import com.lt.permission.exception.PermissionException;
import com.lt.permission.model.Function;
import com.lt.permission.model.User;
import com.lt.permission.service.IFunctionService;
import com.lt.permission.util.ResultUtil;

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
					.findFunctionTreesByUid(this.getUid());
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
	@ResponseBody
	public ResultObject<List<Map<String, Object>>> findFunctionTrees() {
		List<Map<String, Object>> mapList = null;
		List<Function> functionList = functionService.findAllFunctionTrees();
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

		return ResultUtil.success(mapList);
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
	public ResultObject<Function> getFunction(
			@RequestParam(value = "fid", required = true) String fid) {
		Function f = functionService.getFunction(fid);
		return ResultUtil.success(f);
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
	public ResultObject addFunction(@RequestBody FunctionDto dto) {
		// 获取父级节点信息
		if ("0".equals(dto.getPfid())) {
			dto.setFrelation("0");
		} else {
			Function pf = functionService.getFunction(dto.getPfid());
			dto.setFrelation(pf.getFrelation());
		}
		functionService.addFunction(dto);
		return ResultUtil.success();
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
	public ResultObject updateFunction(@RequestBody FunctionDto dto) {
		functionService.updateFunction(dto);
		return ResultUtil.success();
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
	public ResultObject deleteFunction(
			@RequestParam(value = "fid", required = true) String fid) {
		functionService.deleteFunction(fid);
		return ResultUtil.success();
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
