package com.lt.permission.model;

import java.util.Date;

public class Dict {
	private String did;

	private String pdid;

	private String dcode;

	private String dname;

	private String dkey;

	private String dvalue;

	private Integer dsort;

	private String remark;

	private Date createdTime;

	private String creator;

	private Date modifiedTime;

	private String modifier;

	private Integer isDelete;

	private Integer version;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did == null ? null : did.trim();
	}

	public String getPdid() {
		return pdid;
	}

	public void setPdid(String pdid) {
		this.pdid = pdid == null ? null : pdid.trim();
	}

	public String getDcode() {
		return dcode;
	}

	public void setDcode(String dcode) {
		this.dcode = dcode == null ? null : dcode.trim();
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname == null ? null : dname.trim();
	}

	public String getDkey() {
		return dkey;
	}

	public void setDkey(String dkey) {
		this.dkey = dkey == null ? null : dkey.trim();
	}

	public String getDvalue() {
		return dvalue;
	}

	public void setDvalue(String dvalue) {
		this.dvalue = dvalue == null ? null : dvalue.trim();
	}

	public Integer getDsort() {
		return dsort;
	}

	public void setDsort(Integer dsort) {
		this.dsort = dsort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}