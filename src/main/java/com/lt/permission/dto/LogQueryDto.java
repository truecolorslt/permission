package com.lt.permission.dto;

public class LogQueryDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LogQueryDto(String currentPage, String pageSize) {
		this.setCurrentPage(Integer.parseInt(currentPage == null ? "1"
				: currentPage));
		this.setPageSize(Integer.parseInt(pageSize == null ? "10" : pageSize));
	}

	private String creator;
	private String logType;
	private String startDate;
	private String endDate;

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
