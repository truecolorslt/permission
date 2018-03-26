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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lt.permission.annotation.Log;
import com.lt.permission.common.DictConstants;
import com.lt.permission.dto.UserDto;
import com.lt.permission.dto.UserQueryDto;
import com.lt.permission.model.Department;
import com.lt.permission.model.Role;
import com.lt.permission.model.User;
import com.lt.permission.service.IDepartmentService;
import com.lt.permission.service.IRoleService;
import com.lt.permission.service.IUserService;
import com.lt.permission.util.MD5Util;
import com.lt.permission.vo.UserVo;

/**
 * 用户控制器
 * 
 * @author lantian
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IUserService userService;

	@Autowired
	private IDepartmentService departmentService;

	@Autowired
	private IRoleService roleService;

	/**
	 * 进入用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userMgt")
	public String roleMgt() {
		return "/user/user_mgt";
	}

	/**
	 * 查询用户列表
	 * 
	 * @param page
	 * @param rows
	 * @param username
	 * @param realName
	 * @param did
	 * @return
	 */
	@RequestMapping(value = "/findUsers")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_OPT, logDesc = "查询用户列表")
	@ResponseBody
	public String findUsers(
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "rows", defaultValue = "10") String rows,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "realName", required = false) String realName,
			@RequestParam(value = "did", required = false) String did) {
		String jsonStr = "";
		JSONObject jo = new JSONObject();
		try {
			UserQueryDto queryDto = new UserQueryDto(page, rows);
			queryDto.setUsername(username);
			queryDto.setRealName(realName);
			if (!StringUtils.isEmpty(did) && !"0".equals(did)) {
				// 根据传入部门条件进行查询
				List<String> didList = new ArrayList<String>();
				didList.add(did);

				// 获取子部门信息列表
				List<Department> deptList = departmentService
						.findDepartmentTreesByPdid(did, true);
				if (deptList != null && deptList.size() > 0) {
					for (Department d : deptList) {
						didList.add(d.getDid());
					}
				}
				queryDto.setDidList(didList);
			}

			UserVo userVo = userService.findUsersByPage(queryDto);
			if (userVo != null) {
				List<User> list = (List<User>) userVo.getResultList();
				Integer count = userVo.getTotalCount();

				List<Map<String, Object>> mapList = null;
				if (list != null && list.size() > 0) {
					mapList = new ArrayList<Map<String, Object>>();

					for (User u : list) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("uid", u.getUid());
						map.put("username", u.getUsername());
						map.put("realName", u.getRealName());
						map.put("nickName", u.getNickName());
						map.put("sex", this.getAttrValue(
								DictConstants.DICT_CODE_SEX, u.getSex()));
						map.put("sexCode", u.getSex());
						map.put("dname", u.getDname());
						map.put("did", u.getDid());
						map.put("remark", u.getRemark());
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
	 * 新增用户
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/addUser")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_OPT, logDesc = "新增用户信息")
	@ResponseBody
	public String addUser(@RequestBody UserDto dto) {
		String rtnStr = "";
		try {
			int i = userService.addUser(dto);
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
	 * 逻辑删除用户
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/deleteUser")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_OPT, logDesc = "删除用户信息")
	@ResponseBody
	public String deleteUser(
			@RequestParam(value = "uid", required = true) String uid) {
		String rtnStr = "";
		try {
			int i = userService.deleteUser(uid);
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
	 * 编辑用户信息
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/updateUser")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_OPT, logDesc = "编辑用户信息")
	@ResponseBody
	public String updateUser(@RequestBody UserDto dto) {
		String rtnStr = "";
		try {
			JSONObject jo = new JSONObject();
			// UserDto dto = new UserDto();
			// dto.setUid(uid);
			// dto.setRealName(realName);
			// dto.setDname(dname);
			// dto.setDid(did);
			// dto.setNickName(nickName);
			// dto.setSex(sex);
			// dto.setRemark(remark);
			int i = userService.updateUser(dto);
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
	 * 重置密码
	 * 
	 * @param uid
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/resetPwd")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_OPT, logDesc = "重置密码")
	@ResponseBody
	public String resetPwd(
			@RequestParam(value = "uid", required = true) String uid,
			@RequestParam(value = "password", required = true) String password) {
		String rtnStr = "";
		JSONObject jo = new JSONObject();
		UserDto dto = new UserDto();
		dto.setUid(uid);
		dto.setPassword(MD5Util.getMD5(password));
		int i = userService.updateUser(dto);
		if (i > 0) {
			jo.put("result", true);
		} else {
			jo.put("result", false);
		}
		rtnStr = jo.toString();
		return rtnStr;
	}

	/**
	 * 获取用户角色list
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/getRoleList")
	@ResponseBody
	public String getRoleList(
			@RequestParam(value = "uid", required = true) String uid) {
		String rtnStr = "";
		// 未拥有的角色
		List<Role> nonSelectedList = roleService.getRoleByUid(uid, false);
		// 已经拥有的角色；
		List<Role> selectedList = roleService.getRoleByUid(uid, true);

		List<Map<String, Object>> mapList1 = new ArrayList<Map<String, Object>>();
		if (nonSelectedList != null && nonSelectedList.size() > 0) {
			for (Role r : nonSelectedList) {
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("roleId", r.getRid());
				map1.put("roleName", r.getRname());
				mapList1.add(map1);
			}
		}
		List<Map<String, Object>> mapList2 = new ArrayList<Map<String, Object>>();
		if (selectedList != null && selectedList.size() > 0) {
			for (Role r : selectedList) {
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("roleId", r.getRid());
				map2.put("roleName", r.getRname());
				mapList2.add(map2);
			}
		}

		JSONObject rtnJson = new JSONObject();
		rtnJson.put("nonSelectedList", mapList1);
		rtnJson.put("selectedList", mapList2);
		rtnStr = rtnJson.toJSONString();
		return rtnStr;
	}

	/**
	 * 设置用户角色权限
	 * 
	 * @param rid
	 * @return
	 */
	@RequestMapping(value = "/setUserRole")
	@Log(logType = DictConstants.DICT_CODE_LOG_TYPE_OPT, logDesc = "设置用户角色权限")
	@ResponseBody
	public String setUserRole(
			@RequestParam(value = "uid", required = true) String uid,
			@RequestParam(value = "roles", required = true) String roles) {
		String rtnStr = "";
		try {
			JSONObject jo = new JSONObject();
			if (StringUtils.isEmpty(roles)) {
				jo.put("result", false);
				jo.put("msg", "请选择角色");
			} else {
				String[] rids = roles.split("[|]");
				int i = userService.setUserRole(uid, rids);
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
}
