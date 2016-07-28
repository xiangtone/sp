package GameMtHandle;
import java.sql.*;
import DBHandle.*;
public class SmsMoMtHandle {
	public MysqldbT mydb;
	public CompanyMo compMo;
	public CompanyMt compMt;
	//public CompanyCount companyCount;
	private String status = "";
	public SmsMoMtHandle(String dbIp,String dbName,String dbUser,String dbPwd,String dbPort){
		mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
		compMo = new CompanyMo();
		compMt = new CompanyMt();
		//companyCount = new CompanyCount();
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

			}
	//////////////////////////以下用于上行的记录统计的操作//////////////////
	private void insertMo(){
		try{
			String companyName = getCompanyName(this.compMo.getCompany());
			//String strSql = "insert into company_mo set company='" + companyName + "',game='" + this.compMo.getGame() + "',userinput='" + this.compMo.getUserInput() + "',cpn = '" + this.compMo.getCpn() + "',linkid='" + this.compMo.getMsgId() +"',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='" + this.status + "'";
			//System.out.println(strSql);
			//String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mo";
			//String strSql = "insert into company_mo set company='" + companyName + "',game='" + this.compMo.getGame() + "',userinput='" + this.compMo.getUserInput() + "',cpn = '" + this.compMo.getCpn() + "',msgid='" + this.compMo.getMsgId() +"',linkid='" + this.compMo.getLinkId() +"',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='-1'";
			String tableName = "tempcompany_mo";
			String strSql = "insert into " + tableName + " set company='" + companyName + "',game='" + this.compMo.getGame() + "',userinput='" + this.compMo.getUserInput() + "',cpn = '" + this.compMo.getCpn() + "',msgid='" + this.compMo.getMsgId() +"',linkid='" + this.compMo.getLinkId() +"',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='-1'";
			System.out.println(strSql);
			mydb.executeUpdate(strSql);
			String strSqlMos = "insert into company_mos set company='" + companyName + "',game='" + this.compMo.getGame() + "',userinput='" + this.compMo.getUserInput() + "',cpn = '" + this.compMo.getCpn() + "',msgid='" + this.compMo.getMsgId() +"',linkid='" + this.compMo.getLinkId() +"',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='-1'";
			mydb.executeUpdate(strSqlMos);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	////////////////////以下用于下行记录统计的操作//////////////////////////
	private void insertMt(){
		try{
			String pmId = this.checkGameCode(this.compMt.getCpn(),this.compMt.getGame());
			//String[] companysTemp = (this.compMt.getCompany()).split(":");
			String companyName = "";//getCompanyName(this.compMt.getCompany());
			String recp="0";
			//////////////////////
			if((this.compMt.getCompany()).indexOf(",")!=-1){
					//System.out.println("---------------存在扣量合作方");
					String companysTemp[]=(this.compMt.getCompany()).split(",");
					companyName=companysTemp[0];
					recp=companysTemp[1];
		  }
			else if((this.compMt.getCompany()).indexOf(":")!=-1){
				String[] companysTemp = (this.compMt.getCompany()).split(":");
				companyName = companysTemp[0];
			}
			String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
			//String strSql = "insert into "+ tableName +" set company='" + companyName + "',game='" + this.compMt.getGame() + "',content='" + this.compMt.getContent() + "',msgid='" + this.compMt.getMsgId() + "',linkid='" + this.compMt.getLinkId() + "',cpn = '" + this.compMt.getCpn() + "',addate='" + FormatSysTime.getCurrentTimeA() + "',sendstate='-3'";
			//String strSql = "insert into tempcompany_mt set company='" + companyName + "',game='" + this.compMt.getGame() + "',content='" + this.compMt.getContent() + "',msgid='" + this.compMt.getMsgId() + "',linkid='" + this.compMt.getLinkId() + "',submit_linkid='" + this.compMt.getLinkId() + "',cpn = '" + this.compMt.getCpn() + "',addate='" + FormatSysTime.getCurrentTimeA() + "',sendstate='-3'";
			String strSql = "insert into tempcompany_mt set company='" + companyName + "',game='" + this.compMt.getGame() + "',content='" + this.compMt.getContent() + "',msgid='" + this.compMt.getMsgId() + "',linkid='" + this.compMt.getLinkId() + "',submit_linkid='" + this.compMt.getLinkId() + "',cpn = '" + this.compMt.getCpn() + "',addate='" + FormatSysTime.getCurrentTimeA() + "',sendstate='-3',pmid='" + pmId + "'";
			mydb.executeUpdate(strSql);
			//strSql = "insert into companyscpns set company='" + companyName + "',cpn = '" + this.compMt.getCpn() + "',month = now(),addate=now()";
			//System.out.println("::::::::::::::::::::::::" + strSql);
			//mydb.executeUpdate(strSql);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

//用于随机取得相应业务Id号。
//add at 2011-11-24
private String checkGameCode(String cpn,String game){
	String tempPmId = "";
	try{
		
		String strSql = "";
		strSql = "select pmid from tempcompany_mt where cpn='"+ cpn +"' and game='" + game + "' and pmid !=''";
		System.out.println(strSql);
		ResultSet rs = mydb.executeQuery(strSql);
		if(rs.next()){
			tempPmId = rs.getString("pmid");	
		}
		else{
			  String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
				strSql = "select pmid from " + tableName + " where cpn='"+cpn+"' and game='" + game + "' and pmid !=''";
				rs = mydb.executeQuery(strSql);
				if(rs.next()){
					tempPmId = rs.getString("pmid");	
				}
				else{
					strSql = "select gcode from pm_allimformation ORDER BY rand( ) LIMIT 1";
					rs = mydb.executeQuery(strSql);
					if(rs.next()){
							tempPmId = rs.getString("gcode");
					}
				}
		}
		//return tempPmId;
	}catch(Exception e){
		e.printStackTrace();
	}
	return tempPmId;
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
