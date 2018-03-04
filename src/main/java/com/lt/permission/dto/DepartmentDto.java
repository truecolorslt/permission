package com.lt.permission.dto;

public class DepartmentDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dname;

	private String dcode;

	private String pdid;

	private String dsort;

	private String drelation;

	private String did;

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

	public String getPdid() {
		return pdid;
	}

	public void setPdid(String pdid) {
		this.pdid = pdid;
	}

	public String getDsort() {
		return dsort;
	}

	public void setDsort(String dsort) {
		this.dsort = dsort;
	}

	public String getDrelation() {
		return drelation;
	}

	public void setDrelation(String drelation) {
		this.drelation = drelation;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

}
