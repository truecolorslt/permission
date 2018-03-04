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
import com.lt.permission.dto.DepartmentDto;
import com.lt.permission.model.Department;
import com.lt.permission.service.IDepartmentService;

/**
 * 部门控制器
 * 
 * @author lantian
 * 
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDepartmentService departmentService;

	/**
	 * 进入部门管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deptMgt")
	public String deptMgt() {
		return "/dept/dept_mgt";
	}

	/**
	 * 湖区部门树形数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDepartmentTrees")
	@ResponseBody
	public String findDepartmentTrees() {
		String treesJson = "";
		try {
			List<Department> departmentList = departmentService
					.findAllDepartmentTrees();
			List<Map<String, Object>> mapList = null;
			mapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put("id", "0");
			rootMap.put("pId", null);
			rootMap.put("name", "公司");
			rootMap.put("open", true);
			mapList.add(rootMap);
			if (departmentList != null && departmentList.size() > 0) {
				for (Department d : departmentList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", d.getDid());
					map.put("pId", d.getPdid());
					map.put("name", d.getDname());
					if ("0".equals(d.getPdid())) {
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

	@RequestMapping(value = "/findDepartmentTreesByPfid")
	@ResponseBody
	public String findDepartmentTreesByPfid(String id) {
		return "";
	}

	/**
	 * 新增部门
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/addDepartment")
	@ResponseBody
	public String addDepartment(@RequestBody DepartmentDto dto) {
		String rtnStr = "";
		try {
			// 获取父级节点信息
			if ("0".equals(dto.getPdid())) {
				dto.setDrelation("0");
			} else {
				Department pd = departmentService.getDepartment(dto.getPdid());
				dto.setDrelation(pd.getDrelation());
			}

			int i = departmentService.addDepartment(dto);
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

	@RequestMapping(value = "/deleteDepartment")
	@ResponseBody
	public String deleteDepartment(
			@RequestParam(value = "did", required = true) String did) {
		String rtnStr = "";
		try {
			int i = departmentService.deleteDepartment(did);
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

	@RequestMapping(value = "/getDepartment")
	@ResponseBody
	public String getDepartment(
			@RequestParam(value = "did", required = true) String did) {
		String rtnStr = "";
		try {
			Department d = departmentService.getDepartment(did);
			if (d != null) {
				rtnStr = this.toJSONObject(d).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnStr;
	}

	/**
	 * 修改
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/updateDepartment")
	@ResponseBody
	public String updateDepartment(@RequestBody DepartmentDto dto) {
		String rtnStr = "";
		try {
			int i = departmentService.updateDepartment(dto);
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
}
