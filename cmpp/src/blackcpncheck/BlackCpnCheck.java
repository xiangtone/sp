package blackcpncheck;
import java.sql.*;

import com.xiangtone.sql.Mysqldb;
public class BlackCpnCheck {
	Mysqldb mydb;
	public BlackCpnCheck(){
		mydb = new Mysqldb();
	}
	public boolean checkCpn(String cpn){
		boolean checkFlag = false;
		String strSql = "select cpn from blackcpns where cpn='" + cpn + "'";
		try{
			ResultSet rs = mydb.executeQuery(strSql);
			if(rs.next()){
				checkFlag = true;
			}
			mydb.close();
		}catch(Exception e){
			checkFlag = true;
			e.printStackTrace();
		}
		return checkFlag;
	}
}
