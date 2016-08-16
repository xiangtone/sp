package GameMtHandle;

public class CompanyMo {
	private String game = "";
	private String company = "";
	private String userInput = "";
	private String cpn = "";
	private String msgId = "";
	private String linkId = "";

	private String gwId = "";
	private String spCode = "";
	private String serverId = "";
	private String feeFlag = "";
	private String cpProductId = "";
	/*
	 * mo.setMsgId(msgId); mo.setMobile(cpn); mo.setMobileType("" + cpnType);
	 * mo.setUserType("" + userType); mo.setGwId("cshdyd");
	 * mo.setSpCode("106650081"); mo.setServiceType(serverId);
	 * mo.setLinkId(linkid); mo.setMoMsg(userInput); mo.setFeeFlag("1");
	 * mo.setCpProductId("101011");
	 * 
	 */

	public void setGame(String game) {
		this.game = game;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}

	public void setCpn(String cpn) {
		this.cpn = cpn;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public void setGwId(String gwId) {
		this.gwId = gwId;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}

	public void setCpProductId(String cpProductId) {
		this.cpProductId = cpProductId;
	}

	public String getGame() {
		return this.game;
	}

	public String getCompany() {
		return this.company;
	}

	public String getUserInput() {
		return this.userInput;
	}

	public String getCpn() {
		return this.cpn;
	}

	public String getMsgId() {
		return this.msgId;
	}

	public String getLinkId() {
		return this.linkId;
	}

	public String getGwId() {
		return this.gwId;
	}

	public String getSpCode() {
		return this.spCode;
	}

	public String getServerId() {
		return this.serverId;
	}

	public String getFeeFlag() {
		return this.feeFlag;
	}

	public String getCpProductId() {
		return this.cpProductId;
	}
}
