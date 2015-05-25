package com.pmc.crackanalyse.model;

import java.util.Date;

public class CrackReport {
	private String id;
	private Date crackTime;
	private String crackType;
	private String crackInfo;
	private String crackStackinfo;
	private CrackDeviceInfo crackDeviceInfo;
	
	public CrackReport(){
		
	}
	public CrackDeviceInfo getCrackDeviceInfo() {
		return crackDeviceInfo;
	}
	public void setCrackDeviceInfo(CrackDeviceInfo crackDeviceInfo) {
		this.crackDeviceInfo = crackDeviceInfo;
	}
	public Date getCrackTime() {
		return crackTime;
	}
	public void setCrackTime(Date crackTime) {
		this.crackTime = crackTime;
	}
	public String getCrackType() {
		return crackType;
	}
	public void setCrackType(String crackType) {
		this.crackType = crackType;
	}
	public String getCrackInfo() {
		return crackInfo;
	}
	public void setCrackInfo(String crackInfo) {
		this.crackInfo = crackInfo;
	}
	public String getCrackStackinfo() {
		return crackStackinfo;
	}
	public void setCrackStackinfo(String crackStackinfo) {
		this.crackStackinfo = crackStackinfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	
}
