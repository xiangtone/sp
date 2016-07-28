package smsgm_heartthrobarea;

public class User {
	private String cpn;
	private String step;
	private int enjoy;
	private String loginDate;
	private String regDate;
	private String retMsg;
	private String userInput;

	public void setCpn(String cpn){
		this.cpn = cpn;
	}
	public void setUserInput(String userInput){
		this.userInput = userInput;
	}
	public void setStep(String step){
		this.step = step;
	}
	public void setEnjoy(int enjoy){
		this.enjoy = enjoy;
	}
	public void setLoginDate(String loginDate){
		this.loginDate = loginDate;
	}
	public void setRegDate(String regDate){
		this.regDate = regDate;
	}
	public void setRetMsg(String retMsg){
		this.retMsg = retMsg;
	}


	public String getCpn(){
		return this.cpn;
	}
	public String getUserInput(){
		return this.userInput;
	}
	public String getStep(){
		return this.step;
	}
	public int getEnjoy(){
		return this.enjoy;
	}
	public String getLoginDate(){
		return this.loginDate;
	}
	public String getRegDate(){
		return this.regDate;
	}
	public String getRetMsg(){
		return this.retMsg;
	}
}
