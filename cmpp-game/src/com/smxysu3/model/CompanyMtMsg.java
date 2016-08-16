package com.smxysu3.model;

public class CompanyMtMsg {
	private int id;

	private String company;

	private String serverId;

	private String mtmsg;

	private String createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMtmsg() {
		return mtmsg;
	}

	public void setMtmsg(String mtmsg) {
		this.mtmsg = mtmsg;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

}
