package com.lt.permission.exception;

import com.lt.permission.common.ResultEnum;

/**
 * 自定义异常
 * 
 * @author lantian
 * 
 */
public class PermissionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;

	public PermissionException(ResultEnum resultEnum) {
		super(resultEnum.getMsg());
		this.code = resultEnum.getCode();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
