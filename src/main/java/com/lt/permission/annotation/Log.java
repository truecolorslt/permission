package com.lt.permission.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
	/** 日志类型，比如：01-操作日志； 02-业务日志； 03-异常日志 **/
	public String logType() default "";

	/** 日志描述，比如：添加用户 **/
	public String logDesc() default "";
}
