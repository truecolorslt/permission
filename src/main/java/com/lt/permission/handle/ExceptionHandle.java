package com.lt.permission.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lt.permission.common.ResultEnum;
import com.lt.permission.common.ResultObject;
import com.lt.permission.exception.PermissionException;
import com.lt.permission.util.ResultUtil;

/**
 * 异常统一处理类
 * 
 * @author lantian
 * 
 */
@ControllerAdvice
public class ExceptionHandle {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResultObject handle(Exception e) {
		if (e instanceof PermissionException) {
			PermissionException pe = (PermissionException) e;
			return ResultUtil.error(pe.getCode(), pe.getMessage());
		} else {
			logger.error("【系统异常】{}", e);
			return ResultUtil.error(
					ResultEnum.RESULT_CODE_UNKONW_ERROR.getCode(),
					ResultEnum.RESULT_CODE_UNKONW_ERROR.getMsg());
		}
	}

}
