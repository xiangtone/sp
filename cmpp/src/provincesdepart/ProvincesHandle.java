package provincesdepart;
import java.sql.*;
public class ProvincesHandle {
	public Mysqldb promydb;
	public ProvincesHandle(){
		promydb = new Mysqldb();
	}
	public void provinceHandle(int cpnId,String cpn){
		
		String tempCpn = cpn.substring(0,7);
		//String strSql = "select province,provid from cpnprovinces where phone_from <= '" + tempCpn + "' and phone_to >='" + tempCpn + "'";
		String strSql = "select province,miscid from mobile_miscid_cmcc  where mobile = '" + tempCpn + "'";
		System.out.println(strSql);
		try{
			ResultSet rs = promydb.executeQuery(strSql);
			String provId = "0000";
			if(rs.next()){
				System.out.println(rs.getString("province"));
				//System.out.println(rs.getString("provid"));
				provId = rs.getString("miscid");
			}

			updateProvId(cpnId,provId,cpn);
			//this.promydb.dbclose();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void updateProvId(int cpnId,String provId,String cpn){
		String strSql = "update sms_mtlog set province='" + provId + "' where destcpn = '" + cpn + "'";
		System.out.println(strSql);
		this.promydb.executeUpdate(strSql);
	}
}
