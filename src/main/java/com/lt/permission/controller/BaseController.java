package com.lt.permission.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lt.permission.util.JsonUtils;

public class BaseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public JSONArray toJSONArray(Object obj) {
		if (obj != null) {
			return JsonUtils.toJSONArray(obj);
		} else {
			return null;
		}
	}

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
}
