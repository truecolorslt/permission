package com.lt.permission.dto;

/**
 * 数据字典查询dto
 * 
 * @author lantian
 * 
 */
public class DictQueryDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dname;
	private String dcode;

	public DictQueryDto(String currentPage, String pageSize) {
		this.setCurrentPage(Integer.parseInt(currentPage == null ? "1"
				: currentPage));
		this.setPageSize(Integer.parseInt(pageSize == null ? "10" : pageSize));
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getDcode() {
		return dcode;
	}

	public void setDcode(String dcode) {
		this.dcode = dcode;
	}

}
