package com.smxysu3.model;

public class MoData {

	private int id;
	// ��������˾����
	private String company;
	// �������ĺ�����
	private String recp;
	// ҵ������
	private String game;
	// �û�����
	private String userinput;
	// �ֻ�����
	private String cpn;
	// msgid
	private String msgid;
	// linkid
	private String linkid;
	// ���ʱ��
	private String addate;
	// ���͸���������״̬��־��0Ϊδ���ͣ�1Ϊ����
	private short tocompstat;
	// ���������յ�Ϊ0 ����Ϊδ���յ�
	private String comprecstat;

	private int moState;

	// ����״̬��0����ͨ����1������������2�ﵽ���ޣ�3�ﵽ���ޣ�4���Σ�5�Һ��룬99�׺���
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
