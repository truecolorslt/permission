package com.lt.permission.dto;

/**
 * 数据字典操作dto
 * 
 * @author lantian
 * 
 */
public class DictDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String did;
	private String dname;
	private String dcode;
	private String remark;

	public String getDcode() {
		return dcode;
	}

	public void setDcode(String dcode) {
		this.dcode = dcode;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
