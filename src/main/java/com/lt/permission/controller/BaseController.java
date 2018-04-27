package com.lt.permission.controller;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lt.permission.init.LoadInfo;
import com.lt.permission.model.Dict;
import com.lt.permission.model.User;
import com.lt.permission.service.IDictService;
import com.lt.permission.shiro.token.TokenManager;
import com.lt.permission.util.JsonUtils;

public class BaseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IDictService dictService;

	/**
	 * 将对象转换成json数组对象
	 * 
	 * @param obj
	 * @return
	 */
	public JSONArray toJSONArray(Object obj) {
		if (obj != null) {
			return JsonUtils.toJSONArray(obj);
		} else {
			return null;
		}
	}

	/**
	 * 将对象转换成json对象
	 * 
	 * @param obj
	 * @return
	 */
	public JSONObject toJSONObject(Object obj) {
		if (obj != null) {
			return JsonUtils.toJSONObject(obj);
		} else {
			return null;
		}
	}

	/**
	 * 代表页码总数
	 * 
	 * @param count总记录数
	 * @param rows每页显示行数
	 * @return
	 */
	public Integer getTotal(Integer count, String rows) {
		Integer total = 0;
		total = count % Integer.parseInt(rows) == 0 ? count
				/ Integer.parseInt(rows) : 1 + count / Integer.parseInt(rows);
		return total;
	}

	/**
	 * 根据数据字典编码和属性编码获取属性对象
	 * 
	 * @param code
	 * @param key
	 * @return
	 */
	public String getAttrValue(String code, String key) {
		Dict d = null;
		List<Dict> list = (List<Dict>) LoadInfo.initDictInfo.get(code);
		if (list != null && list.size() > 0) {
			for (Dict d1 : list) {
				if (d1.getDcode().equals(key)) {
					d = d1;
					break;
				}
			}
		} else {
			d = dictService.getAttrByCodeAndKey(code, key);
		}
		String value = "";
		if (d != null) {
			value = d.getDname();
		}
		return value;
	}

	/**
	 * 根据key更新内存中的数据字典数据
	 * 
	 * @param key
	 * @param obj
	 */
	public void updateLoadDictInfo(String key, Object obj) {
		LoadInfo.initDictInfo.put(key, obj);
	}

	/**
	 * 登录成功后，从session中获取登录用户信息
	 * 
	 * @return
	 */
	public User getUser() {
		User user = TokenManager.getToken();
		return user;
	}

	/**
	 * 获取登录用户ID
	 * 
	 * @return
	 */
	public String getUid() {
		User user = TokenManager.getToken();
		return user.getUid();
	}

	/**
	 * 获取登录用户账号
	 * 
	 * @return
	 */
	public String getUsername() {
		User user = TokenManager.getToken();
		return user.getUsername();
	}
}
