package smsubmitmsgid;
import java.sql.*;
import companycount.FormatSysTime;
import java.util.*;
import DBHandle.*;
public class SmsSubmitMsgidHandle{
	String dbIp = "xiangtone_dbip";
	String dbMoName = "mos";
	String dbMtName = "mts";
	String dbUser = "joyfulUser";
	String dbPwd = "joyfulPwd";
	String dbPort = "xiangtone_dbport";
	
	public MysqldbT mydb;
	ArrayList mtLinkIdsList;
	ArrayList mtIdsList;
	public void UpdateSubmitMsgId(){
		Long startTime = System.currentTimeMillis();
		this.mtLinkIdsList = new ArrayList();
		this.mtIdsList = new ArrayList();
		mydb = new MysqldbT(dbIp,dbMtName,dbUser,dbPwd,dbPort);
		try{
			ArrayList mtLinkIdList = new ArrayList();
			//mtLinkIdList = getMtLinkId();
			this.checkMtLinkId();
			MtSubimtMsgidHandle mtSubmitMsgIdHandle = new MtSubimtMsgidHandle();
			mtSubmitMsgIdHandle.setLinkIdsList(this.mtLinkIdsList);
			mtSubmitMsgIdHandle.checkSubmitMsgid();
			ArrayList mtSubMsgIdList  = mtSubmitMsgIdHandle.getSubmitMsgIdsList();
			updateSubmitMsgId(this.mtLinkIdsList,mtSubMsgIdList);
			Long endTime = System.currentTimeMillis();
			System.out.println("use time is:" + (endTime - startTime)/1000);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				this.mydb.close();
			}catch(Exception e1){
				
			}
			
		}
			
	}
	private void checkMtLinkId(){
		//String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
		String tableName =  "tempcompany_mt";
		String strSql = "select id,linkid from " + tableName + " where sendstate = '-3' and submitmsgid=''  order by id desc limit 5000";
		System.out.println("select :" + strSql);
		//ArrayList linkIdList = new ArrayList();
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			while(rs.next()){
				this.mtIdsList.add(new Integer(rs.getInt("id")));
				this.mtLinkIdsList.add(rs.getString("linkid"));
			}	
		}catch(Exception e){
		e.printStackTrace();
		}
		System.out.println("the size is:" + this.mtIdsList.size());
		//return linkIdList;	
	}
	private void updateSubmitMsgId(ArrayList linkidList,ArrayList subMsgIdList){
			//String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
			String tableName =  "tempcompany_mt";
			int linkidNum = linkidList.size();
			String strSql = "";
			String submitMsgId = "";
			String liniId = "";
			int id = 0;
			for(int i = 0;i < linkidList.size();i++){
				submitMsgId = (String)subMsgIdList.get(i);
				liniId = (String)linkidList.get(i);
				id = ((Integer)this.mtIdsList.get(i)).intValue();
				//System.out.println("submitMsgId is:::::::::" + submitMsgId);
					if(submitMsgId.length() > 10){
						strSql = "update " + tableName + " set submitmsgid='" + submitMsgId + "',submit_linkid='0' where linkid='" + liniId + "' and id = '" + id + "'";
						//System.out.println(strSql);
						this.mydb.executeUpdate(strSql);
					}
					
			}
	}
}
