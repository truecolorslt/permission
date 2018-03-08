package com.lt.permission.dto;

public class RoleQueryDto extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rname;
	private String rcode;

	public RoleQueryDto(String currentPage, String pageSize) {
		this.setCurrentPage(Integer.parseInt(currentPage == null ? "1"
				: currentPage));
		this.setPageSize(Integer.parseInt(pageSize == null ? "10" : pageSize));
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getRcode() {
		return rcode;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode;
	}

}
