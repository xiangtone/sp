package smsreportsend;
import java.util.ArrayList;
import java.sql.*;
public class SmsCompanys {
	public Mysqldb mydb;
	public SmsCompanys(){
		this.mydb = new Mysqldb();
	}
	public ArrayList getCompanys(){
		String strSql = "select company from companys";
		ArrayList companysList = new ArrayList();
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			while(rs.next()){
				companysList.add(rs.getString("company"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return companysList;
	}
}
