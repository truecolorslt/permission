package com.lt.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lt.permission.annotation.Log;
import com.lt.permission.dto.DictDto;
import com.lt.permission.dto.RoleDto;
import com.lt.permission.dto.RoleQueryDto;
import com.lt.permission.model.Function;
import com.lt.permission.model.Role;
import com.lt.permission.model.RoleFunction;
import com.lt.permission.service.IRoleService;
import com.lt.permission.vo.RoleVo;

/**
 * 角色控制器
 * 
 * @author lantian
 * 
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IRoleService roleService;

	/**
	 * 进入角色管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/roleMgt")
	public String roleMgt() {
		return "/role/role_mgt";
	}

	/**
	 * 查询角色列表
	 * 
	 * @param page
	 * @param rows
	 * @param rname
	 * @param rcode
	 * @return
	 */
	@RequestMapping(value = "/findRoles")
	@ResponseBody
	public String findRoles(
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "rows", defaultValue = "10") String rows,
			@RequestParam(value = "rname", required = false) String rname,
			@RequestParam(value = "rcode", required = false) String rcode) {
		String jsonStr = "";
		JSONObject jo = new JSONObject();
		try {
			RoleQueryDto queryDto = new RoleQueryDto(page, rows);
			queryDto.setRname(rname);
			queryDto.setRcode(rcode);

			RoleVo vo = roleService.findRolesByPage(queryDto);
			if (vo != null) {
				List<Role> list = (List<Role>) vo.getResultList();
				Integer count = vo.getTotalCount();

				List<Map<String, Object>> mapList = null;
				if (list != null && list.size() > 0) {
					mapList = new ArrayList<Map<String, Object>>();

					for (Role r : list) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("rid", r.getRid());
						map.put("rname", r.getRname());
						map.put("rcode", r.getRcode());
						map.put("remark", r.getRemark());
						mapList.add(map);
					}

					jo.put("rows", this.toJSONArray(mapList));
					// json中代表页码总数
					jo.put("total", this.getTotal(count, rows));
					// json中代表当前页码
					jo.put("page", page);
					// json中代表数据行总数
					jo.put("records", count);
				} else {
					jo.put("total", "0");
					jo.put("page", "1");
					jo.put("records", "0");
					jo.put("rows", "");
				}
				jsonStr = this.toJSONObject(jo).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 新增角色
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/addRole")
	@ResponseBody
	public String addRole(@RequestBody RoleDto dto) {
		String rtnStr = "";
		try {
			int i = roleService.addRole(dto);
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
	 * 编辑角色
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/updateRole")
	@ResponseBody
	public String updateRole(@RequestBody RoleDto dto) {
		String rtnStr = "";
		try {
			int i = roleService.updateRole(dto);
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
	 * 逻辑删除
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/deleteRole")
	@ResponseBody
	@Log(operationType = "delete操作:", operationName = "删除角色")
	public String deleteRole(
			@RequestParam(value = "rid", required = true) String rid) {
		String rtnStr = "";
		try {
			int i = roleService.deleteRole(rid);
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
	 * 设置角色权限
	 * 
	 * @param rid
	 * @return
	 */
	@RequestMapping(value = "/setRoleFunction")
	@ResponseBody
	public String setRoleFunction(
			@RequestParam(value = "rid", required = true) String rid,
			@RequestParam(value = "nodes", required = true) String nodes) {
		String rtnStr = "";
		try {
			JSONObject jo = new JSONObject();
			if (StringUtils.isEmpty(nodes)) {
				jo.put("result", false);
				jo.put("msg", "请选择功能菜单");
			} else {
				String[] fids = nodes.split("[|]");
				int i = roleService.setFunctionForRole(rid, fids);
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

	/**
	 * 获取角色菜单
	 * 
	 * @param rid
	 * @return
	 */
	@RequestMapping(value = "/getRoleFunction")
	@ResponseBody
	public String getRoleFunction(
			@RequestParam(value = "rid", required = true) String rid) {
		String treesJson = "";
		try {
			List<Map<String, Object>> listMap = roleService
					.getFunctionByRole(rid);
			List<Map<String, Object>> mapList = null;
			mapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put("id", "0");
			rootMap.put("pId", null);
			rootMap.put("name", "当前系统");
			rootMap.put("open", true);
			rootMap.put("nocheck", true);
			mapList.add(rootMap);
			if (listMap != null && listMap.size() > 0) {
				for (Map<String, Object> f : listMap) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", f.get("functionId"));
					map.put("pId", f.get("pfid"));
					map.put("name", f.get("fname"));
					if ("0".equals(f.get("pfid"))) {
						map.put("open", true);
					}
					if (!StringUtils.isEmpty(f.get("fid"))) {
						map.put("checked", true);
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
}
