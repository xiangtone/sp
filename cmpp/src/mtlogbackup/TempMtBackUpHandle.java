package mtlogbackup;
import java.sql.*;
public class TempMtBackUpHandle {
	public static void main(String[] args){
		Mysqldb mydb = new Mysqldb();
		String strSql = "select * from sms_mtlog0408 where id > 850437";
		try{
			ResultSet rs = mydb.executeQuery(strSql);
			while(rs.next()){
				strSql = "insert into sms_mtlogbackup set ";
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
				mydb.executeUpdate(strSql);
			}
			mydb.dbclose();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
