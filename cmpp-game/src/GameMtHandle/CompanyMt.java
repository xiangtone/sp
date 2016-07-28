package GameMtHandle;

public class CompanyMt {
	private String company = "";
	private String game = "";
	private String content = "";
	private String cpn = "";
	private String msgId = "";
	private String linkId = "";
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
		this.msgId = msgId;	
	}
		public void setLinkId(String linkId){
		this.linkId = linkId;	
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
		return this.msgId;	
	}
		public String getLinkId(){
		return this.linkId;	
	}
}
