package newsmsendstatecheck;
import java.sql.*;
import java.util.*;
import companycount.FormatSysTime;

public class SendStateHandle {
	public Mysqldb mydb;
	static boolean checkFlag = false;
	public SendStateHandle(){
		
	}
	public void getUnUpdateMtState(){
		//用于取得那些发送状态还没更新的下行

		mydb = new Mysqldb();
		//String strSql = "select company,game from companys ";
		String strSql = "select company from companys group by company";
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
				//gamesList.add(rs.getString("game"));
				//spcodeArrayList.add(rs.getString("spcode"));
				//updateSendState(spcode,cpn,addate);
			}
			//updateSendState(companysList,gamesList);
			updateSendState(companysList);
			checkFlag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//private void updateSendState(ArrayList companys,ArrayList games){
	private void updateSendState(ArrayList companys){
		//System.out.println(addate);
		String checkDate = FormatSysTime.getCurrentTimeB();
		
		int size = companys.size();
		String company = "";
		String game = "";
		String spcode = "";

		//for(int i = 0;i < size;i++){
			//company = (String)companys.get(i);
			//game = (String)games.get(i);
			ArrayList linkIdsList = new ArrayList();
			ArrayList submitMsgIdsList = new ArrayList();
			ArrayList idsList = new ArrayList();
			//String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
			String tableName =  "tempcompany_mt";
			//String strSql = "select linkid from " + tableName + " where company='" + company + "' and addate >='" + checkDate +" 00:00:00' and addate <='" + checkDate +" 23:59:59'";
			//String strSql = "select distinct(linkid) from " + tableName + " where company='" + company + "' and sendstate = -3 order by id desc limit 100";
				String strSql = "select id,linkid,submitmsgid from " + tableName + " where sendstate = -3";

			//System.out.println(strSql);
			try{
				ResultSet rs = mydb.executeQuery(strSql);
				while(rs.next()){
					//System.out.println(rs.getString("linkid"));
					linkIdsList.add(rs.getString("linkid"));
					submitMsgIdsList.add(rs.getString("submitmsgid"));
					idsList.add(new Integer(rs.getInt("id")));
				}
			}catch(Exception e){
				e.printStackTrace();
			}

			System.out.println(company + " " + game);
			//System.out.println(cpnsList.size());
			//UpdateState updateState = new UpdateState(company,game,linkIdsList,submitMsgIdsList,idsList);
			UpdateState updateState = new UpdateState(company,linkIdsList,submitMsgIdsList,idsList);
			updateState.mydb.dbclose();

		//}
		
		//updateState(spcodes,cpns,addates,statList);
		System.out.println("check finished");
	}
	private void updateState(ArrayList spcodes,ArrayList cpns,ArrayList addates,ArrayList stats){
		//System.out.println(cpn + " " + addate + " stat:" + sendStat);
		String strSql = "";
		for(int i = 0;i < spcodes.size();i++){
			strSql = "update company_mt set sendstate='" + stats.get(i) + "' where spcode='"+ spcodes.get(i) + "' and cpn = '" + cpns.get(i) + "' and addate='" + addates.get(i) + "'";
			//System.out.println(strSql);
			mydb.executeUpdate(strSql);
		}
		
	}
	private String newDateOperate(String addate){
		int explote = addate.indexOf(" ");
		String year = addate.substring(0,explote);
		/*
		System.out.println(year);
		int yearFirstIndex = year.indexOf("-");
		int yearLastIndex = year.lastIndexOf("-");
		int intYear = Integer.parseInt(year.substring(0,yearFirstIndex));
		int intMonth = Integer.parseInt(year.substring(yearFirstIndex+1,yearLastIndex));
		int intDate = Integer.parseInt(year.substring(yearLastIndex+1));
		String time = addate.substring(explote+1);
		int firstIndex = time.indexOf(":");
		int lastIndex = time.lastIndexOf(":");
		int hour = Integer.parseInt(time.substring(0,firstIndex));
		int min = Integer.parseInt(time.substring(firstIndex+1,lastIndex));

		java.util.Date aDate = new java.util.Date(intYear,intMonth,intDate,hour,(min-4));
		int newYear = aDate.getYear();
		int newMonth = aDate.getMonth();
		int newDay = aDate.getDay();
		int newHour = aDate.getHours();
		int newMin = aDate.getMinutes();

		String newDate = "";
		newDate += newYear;
		if(newMonth < 10){
			newDate += "-0" + newMonth;
		}
		else{
			newDate += "-" + newMonth;
		}
		if(newDay < 10){
			newDate += "-0" + newDay;
		}
		else{
			newDate += "-" + newDay;
		}
		if(newHour < 10){
			newDate += " 0" + newHour;
		}
		else{
			newDate += " " + newHour;
		}
		if(newMin < 10){
			newDate += ":0" + newMin;
		}
		else{
			newDate += ":" + newMin;
		}
		newDate += ":00";
		return newDate;
		*/
		return year;
	}
	public static void main(String[] args){
		SendStateHandle sh = new SendStateHandle();
		sh.getUnUpdateMtState();
	}
}
