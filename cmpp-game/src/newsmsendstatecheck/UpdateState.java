package newsmsendstatecheck;
import java.sql.*;
import java.util.*;
import companycount.FormatSysTime;
import DBHandle.*;
public class UpdateState {
	Mysqldb mydb;
	String company = "";
	String game = "";
	ArrayList linksList = new ArrayList();
	ArrayList submitMsgIds = new ArrayList();
	ArrayList idsList = new ArrayList();
	//public UpdateState(String company,String game,ArrayList links,ArrayList submitMsgIds,ArrayList idsList){
	public UpdateState(String company,ArrayList links,ArrayList submitMsgIds,ArrayList idsList){	
		this.company = company;
		this.game = game;
		this.linksList = links;
		this.submitMsgIds = submitMsgIds;
		this.idsList = idsList;
		mydb = new Mysqldb();
		getGameSendStat();
	}
	private void getGameSendStat(){
		String checkDate = FormatSysTime.getCurrentTimeB();
		//System.out.println(checkDate);
		int linksSize = linksList.size();
		int successNum = 0;
		int successCpns = 0;
		String statMsg = "";
		String spCode = getSpcode();
		////////////////////////////////
		String dbIp = "xiangtone_dbip";
		String dbMoName = "mos";
		String dbName = "xiangtone_platdbname";
		String dbUser = "joyfulUser";
		String dbPwd = "joyfulPwd";
		String dbPort = "xiangtone_dbport";
		MysqldbT rptdb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
		////////////////////////////////
		try{
			String linkid = "";
			String submitMsgId = "";
			ArrayList linkStatsList = new ArrayList();
			ArrayList templinksList = new ArrayList();
			ArrayList tempSubmitMsgIds = new ArrayList();
			ArrayList tempIdsList = new ArrayList();
			ArrayList repMsgIdsList = new ArrayList();
			String strSql = "";
			int id = 0;
			for(int i = 0;i < linksSize;i++){
				linkid = (String)linksList.get(i);
				submitMsgId = (String)this.submitMsgIds.get(i);
				id = ((Integer)this.idsList.get(i)).intValue();
				if(!submitMsgId.equals("") && submitMsgId != null){
					strSql = "select id,stat_msg from companysms_reportlog where msg_id='" + submitMsgId + "'";
					//strSql = "select id,stat_msg from sms_reportlog where linkid='" + linkid + "'";
				}
				else{
					strSql = "select id,stat_msg from companysms_reportlog where linkid='" + linkid + "' order by id desc limit 1";
				}
				//String strSql = "select stat_msg from sms_reportlog where linkid='" + linkid + "'";
				//System.out.println(strSql);

				ResultSet rs = rptdb.executeQuery(strSql);
				if(rs.next()){
					//System.out.println(rs.getString("stat_msg"));
					statMsg = rs.getString("stat_msg");
					
					linkStatsList.add(statMsg);
					templinksList.add(linkid);
					tempSubmitMsgIds.add(submitMsgId);
					tempIdsList.add(new Integer(id));
					repMsgIdsList.add(new Integer(rs.getInt("id")));//状态报告的id序列号
				}
			}
			if(templinksList.size() > 0){
				//System.out.println("the tempSubmitMsgIds size is:" + tempSubmitMsgIds.size());
				CompanyMtHandle companyMtHandle = new CompanyMtHandle();
				companyMtHandle.setStatMsgList(linkStatsList);
				companyMtHandle.setLinksList(templinksList);
				companyMtHandle.setSubmitMsgIds(tempSubmitMsgIds);
				companyMtHandle.setIdsList(tempIdsList);
				companyMtHandle.setRepMsgIdsList(repMsgIdsList);
				companyMtHandle.updateCompanyRpt();
				deleteCompanyRpt(linkStatsList,templinksList,tempSubmitMsgIds,tempIdsList,repMsgIdsList);
				//delete(linkidList,submitMsgIdsList,repMsgIdsList);
			}
			//mydb.dbclose();
				//mydb.dbclose();
		}catch(Exception e){
			try{
				rptdb.close();
				e.printStackTrace();
			}catch(Exception e1){
				
			}
			
		}
		finally{
				try{
					rptdb.close();
				}catch(Exception e){
						
				}
		}

	}

