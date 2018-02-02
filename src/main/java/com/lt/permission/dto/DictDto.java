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
	private String dkey;
	private String dvalue;
	private String dsort;
	private String pdid;

	public String getPdid() {
		return pdid;
	}

	public void setPdid(String pdid) {
		this.pdid = pdid;
	}

	public String getDkey() {
		return dkey;
	}

	public void setDkey(String dkey) {
		this.dkey = dkey;
	}

	public String getDvalue() {
		return dvalue;
	}

	public void setDvalue(String dvalue) {
		this.dvalue = dvalue;
	}

	public String getDsort() {
		return dsort;
	}

	public void setDsort(String dsort) {
		this.dsort = dsort;
	}

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
