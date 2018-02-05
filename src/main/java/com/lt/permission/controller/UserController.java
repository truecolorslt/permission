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
import com.lt.permission.dto.UserDto;
import com.lt.permission.dto.UserQueryDto;
import com.lt.permission.model.User;
import com.lt.permission.service.IUserService;
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

	/**
	 * 进入用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userMgt")
	public String roleMgt() {
		return "/user/user_mgt";
	}

	@RequestMapping(value = "/findUsers")
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
			queryDto.setDid(did);

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
						map.put("sex", u.getSex());
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

	@RequestMapping(value = "/addUser")
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
}