	private String getSpcode(){
		String strSql = "select spcode from companygames where gamecode='" + this.game + "'";
		String spcode = "";
		try{
			ResultSet rs = mydb.executeQuery(strSql);
			if(rs.next()){
				spcode = rs.getString("spcode");
			}
		}catch(Exception e){
			mydb.dbclose();
			e.printStackTrace();
		}
		return spcode;
	}
	//private  void updateMt(ArrayList statMsgList,ArrayList linkidList,ArrayList submitMsgIdsList,ArrayList idsList,ArrayList repMsgIdsList){
		private  void deleteCompanyRpt(ArrayList statMsgList,ArrayList linkidList,ArrayList submitMsgIdsList,ArrayList idsList,ArrayList repMsgIdsList){
		/*
		String statMsg = "";
		String linkId = "";
		String strSql = "";
		String submitMsgId = "";
		int id = 0;
		String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
		for(int i = 0;i < linkidList.size();i++){
				statMsg = (String)statMsgList.get(i);
				linkId = (String)linkidList.get(i);
				submitMsgId = (String)submitMsgIdsList.get(i);
				id = ((Integer)idsList.get(i)).intValue();
				//strSql = "update " + tableName + " set sendstate='" + statMsg + "' where linkid='" + linkId + "'";
				if(!submitMsgId.equals("") && submitMsgId != null){
					strSql = "update " + tableName + " set sendstate='" + statMsg + "' where submitmsgid='" + submitMsgId + "'";
				}
				else{
					strSql = "update " + tableName + " set sendstate='" + statMsg + "' where linkid='" + linkId + "'";	
				}	
				//strSql = "update " + tableName + " set sendstate='" + statMsg + "' where id='" + id + "'";
				//System.out.println(strSql);
				mydb.executeUpdate(strSql);
		}
		*/
		System.out.println("start to del linkid which update finished");
		delete(linkidList,submitMsgIdsList,repMsgIdsList);
	}
	private void delete(ArrayList delLinkIdList,ArrayList submitMsgIdsList,ArrayList repMsgIdsList){//删除状态表中的已更新过的状态
		String dbIp = "xiangtone_dbip";
		String dbMoName = "mos";
		String dbName = "xiangtone_platdbname";
		String dbUser = "joyfulUser";
		String dbPwd = "joyfulPwd";
		String dbPort = "xiangtone_dbport";
		MysqldbT rptdb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
		try{
			/////////////////////
		
		
		/////////////////////
		String linkId = "";
		String msgId = "";
		String strSql = "";
		int repMsgId = 0;
			for(int i = 0;i < delLinkIdList.size();i++){
				linkId = (String)delLinkIdList.get(i);
				msgId = (String)submitMsgIdsList.get(i);
				repMsgId = ((Integer)repMsgIdsList.get(i)).intValue();
				if(msgId.equals("") || msgId == null){
					strSql = "delete from companysms_reportlog where linkid='" + linkId + "' and id = '" + repMsgId + "'";
					//strSql = "delete from sms_reportlog where id='" + repMsgId + "'";
				}
				else{
					strSql = "delete from companysms_reportlog where msg_id='" + msgId + "'";	
					//strSql = "delete from sms_reportlog where id='" + repMsgId + "'";	
				}
				//System.out.println(strSql);
				rptdb.executeUpdate(strSql);
			}
		}catch(Exception e){
			try{
				rptdb.close();
			}catch(Exception e2){
				
			}
		}
		finally{
			try{
				rptdb.close();
			}catch(Exception e1){
				
			}	
		}
		
	}
	private void updateMtCount(int successNum,String checkDate){
		String strSql = "update company_mtcount set successstat = '" + successNum + "' where company='" + this.company + "' and game='" + this.game +"' and countdate='" + checkDate + "'";
		mydb.executeUpdate(strSql);
		//mydb.dbclose();
	}
	private void updateMtCpns(int successCpns,String checkDate){
		String strSql = "update company_mtcount set successcpns = '" + successCpns + "' where company='" + this.company + "' and game='" + this.game +"' and countdate='" + checkDate + "'";
		mydb.executeUpdate(strSql);

	}
}

