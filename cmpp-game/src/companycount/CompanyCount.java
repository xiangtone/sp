package companycount;

public class CompanyCount {
	private String company = "";
	private String game = "";
	private int moNum = 0;
	private int mtNum = 0;

	public void setCompany(String company){
		this.company = company;
	}
	public void setGame(String game){
		this.game = game;
	}
	public void setMoNum(int moNum){
		this.moNum = moNum;
	}
	public void setMtNum(int mtNum){
		this.mtNum = mtNum;
	}

	public String getCompany(){
		return this.company;
	}
	public String getGame(){
		return this.game;
	}
	public int getMoNum(){
		return this.moNum;
	}
	public int getMtNum(){
		return this.mtNum;
	}
}
