package com.lt.permission.annotation;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lt.permission.init.LoadInfo;
import com.lt.permission.model.Function;
import com.lt.permission.model.SystemLog;
import com.lt.permission.model.User;
import com.lt.permission.service.ISystemLogService;
import com.lt.permission.shiro.token.TokenManager;
import com.lt.permission.util.JsonUtils;

@Component
@Aspect
public class SystemLogAspect {
	// 注入Service用于把日志保存数据库
	// 这里我用resource注解，一般用的是@Autowired，他们的区别如有时间我会在后面的博客中来写

	@Autowired
	private ISystemLogService systemLogService;

	private static final Logger logger = LoggerFactory
			.getLogger(SystemLogAspect.class);

	// Controller层切点
	// @Pointcut("execution (* com.lt.permission.controller..*.*(..))")
	@Pointcut("execution (* com.lt.permission.controller..*.*(..)) "
			+ "&& @annotation(com.lt.permission.annotation.Log)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	// @Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		logger.info("==========执行controller前置通知===============");
		if (logger.isInfoEnabled()) {
			logger.info("before " + joinPoint);
		}
	}

	// 配置controller环绕通知,使用在方法aspect()上注册的切入点
	// @Around("controllerAspect()")
	public Object around(JoinPoint joinPoint) {
		Object object = null;
		logger.info("==========开始执行controller环绕通知===============");
		long start = System.currentTimeMillis();
		try {
			object = ((ProceedingJoinPoint) joinPoint).proceed();
			long end = System.currentTimeMillis();
			if (logger.isInfoEnabled()) {
				logger.info("around " + joinPoint + "\tUse time : "
						+ (end - start) + " ms!");
			}
			logger.info("==========结束执行controller环绕通知===============");
		} catch (Throwable e) {
			long end = System.currentTimeMillis();
			if (logger.isInfoEnabled()) {
				logger.info("around " + joinPoint + "\tUse time : "
						+ (end - start) + " ms with exception : "
						+ e.getMessage());
			}
		}
		return object;
	}

	/**
	 * 后置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@After("controllerAspect()")
	public void after(JoinPoint joinPoint) {
		try {
			// logger.info("=====controller后置通知开始=====");
			this.saveSystemLog(joinPoint);
			// logger.info("=====controller后置通知结束=====");
		} catch (Exception e) {
			e.printStackTrace();
			// 记录本地异常日志
			logger.error("==后置通知异常==");
			logger.error("异常信息:{}", e.getMessage());
		}
	}

	// 配置后置返回通知,使用在方法aspect()上注册的切入点
	// @AfterReturning("controllerAspect()")
	public void afterReturn(JoinPoint joinPoint) {
		logger.info("=====执行controller后置返回通知=====");
		if (logger.isInfoEnabled()) {
			logger.info("afterReturn " + joinPoint);
		}
	}

	/**
	 * 异常通知 用于拦截记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		logger.info("=====异常通知开始=====");
		try {
			this.saveExceptionLog(joinPoint, e);
			logger.info("=====异常通知结束=====");
		} catch (Exception ex) {
			// 记录本地异常日志
			logger.error("==异常通知异常==");
			logger.error("异常信息:{}", ex.getMessage());
		}
	}

	/**
	 * 记录系统日志：操作日志+业务日志
	 * 
	 * @throws ClassNotFoundException
	 **/
	private void saveSystemLog(JoinPoint joinPoint)
			throws ClassNotFoundException {
		this.saveLog(joinPoint, null);
	}

	/**
	 * 记录异常日志
	 * 
	 * @param joinPoint
	 * @throws ClassNotFoundException
	 */
	private void saveExceptionLog(JoinPoint joinPoint, Throwable e)
			throws ClassNotFoundException {
		this.saveLog(joinPoint, e);
	}

	/**
	 * 保存日志
	 * 
	 * @param joinPoint
	 * @param e
	 * @throws ClassNotFoundException
	 */
	private void saveLog(JoinPoint joinPoint, Throwable e)
			throws ClassNotFoundException {
		String params = "";
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			Object[] paramValues = joinPoint.getArgs();
			String[] paramNames = ((CodeSignature) joinPoint.getSignature())
					.getParameterNames();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				paramMap.put(paramNames[i], paramValues[i]);
			}
			params = JsonUtils.toJSONString(paramMap);
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(); // 读取session中的用户
		// User user = (User) session.getAttribute(WebConstants.CURRENT_USER);
		// //获取请求ip
		String ip = request.getRemoteAddr();

		// 获取用户请求方法的参数并序列化为JSON格式字符串
		User user = TokenManager.getToken();

		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String logType = "";
		String logDesc = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					logType = method.getAnnotation(Log.class).logType();
					logDesc = method.getAnnotation(Log.class).logDesc();
					break;
				}
			}
		}
		/* ========控制台输出========= */
		// logger.info("请求方法:" + (targetName + "." + methodName + "()"));
		// logger.info("方法描述:" + logDesc);
		// logger.info("请求人:" + user.getRealName());
		// logger.info("请求IP:" + ip);
		// logger.info("请求参数:" + params);
		/* ==========数据库日志========= */
		SystemLog log = new SystemLog();
		log.setLid(UUID.randomUUID().toString());
		log.setDescription(logDesc);
		if (e != null) {
			log.setExceptionCode(e.getClass().getSimpleName());
			log.setExceptionDetail(e.getMessage());
			/* ==========记录本地异常日志========== */
			logger.info("异常代码:" + e.getClass().getName());
			logger.info("异常信息:" + e.getMessage());
			logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget()
					.getClass().getName()
					+ joinPoint.getSignature().getName(), e.getClass()
					.getName(), e.getMessage(), params);
		}
		log.setLogType(logType);
		log.setMethod(targetName + "." + methodName + "()");
		log.setParams(params);
		if (user != null) {
			log.setCreator(user.getRealName() + "（" + user.getUsername() + "）");
			log.setUid(user.getUid());
		}
		log.setCreatedTime(new Date());
		log.setRequestIp(ip);
		String[] functionArr = targetName.split("[.]");
		if (functionArr != null && functionArr.length > 0) {
			String functionController = functionArr[functionArr.length - 1];
			if ("LoginController".equals(functionController)) {
				log.setRelationFunctionCode("LOGIN");
				log.setRelationFunctionDetail("用户登录（LOGIN）");
			} else {
				String key = functionController.replace("Controller", "")
						.toLowerCase();
				Function f = (Function) LoadInfo.initFunctionInfo.get(key);
				if (f != null) {
					log.setRelationFunctionCode(f.getFid());
					log.setRelationFunctionDetail(f.getFname() + "（"
							+ f.getFcode() + "）");
				}
			}
		}
		// 保存数据库
		systemLogService.insert(log);

	}
}