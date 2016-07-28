package provincesdepart;
import java.sql.*;
import java.util.*;
public class ProvincesDateHandle {
	public Mysqldb promydb;
	public ProvincesDateHandle(){
		promydb = new Mysqldb();
	}
	public void provinceHandle(ArrayList cpnsList){
		String cpn = "";
		ArrayList provsList = new ArrayList();
		for(int i = 0;i < cpnsList.size();i++){
				cpn = (String)cpnsList.get(i);
				String tempCpn = cpn.substring(0,7);
				//String strSql = "select province,provid from cpnprovinces where phone_from <= '" + tempCpn + "' and phone_to >='" + tempCpn + "'";
				String strSql = "select province,miscid from mobile_miscid_cmcc  where mobile = '" + tempCpn + "'";
				System.out.println(strSql);
				try{
					ResultSet rs = promydb.executeQuery(strSql);
					String provId = "0000";
					if(rs.next()){
						provId = rs.getString("miscid");
						
					}
					provsList.add(provId);
					
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		updateProvId(provsList,cpnsList);
		
	}
	public void updateProvId(ArrayList provIdsList,ArrayList cpnsList){
		String provId = "";
		String cpn = "";
		for(int i = 0;i < provIdsList.size();i++){
			provId = (String)provIdsList.get(i);
			cpn = (String)cpnsList.get(i);
			String strSql = "update sms_mtlogbackup set province='" + provId + "' where destcpn = '" + cpn + "'";
			System.out.println(strSql);
			this.promydb.executeUpdate(strSql);
		}
		
	}
	public void provinceDateHandle(String cpn){
		
		String tempCpn = cpn.substring(0,7);
		//String strSql = "select province,provid from cpnprovinces where phone_from <= '" + tempCpn + "' and phone_to >='" + tempCpn + "'";
		String strSql = "select province,miscid from mobile_miscid_cmcc  where mobile = '" + tempCpn + "'";
		//System.out.println(strSql);
		try{
			ResultSet rs = promydb.executeQuery(strSql);
			String provId = "0000";
			if(rs.next()){
				provId = rs.getString("miscid");
			}

			updateDateProvId(provId,cpn);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void updateDateProvId(String provId,String cpn){
		String strSql = "update sms_mtlogbackup set province='" + provId + "' where destcpn = '" + cpn + "'";
		//System.out.println(strSql);
		this.promydb.executeUpdate(strSql);
	}
}
