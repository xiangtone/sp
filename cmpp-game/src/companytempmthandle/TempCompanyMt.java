package companytempmthandle;

public class TempCompanyMt {

	private String checkTime;
	private String tableName;
	private int id;
	private String company;
	private String game;
	private String content;
	private String cpn;
	private String msgid;
	private String linkid;
	private String submit_linkid;
	private String submitmsgid;
	private String addate;
	private String sendstate;
	private String comprecstat;
	private String province;
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setCompany(String company){
		this.company = company;
	}
	public void setGame(String game){
		this.game = game;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setCpn(String cpn){
		this.cpn = cpn;
	}
	public void setMsgId(String msgId){
		this.msgid = msgId;
	}
	public void setLinkId(String linkId){
		this.linkid = linkId;
	}
	public void setSubmitLinkid(String submitLinkid){
		this.submit_linkid = submitLinkid;
	}
	public void setAddate(String addate){
		this.addate = addate;
	}
	public void setSendstate(String sendstate){
		this.sendstate = sendstate;
	}
	public void setComprecstat(String comprecstat){
		this.comprecstat = comprecstat;
	}
	public void setProvince(String province){
		this.province = province;
	}
	
	public String getTableName(){
		return this.tableName;
	}
	public int getId(){
		return this.id;
	}
	public String getCompany(){
		return this.company;
	}

	public String getGame(){
		return this.game;
	}
	public String getContent(){
		return this.content;
	}
	public String getCpn(){
		return this.cpn;
	}
	public String getMsgId(){
		return this.msgid;
	}

	public String getLinkId(){
		return this.linkid;
	}
	public String getSubmitLinkid(){
		return this.submit_linkid;
	}
	public String getSubmitmsgid(){
		return this.submitmsgid;
	}
	public String getAddate(){
		return this.addate;
	}
	public String getSendstate(){
		return this.sendstate;
	}
	public String getComprecstat(){
		return this.comprecstat;
	}
	public String getProvince(){
		return this.province;
	}
}
