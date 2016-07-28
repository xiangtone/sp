package companysmosbackup;

public class Mo {
	private String company = "";
	private String game = "";
	private String userinput = "";
	private String cpn = "";
	private String msgId = "";
	private String linkId = "";
	private String addTime = "";
	private int toCompStat = -1;
	private String comprecStat = "";
	private String province = "";

	public void setCompany(String company){
		this.company = company;
	}
	public void setGame(String game){
		this.game = game;
	}
	public void setUserinput(String userinput){
		this.userinput = userinput;
	}
	public void setCpn(String cpn){
		this.cpn = cpn;
	}
	public void setMsgId(String msgId){
		this.msgId = msgId;
	}
	public void setLinkId(String linkId){
		this.linkId = linkId;
	}
	public void setAddTime(String addTime){
		this.addTime = addTime;
	}
	public void setToCompStat(int toCompStat){
		this.toCompStat = toCompStat;
	}
	public void setComprecStat(String comprecStat){
		this.comprecStat = comprecStat;
	}
	public void setProvince(String province){
		this.province = province;	
	}
	/*
	public void setCpId(String cpId){
		this.cpId = cpId;
	}
	
	private String company = "";
	private String game = "";
	private String userinput = "";
	private String cpn = "";
	private String msgId = "";
	private String linkId = "";
	private String addTime = "";
	private int toCompStat = -1;
	private String comprecStat = "";
*/
	public String getCompany(){
		return this.company;
	}
	public String getGame(){
		return this.game;
	}
	public String getUserinput(){
		return this.userinput;
	}
	public String getCpn(){
		return this.cpn;
	}
	public String getLinkId(){
		return this.linkId;
	}
	public String getMsgId(){
		return this.msgId;
	}
	public String getAddTime(){
		return this.addTime;
	}
	public int getToCompStat(){
		return this.toCompStat;
	}
	public String getComprecStat(){
		return this.comprecStat;
	}
	public String getProvince(){
		return this.province;	
	}
}
