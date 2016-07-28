package newsmsendstatecheck;
import java.sql.*;
import java.util.*;
import java.io.*;
import companycount.FormatSysTime;

public class SendStateHandleAgain {
	public Mysqldb mydb;
	static boolean checkFlag = false;
	String tableName = "";
	public SendStateHandleAgain(){
		
	}
	public void getUnUpdateMtState(){
		//用于取得那些发送状态还没更新的下行

		mydb = new Mysqldb();
		String strSql = "select company,game from companys group by company order by id desc";
		try{
			ResultSet rs = mydb.executeQuery(strSql);
			String cpn = "";
			String addate = "";
			String spcode = "";
			ArrayList companysList = new ArrayList();
			ArrayList gamesList = new ArrayList();
			//ArrayList spcodeArrayList = new ArrayList();
			while(rs.next()){
				//System.out.println(rs.getString("company"));
				//System.out.println(rs.getString("game"));
				companysList.add(rs.getString("company"));
				gamesList.add(rs.getString("game"));
				//spcodeArrayList.add(rs.getString("spcode"));
				//updateSendState(spcode,cpn,addate);
			}
			updateSendState(companysList,gamesList);
			checkFlag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void updateSendState(ArrayList companys,ArrayList games){
		//System.out.println(addate);
		String checkDate = FormatSysTime.getCurrentTimeB();
		
		int size = companys.size();
		String company = "";
		String game = "";
		String spcode = "";

		for(int i = 0;i < size;i++){
			company = (String)companys.get(i);
			game = (String)games.get(i);
			ArrayList linkIdsList = new ArrayList();
			ArrayList submitMsgIdsList = new ArrayList();
			ArrayList idsList = new ArrayList();
			String temptableName =  this.tableName + "company_mt";
			//String strSql = "select linkid from " + tableName + " where company='" + company + "' and addate >='" + checkDate +" 00:00:00' and addate <='" + checkDate +" 23:59:59'";
			//String strSql = "select distinct(linkid) from " + tableName + " where company='" + company + "' and sendstate = -3 order by id desc limit 100";
				String strSql = "select id,linkid,submitmsgid from " + temptableName + " where company='" + company + "' and sendstate = -3 order by id asc";

			System.out.println(strSql);
			try{
				ResultSet rs = mydb.executeQuery(strSql);
				while(rs.next()){
					linkIdsList.add(rs.getString("linkid"));
					submitMsgIdsList.add(rs.getString("submitmsgid"));
					idsList.add(new Integer(rs.getInt("id")));
				}
			}catch(Exception e){
				e.printStackTrace();
			}

			System.out.println(company + " " + game);
			//System.out.println(cpnsList.size());
			UpdateStateAgain updateState = new UpdateStateAgain(company,game,linkIdsList,submitMsgIdsList,idsList,temptableName);
			updateState.mydb.dbclose();

		}
		
		//updateState(spcodes,cpns,addates,statList);
		System.out.println("check finished");
	}
	private void updateState(ArrayList spcodes,ArrayList cpns,ArrayList addates,ArrayList stats){
		//System.out.println(cpn + " " + addate + " stat:" + sendStat);
		String strSql = "";
		for(int i = 0;i < spcodes.size();i++){
			strSql = "update company_mt set sendstate='" + stats.get(i) + "' where spcode='"+ spcodes.get(i) + "' and cpn = '" + cpns.get(i) + "' and addate='" + addates.get(i) + "'";
			mydb.executeUpdate(strSql);
		}
		
	}
	private String newDateOperate(String addate){
		int explote = addate.indexOf(" ");
		String year = addate.substring(0,explote);
	
		return year;
	}
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public static void main(String[] args){
		InputStream is = System.in; 
		Scanner scan = new Scanner(is); 
		System.out.println("请输入要更新状态报告的日期(YYYYmmdd):");
		String tableName = scan.next(); 
		SendStateHandleAgain sha = new SendStateHandleAgain();
		sha.setTableName(tableName);
		sha.getUnUpdateMtState();
	}
}
