package companycount;
import java.sql.*;
public class SmsCompanyMtHandle {
	public MtMysqldb mydb;
	public CompanyMo compMo;
	public CompanyMt compMt;
	public CompanyCount companyCount;
	private String status = "";
	public SmsCompanyMtHandle(){
		mydb = new MtMysqldb();
		compMo = new CompanyMo();
		compMt = new CompanyMt();
		companyCount = new CompanyCount();
	}
	public void smsCompanyMoHandle(){//处理上行
		
		insertMo();
		//getCompanyMoCount();//q取得相应的合作方的业务上行条数的统计记录
		//addCompanyMoCount();//添加上行记录
		
		//String strSql = "";
	}
	public boolean checkCpn(String cpn){//查询是否是黑号码。是返回TRUE 否返回FALSE
		String strSql = "select cpn_id from blackcpns where cpn='" + cpn + "'";	
		boolean checkFlag = true;
		try{
				ResultSet rs = mydb.executeQuery(strSql);
				if(rs.next()){
					checkFlag = true;	
				}
				else{
					checkFlag = false;	
				}
		}catch(Exception e){
			e.printStackTrace();
			checkFlag = true;
		}
		return checkFlag;
	}
	public void smsCompanyMtHandle(){
		
		insertMt();
		//getCompanyMtCount();//q取得相应的合作方的业务下行条数的统计记录

		//addCompanyMtCount();//添加上行记录
		//mydb.dbclose();
			}
	//////////////////////////以下用于上行的记录统计的操作//////////////////
	private void insertMo(){
		try{
			String companyName = getCompanyName(this.compMo.getCompany());
			//String strSql = "insert into company_mo set company='" + companyName + "',game='" + this.compMo.getGame() + "',userinput='" + this.compMo.getUserInput() + "',cpn = '" + this.compMo.getCpn() + "',linkid='" + this.compMo.getMsgId() +"',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='" + this.status + "'";
			//System.out.println(strSql);
			String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mo";
			//String strSql = "insert into company_mo set company='" + companyName + "',game='" + this.compMo.getGame() + "',userinput='" + this.compMo.getUserInput() + "',cpn = '" + this.compMo.getCpn() + "',msgid='" + this.compMo.getMsgId() +"',linkid='" + this.compMo.getLinkId() +"',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='-1'";
			String strSql = "insert into " + tableName + " set company='" + companyName + "',game='" + this.compMo.getGame() + "',userinput='" + this.compMo.getUserInput() + "',cpn = '" + this.compMo.getCpn() + "',msgid='" + this.compMo.getMsgId() +"',linkid='" + this.compMo.getLinkId() +"',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='-1'";
			System.out.println(strSql);
			mydb.executeUpdate(strSql);
			//String strSqlMos = "insert into company_mos set company='" + companyName + "',game='" + this.compMo.getGame() + "',userinput='" + this.compMo.getUserInput() + "',cpn = '" + this.compMo.getCpn() + "',msgid='" + this.compMo.getMsgId() +"',linkid='" + this.compMo.getLinkId() +"',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='-1'";
			//mydb.executeUpdate(strSqlMos);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private void addCompanyMoCount(){
		int moNum = this.companyCount.getMoNum();

		this.companyCount.setCompany(this.compMo.getCompany());
		this.companyCount.setGame(this.compMo.getGame());
		if(moNum > 0){
			addCompanyMoNum();
		}
		else{
			
			addNewCompanyMoNum();
		}
	}
	private void getCompanyMoCount(){//对已存在的合作方添加新的统计数字
		String companyName = getCompanyName(this.compMo.getCompany());
		String strSql = "select monum from company_mocount where company='" + companyName + "' and game='" + this.compMo.getGame() + "' and countdate='" + FormatSysTime.getCurrentTimeB() + "'";
		ResultSet rs = mydb.executeQuery(strSql);
		try{
			if(rs.next()){
				this.companyCount.setMoNum(rs.getInt("monum"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	private void addNewCompanyMoNum(){//添加新的合作方的统计业务
		try{
			String companyName = getCompanyName(this.compMo.getCompany());
			String strSql = "insert into company_mocount set company='" + companyName + "',game='" + this.companyCount.getGame() + "',monum=1,countdate='" + FormatSysTime.getCurrentTimeB() + "'";
			mydb.executeUpdate(strSql);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private void addCompanyMoNum(){
		try{
			String companyName = getCompanyName(this.compMo.getCompany());
			String strSql = "update company_mocount set monum='" + (this.companyCount.getMoNum() + 1) + "' where company='" + companyName + "' and game = '" + this.companyCount.getGame() + "' and countdate='" + FormatSysTime.getCurrentTimeB() + "'";
			mydb.executeUpdate(strSql);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	////////////////////以下用于下行记录统计的操作//////////////////////////
	private void insertMt(){
		try{
			String companyName = getCompanyName(this.compMt.getCompany());
			String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
			//String strSql = "insert into "+ tableName +" set company='" + companyName + "',game='" + this.compMt.getGame() + "',content='" + this.compMt.getContent() + "',msgid='" + this.compMt.getMsgId() + "',cpn = '" + this.compMt.getCpn() + "',addate='" + FormatSysTime.getCurrentTimeA() + "',sendstate='-3'";
			String strSql = "insert into "+ tableName +" set company='" + companyName + "',game='" + this.compMt.getGame() + "',content='" + this.compMt.getContent() + "',linkid='" + this.compMt.getLinkId() + "',submit_linkid = '" + this.compMt.getLinkId() + "',msgid = '" + this.compMt.getMsgId() + "',cpn = '" + this.compMt.getCpn() + "',addate='" + FormatSysTime.getCurrentTimeA() + "',sendstate='-3'";

			mydb.executeUpdate(strSql);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private void addCompanyMtCount(){
		int mtNum = this.companyCount.getMtNum();

		this.companyCount.setCompany(this.compMo.getCompany());
		this.companyCount.setGame(this.compMo.getGame());
		if(mtNum > 0){
			addCompanyMtNum();
		}
		else{
			addNewCompanyMtNum();
		}
	}
	private void addNewCompanyMtNum(){//添加新的合作方的统计业务
		try{
			String companyName = getCompanyName(this.compMt.getCompany());
			//String strSql = "update company_mtcount set mtnum=1 where company='" + this.companyCount.getCompany() + "' and game='" + this.companyCount.getGame() + "' and countdate='" + FormatSysTime.getCurrentTimeB() + "'";
			String strSql = "insert into company_mtcount set company='" + companyName + "',game='" + this.companyCount.getGame() + "',mtnum=1,countdate='" + FormatSysTime.getCurrentTimeB() + "'";

			mydb.executeUpdate(strSql);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private void addCompanyMtNum(){
		try{
			String companyName = getCompanyName(this.companyCount.getCompany());
			String strSql = "update company_mtcount set mtnum='" + (this.companyCount.getMtNum() + 1) + "' where company='" + companyName + "' and game = '" + this.companyCount.getGame() + "' and countdate='" + FormatSysTime.getCurrentTimeB() + "'";
			mydb.executeUpdate(strSql);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	private void getCompanyMtCount(){//对已存在的合作方添加新的统计数字
		String companyName = getCompanyName(this.compMo.getCompany());
		String strSql = "select mtnum from company_mtcount where company='" + companyName + "' and game='" + this.compMo.getGame() + "' and countdate='" + FormatSysTime.getCurrentTimeB() + "'";
		ResultSet rs = mydb.executeQuery(strSql);
		try{
			if(rs.next()){
				this.companyCount.setMtNum(rs.getInt("mtnum"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/////////////////////////////
	private String getCompanyName(String compTag){
		String strSql = "select company from companys where companytag='" + compTag + "'";
		ResultSet rs = mydb.executeQuery(strSql);
		String retCompanyName = "";
		try{
			if(rs.next()){
				retCompanyName = rs.getString("company");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retCompanyName;
	}
	public void setStatus(String status){
		this.status = status;
	}
}
