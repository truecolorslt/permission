package com.lt.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.lt.permission.annotation.Log;
import com.lt.permission.common.DictConstants;
import com.lt.permission.dto.LogQueryDto;
import com.lt.permission.model.SystemLog;
import com.lt.permission.service.ISystemLogService;
import com.lt.permission.util.DateUtil;
import com.lt.permission.util.JsonUtils;
import com.lt.permission.vo.LogVo;

/**
 * 系统日志控制器
 * 
 * @author lantian
 * 
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ISystemLogService systemLogService;

	/**
	 * 进入日志管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logMgt")
	public String deptMgt() {
		return "/log/log_mgt";
	}

	/**
	 * 查询字典列表
	 * 
	 * @param page
	 * @param rows
	 * @param dname
	 * @param dcode
	 * @return
	 */
	@RequestMapping(value = "/findLogs")
	@ResponseBody
	public String findDicts(
			@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "rows", defaultValue = "10") String rows,
			@RequestParam(value = "logType", required = false) String logType,
			@RequestParam(value = "creator", required = false) String creator,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {
		String dictsJson = "";
		JSONObject jo = new JSONObject();
		try {
			LogQueryDto queryDto = new LogQueryDto(page, rows);
			queryDto.setLogType(logType);
			queryDto.setCreator(creator);
			queryDto.setStartDate(startDate);
			queryDto.setEndDate(endDate);

			LogVo logVo = systemLogService.findLogsByPage(queryDto);
			if (logVo != null) {
				List<SystemLog> logList = (List<SystemLog>) logVo
						.getResultList();
				Integer count = logVo.getTotalCount();

				List<Map<String, Object>> mapList = null;
				if (logList != null && logList.size() > 0) {
					mapList = new ArrayList<Map<String, Object>>();

					for (SystemLog l : logList) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("lid", l.getLid());
						map.put("description", l.getDescription());
						map.put("method", l.getMethod());
						map.put("logType", this.getAttrValue(
								DictConstants.DICT_CODE_LOG_TYPE,
								l.getLogType()));
						map.put("requestIp", l.getRequestIp());
						map.put("functionCode", l.getRelationFunctionCode());
						map.put("functionDetail", l.getRelationFunctionDetail());
						map.put("exceptionCode", l.getExceptionCode());
						map.put("exceptionDetail", l.getExceptionDetail());
						map.put("param", l.getParams());
						map.put("createdTime", DateUtil.formatDate(
								l.getCreatedTime(), "yyyy-MM-dd HH:mm:ss"));
						map.put("creator", l.getCreator());
						map.put("uid", l.getUid());
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
				dictsJson = this.toJSONObject(jo).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictsJson;
	}
}
