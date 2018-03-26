package com.lt.permission.init;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lt.permission.model.Function;
import com.lt.permission.service.IFunctionService;

public class WebStartup implements ServletContextListener {
	private final Logger logger = Logger.getLogger(getClass());

	private IFunctionService functionService;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(event.getServletContext());
		functionService = (IFunctionService) context.getBean("functionService");
		logger.info("\t系统启动开始...");
		logger.info("\t初始化 [功能菜单信息]开始 ...");
		List<Function> functionList = functionService.findAllFunctionTrees();
		if (functionList != null && functionList.size() > 0) {
			for (Function f : functionList) {
				String furl = f.getFurl();
				if (StringUtils.isEmpty(furl)) {
					continue;
				}
				String key = furl.split("/")[0];
				logger.info("\t初 [功能菜单-" + f.getFname() + "(" + f.getFcode()
						+ ")]初始化完成 ...");
				LoadInfo.initInfo.put(key, f);
			}
		}
		logger.info("\t初始化 [功能菜单信息]完成 ...");
		logger.info("\t系统启动完成...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("\t系统销毁开始 ...");
		logger.info("\t系统销毁完成 !!!");
	}

}
