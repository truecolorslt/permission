package com.lt.permission.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 返回参数基类
 * 
 * @author lantian
 * 
 */
public class BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 返回结果编码 */
	private String resultCode;
	/** 返回结果错误描述，正确时为空 */
	private String resultMsg;
	/** 返回的对象 */
	private Object resultObject;
	/** 返回总记录数 */
	private Integer totalCount;

	/** 返回总页数 */
	private Integer totalPage;

	/** 返回结果集 */
	private List<?> resultList;

	/**
	 * 返回key-value形式的结构
	 */
	private Map<String, Object> resultMap;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<?> getResultList() {
		return resultList;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

}
