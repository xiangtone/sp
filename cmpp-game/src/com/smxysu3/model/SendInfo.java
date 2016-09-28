package com.smxysu3.model;

public class SendInfo {

	// id
	private int id;
	// 公司名称
	private String company;
	// 下发内容
	private String mtMessage;
	// 下发时间
	private String sendTime;

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

	public String getMtMessage() {
		return mtMessage;
	}

	public void setMtMessage(String mtMessage) {
		this.mtMessage = mtMessage;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

}
