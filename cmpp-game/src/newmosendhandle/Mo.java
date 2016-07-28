package newmosendhandle;

public class Mo {
	private String msgId = "";
	private String mobile = "";
	private String mobileType = "";
	private String userType = "";
	private String gwId = "";
	private String spCode = "";
	private String serviceType = "";
	private String linkId = "";
	private String moMsg = "";
	private String feeFlag = "";
	private String cpProductId = "";
	private String cpId = "";
	private String addate = "";

	public void setMsgId(String msgId){
		this.msgId = msgId;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	public void setMobileType(String mobileType){
		this.mobileType = mobileType;
	}
	public void setUserType(String userType){
		this.userType = userType;
	}
	public void setGwId(String gwId){
		this.gwId = gwId;
	}
	public void setSpCode(String spCode){
		this.spCode = spCode;
	}
	public void setServiceType(String serviceType){
		this.serviceType = serviceType;
	}
	public void setLinkId(String linkId){
		this.linkId = linkId;
	}
	public void setMoMsg(String moMsg){
		this.moMsg = moMsg;
	}
	public void setFeeFlag(String feeFlag){
		this.feeFlag = feeFlag;
	}
	public void setCpProductId(String cpProductId){
		this.cpProductId = cpProductId;
	}
	public void setAddate(String addate){
		this.addate = addate;
	}
	/*
	public void setCpId(String cpId){
		this.cpId = cpId;
	}
*/
	public String getCpId(){
		return this.cpId;
	}
	public String getCpProductId(){
		return this.cpProductId;
	}
	public String getFeeFlag(){
		return this.feeFlag;
	}
	public String getMoMsg(){
		return this.moMsg;
	}
	public String getLinkId(){
		return this.linkId;
	}
	public String getMsgId(){
		return this.msgId;
	}
	public String getMobile(){
		return this.mobile;
	}
	public String getMobileType(){
		return this.mobileType;
	}
	public String getUserType(){
		return this.userType;
	}
	public String getGwId(){
		return this.gwId;
	}
	public String getSpCode(){
		return this.spCode;
	}
	public String getServiceType(){
		return this.serviceType;
	}
	public String getAddate(){
		return this.addate;	
	}
}
