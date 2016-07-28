package smscount;
import java.sql.*;
import java.util.*;
public class SmsCountHandle {
	float monthTotal = 0;
	float onceTotal = 0;
	String spcode = "";
	Mysqldb mydb = new Mysqldb();
	public void getMonth(){
		String strSql = "select feecode from sms_month where spid='" + this.spcode + "' and begin_time < '2009-02-21 00:00:00' and state != 4";
		System.out.println(strSql);
		try{
			ResultSet rs = mydb.executeQuery(strSql);
			while(rs.next()){
				monthTotal = monthTotal + rs.getInt("feecode");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void getOnce(){
		String[] spcodes = {"106650081","106650082","1066500800","1066500815555","1066500815"};
		String strSql = "";

		for(int i = 0;i < spcodes.length;i++){
			strSql = "select count(*) as count from sms_reportlog where spcode='" + spcodes[i] + "' and stat = 0 and done_time >='2009-02-01 00:00:00' and done_time <='2009-02-28 23:59:59'";
			System.out.println(strSql);
			try{
				ResultSet rs = mydb.executeQuery(strSql);
				System.out.print("the " + spcodes[i] + " count is:");
				if(rs.next()){
					System.out.println(rs.getInt("count"));
					onceTotal = onceTotal + rs.getInt("count");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		/*
		String strSql = "select serverid,infofee from sms_cost where spid='" + this.spcode + "' and feetype='02'";
		ArrayList serverNamesList = new ArrayList();
		ArrayList infofeesList = new ArrayList();
		try{
			ResultSet rs = mydb.executeQuery(strSql);
			while(rs.next()){
				serverNamesList.add(rs.getInt("serverid"));
				infofeesList.add(rs.getInt("infofee"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		for(int i = 0;i < serverNamesList.size();i++){
			onceTotal = onceTotal + getGamesTotal((Integer)infofeesList.get(i),(String)serverNamesList.get(i));
		}
		*/
	}
	public float getGamesTotal(int fee,String gameName){
		ArrayList gameCpnsList = new ArrayList();
		float gameTotal = 0;
		String strSql = "select cpn from sms_mtlog where servername = '" + gameName + "' group by cpn";
		try{
			ResultSet rs = mydb.executeQuery(strSql);
			while(rs.next()){
				gameCpnsList.add(rs.getString("cpn"));
			}
			for(int i = 0;i < gameCpnsList.size();i++){
				strSql = "select count(*) as count from sms_reportlog where dest_cpn = '" + (String)gameCpnsList.get(i)+ "'";
				rs = mydb.executeQuery(strSql);
				if(rs.next()){

				}

			}
					}catch(Exception e){
			e.printStackTrace();
		}
		return gameTotal;
	}
	public static void main(String[] args){
		SmsCountHandle sc = new SmsCountHandle();
		sc.spcode = args[0];
		sc.getMonth();
		sc.getOnce();
		System.out.println("month value is:" + sc.monthTotal);
		System.out.println("once value is:" + sc.onceTotal);
		System.out.println("total is:" + (sc.monthTotal/100 + sc.onceTotal));
		System.out.println("depart total is:" + ((sc.monthTotal/100 + sc.onceTotal)*0.85));
	}
}
