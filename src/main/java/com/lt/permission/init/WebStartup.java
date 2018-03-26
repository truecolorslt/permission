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

import com.lt.permission.dto.DictQueryDto;
import com.lt.permission.model.Dict;
import com.lt.permission.model.Function;
import com.lt.permission.service.IDictService;
import com.lt.permission.service.IFunctionService;

public class WebStartup implements ServletContextListener {
	private final Logger logger = Logger.getLogger(getClass());

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("系统启动开始...");
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(event.getServletContext());
		// 初始化功能菜单数据至内存
		this.initFunctionInfo(context);
		// 初始化数据字典至内存
		this.initDictnInfo(context);
		logger.info("系统启动完成...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("系统销毁开始 ...");
		logger.info("系统销毁完成 !!!");
	}

	/**
	 * 初始化功能菜单数据
	 */
	private void initFunctionInfo(WebApplicationContext context) {
		logger.info("###################初始化 [功能菜单信息]开始 ...###################");
		IFunctionService functionService = (IFunctionService) context
				.getBean("functionService");
		List<Function> functionList = functionService.findAllFunctionTrees();
		if (functionList != null && functionList.size() > 0) {
			for (Function f : functionList) {
				String furl = f.getFurl();
				if (StringUtils.isEmpty(furl)) {
					continue;
				}
				String key = furl.split("/")[0];
				logger.info(" [功能菜单-" + f.getFname() + "(" + f.getFcode()
						+ ")]初始化完成 ...");
				LoadInfo.initFunctionInfo.put(key, f);
			}
		}
		logger.info("###################初始化 [功能菜单信息]完成 ...###################");
	}

	/**
	 * 初始化数据字典数据
	 */
	private void initDictnInfo(WebApplicationContext context) {
		logger.info("###################初始化 [数据字典信息]开始 ...###################");
		IDictService dictService = (IDictService) context
				.getBean("dictService");
		List<Dict> dictList = dictService.getAllDicts();
		if (dictList != null && dictList.size() > 0) {
			for (Dict d : dictList) {
				logger.info("[数据字典-" + d.getDname() + "(" + d.getDcode()
						+ ")]初始化完成 ...");
				DictQueryDto queryDto = new DictQueryDto();
				queryDto.setDid(d.getDid());
				List<Dict> subDict = dictService.getDictsByPdid(queryDto);
				LoadInfo.initDictInfo.put(d.getDcode(), subDict);
			}
		}
		logger.info("###################初始化 [数据字典信息]结束 ...###################");
	}
}
