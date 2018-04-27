package com.lt.permission.util;

import com.lt.permission.common.ResultEnum;
import com.lt.permission.common.ResultObject;

public class ResultUtil {
	/**
	 * 带返回值的成功返回对象
	 * 
	 * @param object
	 * @return
	 */
	public static ResultObject success(Object object) {
		ResultObject result = new ResultObject();
		result.setCode(ResultEnum.RESULT_CODE_SUCCESS.getCode());
		result.setMsg(ResultEnum.RESULT_CODE_SUCCESS.getMsg());
		result.setData(object);
		return result;
	}

	/**
	 * 不带返回值的成功返回对象
	 * 
	 * @param object
	 * @return
	 */
	public static ResultObject success() {
		return success(null);
	}

	/**
	 * 返回错误对象
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public static ResultObject error(Integer code, String msg) {
		ResultObject result = new ResultObject();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}
}
