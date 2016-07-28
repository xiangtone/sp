package newsmsendstatecheck;
import companymtdb.*;
import java.sql.*;
import java.util.ArrayList;
public class CompanyMtHandle{
	//updateMt(linkStatsList,templinksList,tempSubmitMsgIds,tempIdsList,repMsgIdsList);
	private ArrayList statMsgList = new ArrayList();
	private ArrayList linkidList= new ArrayList();
	private ArrayList submitMsgIdsList= new ArrayList();
	private ArrayList idsList= new ArrayList();
	private ArrayList repMsgIdsList= new ArrayList();
	
	public MysqldbT mydb;
	String dbIp = "xiangtone_dbip";
		String dbName = "mts";
		String dbUser = "joyfulUser";
		String dbPwd = "joyfulPwd";
		String dbPort = "xiangtone_dbport";
	public CompanyMtHandle(){
				this.mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
	}
	public void updateCompanyRpt(){
		////////////////
		String statMsg = "";
		String linkId = "";
		String strSql = "";
		String submitMsgId = "";
		int id = 0;
		//String tableName = FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
		String tableName =  "tempcompany_mt";
		ArrayList sqlsList = new ArrayList();
		try{
			//System.out.println("the linkidList size:" + linkidList.size());
			//System.out.println("the linkidList size:" + statMsgList.size());
			//System.out.println("the linkidList size:" + submitMsgIdsList.size());
			//System.out.println("the linkidList size:" + idsList.size());
			for(int i = 0;i < this.linkidList.size();i++){
				statMsg = (String)this.statMsgList.get(i);
				linkId = (String)this.linkidList.get(i);
				submitMsgId = (String)this.submitMsgIdsList.get(i);
				id = ((Integer)idsList.get(i)).intValue();
				//strSql = "update " + tableName + " set sendstate='" + statMsg + "' where linkid='" + linkId + "'";
				if(!submitMsgId.equals("") && submitMsgId != null){
					strSql = "update " + tableName + " set sendstate='" + statMsg + "' where submitmsgid='" + submitMsgId + "'";
				}
				else{
					strSql = "update " + tableName + " set sendstate='" + statMsg + "' where linkid='" + linkId + "'";	
				}	
				//System.out.println(strSql);
				this.mydb.executeUpdate(strSql);
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
		finally{
			try{
				this.mydb.close();	
			}catch(Exception e){
				e.printStackTrace();	
			}
		}
	
		////////////////
	}
	public void setStatMsgList(ArrayList statMsgList){
		this.statMsgList = statMsgList;	
	}
	public void setLinksList(ArrayList linkidList){
		this.linkidList = linkidList;	
	}
	public void setSubmitMsgIds(ArrayList tempSubmitMsgIds){
		this.submitMsgIdsList = tempSubmitMsgIds;	
	}
	public void setIdsList(ArrayList tempIdsList){
		this.idsList = tempIdsList;	
	}
	public void setRepMsgIdsList(ArrayList repMsgIdsList){
		this.repMsgIdsList = repMsgIdsList;	
	}
}