package smsmoscount;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import DBHandle.MysqldbT;
import java.util.Scanner;
public class CompanysMosDateCountHandle implements Runnable{
	ArrayList companysList;
	ArrayList gamesList;
	MysqldbT mydb;
	int countDate = 0;
	public void run(){
		String dbIp = "xiangtone_dbip";
		String dbName = "companymos";
		String dbUser = "xiangtone_dbuser";
		String dbPwd = "xiangtone_dbpwd";
		String dbPort = "xiangtone_dbport";
		//while(true){
			try{
				System.out.println("start count");
				mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
				this.companysGamesList();
				this.mydb.close();
				System.out.println("count finished");
				//Thread.currentThread().sleep(1000 * 60 * 5);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		//}
	}
	public void companysGamesList(){
		companysList = new ArrayList();
		gamesList = new ArrayList();
		String tableName = this.getGamesCompanysCountDate(0) + "company_mo";
		String countDate = this.getGamesCompanysCountDate(1);
		String strSql = "select company,game from " + tableName + " group by company,game";
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			String companyName = "";
			while(rs.next()){
				companyName = rs.getString("company");
				if(!companyName.equals("")){
					companysList.add(companyName);
					gamesList.add(rs.getString("game"));
				}
				
			}
			countCompanysMos(tableName,countDate);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void countCompanysMos(String tableName,String countDate){
		int companysNum = this.companysList.size();
		String company = "";
		String game = "";
		int gameMos = 0;
		int cpns = 0;
		for(int i = 0;i < companysNum;i++){
			company = (String)this.companysList.get(i);
			game = (String)this.gamesList.get(i);
			gameMos = countMosNum(company,game,tableName);
			cpns = countCpnsNum(company,game,tableName);
			if(checkCountDate(company,game,countDate)){
				this.updateMosCount(company,game,countDate,gameMos,cpns);
			}
			else{
				this.insertNewMosCount(company, game, countDate, gameMos,cpns);
			}
		}
	}
	private void updateMosCount(String company,String game,String countDate,int gameMos,int cpns){
		String strSql = "update companys_moscount set mosnum='" + gameMos + "',cpns='" + cpns + "' where company='" + company + "' and game='" + game + "' and countdate='" + countDate +"'";
		try{
			System.out.println(strSql);
			this.mydb.executeUpdate(strSql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void insertNewMosCount(String company,String game,String countDate,int gameMos,int cpns){
		String strSql = "insert into companys_moscount set ";
		strSql += " company='" + company + "'";
		strSql += ",game='" + game + "'";
		strSql += ",mosnum='" + gameMos + "'";
		strSql += ",cpns='" + cpns + "'";
		strSql += ",countdate='" + countDate + "'";
		System.out.println(strSql);
		this.mydb.executeInsert(strSql);
	}
	private int countCpnsNum(String company,String game,String tableName){
		String strSql = "select count(DISTINCT cpn) as count from " + tableName + " where company='" + company + "' and game='" + game + "'";
		int cpnsNum = 0;
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			if(rs.next()){
				cpnsNum = rs.getInt("count");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return cpnsNum;
	}
	private int countMosNum(String company,String game,String tableName){
		String strSql = "select count(*) as count from " + tableName + " where company='" + company + "' and game='" + game + "'";
		int gameMosNum = 0;
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			if(rs.next()){
				gameMosNum = rs.getInt("count");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return gameMosNum;
	}
	//用于判断是否有统计过，有更新统计数据，没有添加统计数据
	private boolean checkCountDate(String company,String game,String countDate){
		String strSql = "select id from companys_moscount where company='" + company + "' and game='" + game + "' and countdate='" + countDate + "'";
		boolean existFlag = false;
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			if(rs.next()){
				existFlag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return existFlag;
	}
	public String getGamesCompanysCountDate(int type){
		String strDate = "";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH,-this.countDate);
		if(type == 0){
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd"); 
		    strDate = format.format(cal.getTime());
		}
		else if(type == 1){
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		    strDate = format.format(cal.getTime());
		}
	    return strDate;
	}
	
	public static void main(String[] args){
		CompanysMosDateCountHandle compMosCountHandle = new CompanysMosDateCountHandle();
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入要统计合作方上行的日期。如7为七天前的数据");
		compMosCountHandle.countDate = scanner.nextInt();
		new Thread(compMosCountHandle).start();
	}
}
