package tempreportlog;
import java.sql.*;

public class ReportHandle {
	Mysqldb mydb;
	public ReportHandle(){
		mydb = new Mysqldb();
	}
	public void logReport(String strSql){
		try{
					mydb.executeUpdate(strSql);
					mydb.dbclose();
		}catch(Exception e){
			System.out.println(e.toString());
		}

	}
}
