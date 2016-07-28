package companytempmthandle;
import DBHandle.MysqldbT;
import java.util.ArrayList;
import java.sql.*;
public class TempMtHandle {
	private MysqldbT mydb;
	private ArrayList SqlMtsList;
	private ArrayList companyMtCountSql;
	private ArrayList deleteSqlMtsList;
	private ArrayList tempMtsList;
	private String tableName;
	private String checkTime = "";
	String dbIp = "xiangtone_dbip";
	String dbMoName = "mos";
	String dbMtName = "mts";
	String dbUser = "joyfulUser";
	String dbPwd = "joyfulPwd";
	String dbPort = "xiangtone_dbport";
	public TempMtHandle(){
		mydb = new MysqldbT(dbIp,dbMtName,dbUser,dbPwd,dbPort);
	}
	public void tempMtOperate(){
		try{
			this.checkTempMts();
			this.updateMt();
			this.deleteTempMt();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				this.mydb.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		
	}
	public void checkTempMts(){
		//company game content cpn msgid linkid submit_linkid submitmsgid addate sendstate 
		//String strSql = "select id,company,game,content,cpn,msgid,linkid,submit_linkid,submitmsgid,addate,sendstate from tempcompany_mt where addate <= '" + this.checkTime + "'";
		//sendstate
		String strSql = "select id,company,game,content,cpn,msgid,linkid,submit_linkid,submitmsgid,addate,sendstate,province,postcode from tempcompany_mt where sendstate !='-3' and province !='' ";
		System.out.println(strSql);
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			String tempSql = "";
			String deleteSql = "";
			String companyCountSql = "";
			SqlMtsList = new ArrayList();
			companyMtCountSql = new ArrayList();
			deleteSqlMtsList = new ArrayList();
			while(rs.next()){
				tempSql = "insert into " + this.tableName + " set company='" + rs.getString("company") + "'";
				tempSql += ",game='" + rs.getString("game") + "'";
				tempSql += ",content='" + rs.getString("content") + "'";
				tempSql += ",cpn='" + rs.getString("cpn")+ "'";
				tempSql += ",msgid='" + rs.getString("msgid") + "'";
				tempSql += ",linkid='" + rs.getString("linkid") + "'";
				tempSql += ",submit_linkid = '" + rs.getString("submit_linkid") + "'";
				tempSql += ",submitmsgid='" + rs.getString("submitmsgid")+"'";
				tempSql += ",addate='" + rs.getString("addate")+ "'";
				tempSql += ",sendstate='" + rs.getString("sendstate")+ "'";
				tempSql += ",province='" + rs.getString("province")+ "'";
				tempSql += ",postcode='" + rs.getString("postcode")+ "'";
				//this.mydb.executeUpdate(tempSql);
				////////////////////
				/*
				companyCountSql = "insert into companys_countmt set company='" + rs.getString("company") + "'";
				companyCountSql += ",game='" + rs.getString("game") + "'";
				companyCountSql += ",content='" + rs.getString("content") + "'";
				companyCountSql += ",cpn='" + rs.getString("cpn")+ "'";
				companyCountSql += ",msgid='" + rs.getString("msgid") + "'";
				companyCountSql += ",linkid='" + rs.getString("linkid") + "'";
				companyCountSql += ",submit_linkid = '" + rs.getString("submit_linkid") + "'";
				companyCountSql += ",submitmsgid='" + rs.getString("submitmsgid")+"'";
				companyCountSql += ",addate='" + rs.getString("addate")+ "'";
				companyCountSql += ",sendstate='" + rs.getString("sendstate")+ "'";
				companyCountSql += ",province='" + rs.getString("province")+ "'";
				*/
				SqlMtsList.add(tempSql);
				//companyMtCountSql.add(companyCountSql);
				deleteSql = "delete from tempcompany_mt where id='" + rs.getInt("id") + "'";
				deleteSqlMtsList.add(deleteSql);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void updateMt(){
		System.out.println("insert size is:" + SqlMtsList.size());
		String tempSql = "";
		if(SqlMtsList.size() > 0){
			for(int i = 0;i < SqlMtsList.size();i++){
				tempSql = (String)SqlMtsList.get(i);
				this.mydb.executeInsert(tempSql);
				//this.mydb.batchUpdate(SqlMtsList);
				
			}
			/*
			for(int i = 0;i < companyMtCountSql.size();i++){
				tempSql = (String)companyMtCountSql.get(i);
				this.mydb.executeInsert(tempSql);
				//this.mydb.batchUpdate(SqlMtsList);
				
			}
			*/
			System.out.println("insert finished");
		}
		else{
			System.out.println("no data");
		}
		
		
	}
	public void deleteTempMt(){
		System.out.println("delete size is:" + deleteSqlMtsList.size());
		//this.mydb.batchUpdate(deleteSqlMtsList);
		String strSql = "";
		if(deleteSqlMtsList.size() > 0){
			for(int i = 0;i < deleteSqlMtsList.size();i++){
				strSql = (String)deleteSqlMtsList.get(i);
				//System.out.println(strSql);
				this.mydb.executeDelete(strSql);
			}
			System.out.println("delete finished");
		}
		else{
			System.out.println("delete not data");	
		}
		
		
	}
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public void setCheckTime(String checkTime){
		this.checkTime = checkTime;
	}
	public void setTempMtsList(ArrayList tempMtsList){
		this.tempMtsList = tempMtsList;
	}
	public ArrayList getTempMtsList(){
		return this.tempMtsList;
	}
}
