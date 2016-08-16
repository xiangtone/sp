package com.smxysu3.model;

public class MoData {

	private int id;
	// 合作方公司名称
	private String company;
	// 被扣量的合作方
	private String recp;
	// 业务名称
	private String game;
	// 用户输入
	private String userinput;
	// 手机号码
	private String cpn;
	// msgid
	private String msgid;
	// linkid
	private String linkid;
	// 添加时间
	private String addate;
	// 发送给合作方的状态标志，0为未发送，1为发送
	private short tocompstat;
	// 合作方接收到为0 其他为未接收到
	private String comprecstat;

	private int moState;

	// 上行状态：0正常通过，1合作方扣量，2达到日限，3达到月限，4屏蔽，5灰号码，99白号码
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

	public String getUserinput() {
		return userinput;
	}

	public void setUserinput(String userinput) {
		this.userinput = userinput;
	}

	public String getCpn() {
		return cpn;
	}

	public void setCpn(String cpn) {
		this.cpn = cpn;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getLinkid() {
		return linkid;
	}

	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}

	public String getAddate() {
		return addate;
	}

	public void setAddate(String addate) {
		this.addate = addate;
	}

	public short getTocompstat() {
		return tocompstat;
	}

	public void setTocompstat(short tocompstat) {
		this.tocompstat = tocompstat;
	}

	public String getComprecstat() {
		return comprecstat;
	}

	public void setComprecstat(String comprecstat) {
		this.comprecstat = comprecstat;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getRecp() {
		return recp;
	}

	public void setRecp(String recp) {
		this.recp = recp;
	}

	public void setMoState(int state) {
		this.moState = state;
	}

	public int getMoState() {
		return this.moState;
	}

}
