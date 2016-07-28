package companysmosbackup;
import java.sql.*;
import java.util.*;
import DBHandle.MysqldbT;
public class MosHandle {
	public MysqldbT mydb;
	public MosHandle(){
		String dbIp = "xiangtone_dbip";
		String dbName = "mos";
		String dbUser = "xiangtone_dbuser";
		String dbPwd = "xiangtone_dbpwd";
		String dbPort = "xiangtone_dbport";
		mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
		//mydb = new MysqldbT();
	}
	
	public void backUpMos(){
		long starttime = System.currentTimeMillis();
		//获取已经发送给合作方的上行
		String selectTime = this.getSelectTime();
		String tableName = this.getInsertTable();
		String strSql = "select company,game,userinput,cpn,msgid,linkid,addate,tocompstat,comprecstat from tempcompany_mo where addate <='" + selectTime + "' and tocompstat='1'";
		ArrayList mosList = new ArrayList();
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			while(rs.next()){
				Mo mo = new Mo();
				mo.setCompany(rs.getString("company"));
				mo.setGame(rs.getString("game"));
				mo.setUserinput(rs.getString("userinput"));
				mo.setCpn(rs.getString("cpn"));
				mo.setMsgId(rs.getString("msgid"));
				mo.setLinkId(rs.getString("linkid"));
				mo.setAddTime(rs.getString("addate"));
				mo.setToCompStat(rs.getInt("tocompstat"));
				mo.setComprecStat(rs.getString("comprecstat"));
				mosList.add(mo);
			}
			insertMos(mosList,tableName);
			this.deleteMos(selectTime);
			long endTime = System.currentTimeMillis();
			System.out.println(endTime - starttime);
		}catch(Exception e){
			
		}
	}
	public void insertMos(ArrayList mosList,String tableName){
		//将已发送上行插入日期表中
		for(int i = 0;i < mosList.size();i++){
			Mo mo = (Mo)mosList.get(i);
			String strSql = "insert into " + tableName + 
							" set company='" + mo.getCompany() + 
							"',game='" + mo.getGame() + 
							"',userinput='" + mo.getUserinput() + 
							"',cpn='" + mo.getCpn() + 
							"',msgid='" + mo.getMsgId() + 
							"',linkid='" + mo.getLinkId() + 
							"',addate='" + mo.getAddTime() + 
							"',tocompstat='" + mo.getToCompStat() + 
							"',comprecstat='" + mo.getComprecStat() +
							"',province='" + mo.getProvince() + "'";
			this.mydb.executeUpdate(strSql);
							
		}
	}
	public void deleteMos(String deleteTime){
		//删除已发给合作方上行在临时表的记录
		String strSql = "delete from tempcompany_mo where addate <='" + deleteTime + "' and tocompstat='1'";
		System.out.println(strSql);
		this.mydb.executeDelete(strSql);
	}
	//public int getTime
	public String getSelectTime(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -30);
	    java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    return format.format(cal.getTime());
	}
	public String getInsertTable(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -30);
	    java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd"); 
	    return format.format(cal.getTime()) + "company_mo ";
	}
	public int getYear(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}	
}
