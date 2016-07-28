package mtlogbackup;
import java.util.*;
import java.sql.*;
public class MtLogBackUpTempHandle {
	Mysqldb mydb;
	public MtLogBackUpTempHandle(){
		
	}
	public void mtBackUpOperate(){
		//String beforeDay = FormatSysTime.getBeforeDay(1);
		String beforeDay = FormatSysTime.getCurrentTimeA();
		long starTime = System.currentTimeMillis();
		String strSql = "select * from sms_mtlogbackup201002 where sendtime >='2010-03-01'";
		System.out.println(strSql);
		try{
			this.mydb = new Mysqldb();
			ResultSet rs = mydb.executeQuery(strSql);
			boolean deleteFlag = false;
			System.out.println("start to operate......");
			while(rs.next()){
				//System.out.println(rs.getInt("id"));
				
				strSql = "insert into sms_mtlogbackup  set ";
				strSql +="id='" + rs.getInt("id") + "',";
				strSql +="vcpid='" + rs.getInt("vcpid") + "',";
				strSql +="ismgid='" + rs.getString("ismgid") + "',";
				strSql +="comp_msgid='"+rs.getString("comp_msgid") + "',";
				strSql +="corpid='" + rs.getString("corpid") + "',";
				strSql +="spcode='"+ rs.getString("spcode")+ "',";
				strSql +="destcpn='" + rs.getString("destcpn") + "',";
				strSql +="feecpn='" + rs.getString("feecpn") + "',";
				strSql +="serverid='" + rs.getString("serverid") + "',";
				strSql +="servername='" + rs.getString("servername")+ "',";
				strSql +="infofee ='" + rs.getInt("infofee") + "',";
				strSql +="feetype='" + rs.getString("feetype") + "',";
				strSql +="feecode='" + rs.getString("feecode") + "',";
				strSql +="content ='" + rs.getString("content")+ "',";
				strSql +="linkid ='" + rs.getString("linkid") + "',";
				strSql +="mediatype ='" + rs.getInt("mediatype")+"',";
				strSql +="sendtime ='" + rs.getString("sendtime")+ "',";
				strSql +="submit_seq='" + rs.getLong("submit_seq") + "',";
				strSql +="submit_msgid='" + rs.getString("submit_msgid") + "',";
				strSql +="submit_result='" + rs.getInt("submit_result") + "',";
				strSql +="province='" + rs.getString("province") + "',";
				strSql +="reptstat='" + rs.getString("reptstat") + "'";
				System.out.println(strSql);
				mydb.executeUpdate(strSql);
				deleteFlag = true;
				
			}
			System.out.println("end  operate......");
			/*
			if(deleteFlag){
				strSql = "delete from sms_mtlog where sendtime < '" + beforeDay + "'";
				System.out.println(strSql);
				mydb.executeUpdate(strSql);
			}	
			*/
			mydb.dbclose();
			long endTime = System.currentTimeMillis();
			
			System.out.println("back up finished.use time is:" + (endTime - starTime)/1000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		MtLogBackUpTempHandle mtLogBackUpTempHandle = new MtLogBackUpTempHandle();
		mtLogBackUpTempHandle.mtBackUpOperate();
		System.out.println(FormatSysTime.getBeforeDay(3));
	}
}
