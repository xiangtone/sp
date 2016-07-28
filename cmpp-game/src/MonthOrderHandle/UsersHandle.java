package MonthOrderHandle;
/*
 * 用于取得订购包月的用户手机号码
 */
import java.sql.*;
import DBHandle.*;
import java.util.*;
public class UsersHandle {
	public MysqldbT mydb = null;
	private String feeCode = "";
	private String spId = "";
	public UsersHandle(){
		String dbIp = "xiangtone_dbip";
		String dbName = "smsmonthdb";
		String dbUser = "xiangtone_dbuser";
		String dbPwd = "xiangtone_dbpwd";
		String dbPort = "xiangtone_dbport";
		mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
	}
	//用于取得第一次订购的用户的信息
	public ArrayList getFirstOrderUsers(){
			String sendDate = FormatSysTime.getCurrentTimeB();
			String strSql = "select cpn from  companys_user where serviceid = '" + this.feeCode + "' and spid = '" + this.spId + "' and state = '1' and firstsend='0' and sendate <= '" + sendDate + "' or serviceid = '" + this.feeCode + "' and spid = '" + this.spId + "' and state = '3' and firstsend='0' and sendate <= '" + sendDate + "' order by id asc limit 100";
			System.out.println(strSql);
			ArrayList usersList = new ArrayList();
			try{
				ResultSet rs = this.mydb.executeQuery(strSql);
				while(rs.next()){
					usersList.add(rs.getString("cpn"));
				}
				//this.mydb.close();
			}catch(Exception e){
				try{
					this.mydb.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
				
				e.printStackTrace();
			}finally{
				try{
					//this.mydb.close();	
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			return usersList;
	}
	public ArrayList getOrderUsers(){
		String sendDate = FormatSysTime.getCurrentTimeB();
		String strSql = "select cpn from  companys_user where serviceid = '" + this.feeCode + "' and spid = '" + this.spId + "' and state = '1' and firstsend='1' and sendate <= '" + sendDate + "' or serviceid = '" + this.feeCode + "' and spid = '" + this.spId + "' and state = '3' and firstsend='1' and sendate <= '" + sendDate + "' limit 100";
		System.out.println(strSql);
		ArrayList usersList = new ArrayList();
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			while(rs.next()){
				usersList.add(rs.getString("cpn"));
			}
			this.mydb.close();
		}catch(Exception e){
			try{
				this.mydb.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}finally{
			try{
				this.mydb.close();	
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return usersList;
	}
	public void setFeeCode(String feeCode){
		this.feeCode = feeCode;
	}
	public void setSpId(String spId){
		this.spId = spId;
	}
}
