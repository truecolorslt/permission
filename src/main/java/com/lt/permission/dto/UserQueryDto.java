package com.lt.permission.dto;

public class UserQueryDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String realName;
	private String dname;
	private String did;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public UserQueryDto(String currentPage, String pageSize) {
		this.setCurrentPage(Integer.parseInt(currentPage == null ? "1"
				: currentPage));
		this.setPageSize(Integer.parseInt(pageSize == null ? "10" : pageSize));
	}
}
