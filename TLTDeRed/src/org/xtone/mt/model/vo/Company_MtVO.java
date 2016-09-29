package org.xtone.mt.model.vo;

/**
 * 合作方下行日表实体类
 * @author liaopengjie
 * @since 2011-09-23
 */
public class Company_MtVO {
	private int id;
	private String company;
	private String companyName;
	private String game;
	private String gameName;
	private String date;
	private String content;
	private String cpn;
	private String msgid;
	private String linkid;
	private String addate;
	private String sendstate;
	private String comprecstat;
	private String province;
	private String provinceName;
	private String postcode;
	private String city;
	private String lostflag;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	private String tableName;//表名称
	private int num;//统计灰号码用到的手机号码条数
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getSendstate() {
		return sendstate;
	}
	public void setSendstate(String sendstate) {
		this.sendstate = sendstate;
	}
	public String getComprecstat() {
		return comprecstat;
	}
	public void setComprecstat(String comprecstat) {
		this.comprecstat = comprecstat;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLostflag() {
		return lostflag;
	}
	public void setLostflag(String lostflag) {
		this.lostflag = lostflag;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
