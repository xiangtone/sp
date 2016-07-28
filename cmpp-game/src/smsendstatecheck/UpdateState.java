package smsendstatecheck;
import java.sql.*;
import java.util.*;
public class UpdateState {
	Mysqldb mydb;
	String company = "";
	String game = "";
	ArrayList cpns = new ArrayList();
	public UpdateState(String company,String game,ArrayList cpns){
		this.company = company;
		this.game = game;
		this.cpns = cpns;
		mydb = new Mysqldb();
		getGameSendStat();
	}
	private void getGameSendStat(){
		String checkDate = FormatSysTime.getCurrentTimeB();
		//System.out.println(checkDate);
		int cpnSize = cpns.size();
		int successNum = 0;
		int successCpns = 0;
		int count = 0;
		String spCode = getSpcode();
		try{
			String cpn = "";
			for(int i = 0;i < cpnSize;i++){
				cpn = (String)cpns.get(i);
				String strSql = "select count(stat) as count from sms_reportlog where spcode = '" + spCode + "5555' and dest_cpn='" + cpn + "' and submit_time >='" + checkDate +" 00:00:00' and submit_time <='" + checkDate +" 23:59:59' and stat='0'";
				System.out.println(strSql);

				ResultSet rs = mydb.executeQuery(strSql);
				if(rs.next()){
					count = rs.getInt("count");
					if(count > 0){
						successNum = successNum + count;
						successCpns = successCpns + 1;
					}
					
				}

			}
			if(successNum > 0){
				updateMtCount(successNum,checkDate);
				updateMtCpns(successCpns,checkDate);
			}
				//mydb.dbclose();
		}catch(Exception e){
			mydb.dbclose();
			e.printStackTrace();
		}

	}
	private String getSpcode(){
		String strSql = "select spcode from companygames where gamecode='" + this.game + "'";
		String spcode = "";
		try{
			ResultSet rs = mydb.executeQuery(strSql);
			if(rs.next()){
				spcode = rs.getString("spcode");
			}
		}catch(Exception e){
			mydb.dbclose();
			e.printStackTrace();
		}
		return spcode;
	}
	private void updateMtCount(int successNum,String checkDate){
		String strSql = "update company_mtcount set successstat = '" + successNum + "' where company='" + this.company + "' and game='" + this.game +"' and countdate='" + checkDate + "'";
		mydb.executeUpdate(strSql);
		//mydb.dbclose();
	}
	private void updateMtCpns(int successCpns,String checkDate){
		String strSql = "update company_mtcount set successcpns = '" + successCpns + "' where company='" + this.company + "' and game='" + this.game +"' and countdate='" + checkDate + "'";
		mydb.executeUpdate(strSql);

	}
}

