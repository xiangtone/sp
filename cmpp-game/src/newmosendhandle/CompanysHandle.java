package newmosendhandle;
/*
 * ��������ȡ�����к������Ĺ�˾���
 * ������������ȡ�������ļ�config.ini����Ӧ�Ľ������е�url
 */
import java.util.ArrayList;
import java.sql.*;
public class CompanysHandle {
	Mysqldb mydb = null;
	public CompanysHandle(){
		
	}
	/*
	 * ����ȡ��companys���е������û�
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
