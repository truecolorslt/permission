package com.lt.permission.dto.function;

import com.lt.permission.dto.BaseDto;

public class FunctionAddDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fname;

	private String fcode;

	private String pfid;

	private String fsort;

	private String ficon;

	private String furl;

	private String frelation;

	private String fid;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFrelation() {
		return frelation;
	}

	public void setFrelation(String frelation) {
		this.frelation = frelation;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFcode() {
		return fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	public String getPfid() {
		return pfid;
	}

	public void setPfid(String pfid) {
		this.pfid = pfid;
	}

	public String getFsort() {
		return fsort;
	}

	public void setFsort(String fsort) {
		this.fsort = fsort;
	}

	public String getFicon() {
		return ficon;
	}

	public void setFicon(String ficon) {
		this.ficon = ficon;
	}

	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

}
