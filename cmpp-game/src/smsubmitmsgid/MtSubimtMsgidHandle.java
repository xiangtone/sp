package smsubmitmsgid;
import java.util.*;
import DBHandle.*;
import java.sql.*;
public class MtSubimtMsgidHandle{
	String dbIp = "xiangtone_dbip";
	String dbMoName = "mos";
	String dbName = "xiangtone_platdbname";
	String dbUser = "joyfulUser";
	String dbPwd = "joyfulPwd";
	String dbPort = "xiangtone_dbport";
	public MysqldbT mydb;
	ArrayList linkidsList;
	ArrayList submitMsgIdsList;
	public MtSubimtMsgidHandle(){
			mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
			submitMsgIdsList = new ArrayList();
	}
	public void checkSubmitMsgid(){
		String strSql = "";
		String linkId = "";
		String submitMsgId = "";
		try{
			for(int i = 0;i < this.linkidsList.size();i++){
				linkId = (String)this.linkidsList.get(i);
				
				strSql = "select submit_msgid from sms_mtlog where linkid = '" + linkId + "'";
				//System.out.println("submit strSql:" + strSql);
				try{
					ResultSet rs = this.mydb.executeQuery(strSql);
					//System.out.println("submit strSql:" + strSql);
					if(rs.next()){
						//System.out.println("submit strSql:" + strSql);
						submitMsgId = rs.getString("submit_msgid");
					}	
					else{
						strSql = "select submit_msgid from sms_mtlogbackup where linkid = '" + linkId + "'";
						rs = this.mydb.executeQuery(strSql);
						//System.out.println("submit strSql:" + strSql);
						if(rs.next()){
							//System.out.println("submit strSql:" + strSql);
							submitMsgId = rs.getString("submit_msgid");
						}
						else{
							submitMsgId = "";
						}
						//submitMsgId = "";
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				this.submitMsgIdsList.add(submitMsgId);
			}	
		}catch(Exception e1){
			e1.printStackTrace();
		}finally{
			try{
				this.mydb.close();	
			}catch(Exception e2){
				
			}
			
		}
		
	}
	public void setLinkIdsList(ArrayList linkidsList){
		this.linkidsList = 	linkidsList;
	}
	public ArrayList getSubmitMsgIdsList(){
		return this.submitMsgIdsList;
	}
	
}