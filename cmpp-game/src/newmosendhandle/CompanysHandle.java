package newmosendhandle;
/*
 * 该类用于取得所有合作方的公司简称
 * 其作用是用来取得配置文件config.ini中相应的接收上行的url
 */
import java.util.ArrayList;
import java.sql.*;
public class CompanysHandle {
	Mysqldb mydb = null;
	public CompanysHandle(){
		
	}
	/*
	 * 用于取得companys表中的所有用户
	 */
	public ArrayList listsCompanys(){
		mydb = new Mysqldb();
		String strSql = "select company from companys group by company";
		ArrayList companys = new ArrayList();
		try{
			ResultSet rs = mydb.executeQuery(strSql);
			while(rs.next()){
				companys.add(rs.getString("company"));
			}
		}catch(Exception e){
			mydb.dbclose();
			e.printStackTrace();
		}
		finally{
			try{
					mydb.dbclose();
			}catch(Exception e1){
				e1.printStackTrace();
			}	
		}
		
		return companys;
	}
}
