package newsmsendstatecheck;
import java.sql.*;
import java.util.*;
import companycount.FormatSysTime;
public class UpdateStateAgain {
	Mysqldb mydb;
	String company = "";
	String game = "";
	private String tableName = "";
	ArrayList linksList = new ArrayList();
	ArrayList submitMsgIds = new ArrayList();
	ArrayList idsList = new ArrayList();
	public UpdateStateAgain(String company,String game,ArrayList links,ArrayList submitMsgIds,ArrayList idsList,String tableName){
		this.company = company;
		this.game = game;
		this.linksList = links;
		this.submitMsgIds = submitMsgIds;
		this.idsList = idsList;
		this.tableName = tableName;
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
		try{
			String linkid = "";
			String submitMsgId = "";
			ArrayList linkStatsList = new ArrayList();
			ArrayList templinksList = new ArrayList();
			ArrayList tempSubmitMsgIds = new ArrayList();
			ArrayList tempIdsList = new ArrayList();
			String strSql = "";
			int id = 0;
			for(int i = 0;i < linksSize;i++){
				linkid = (String)linksList.get(i);
				submitMsgId = (String)this.submitMsgIds.get(i);
				id = ((Integer)this.idsList.get(i)).intValue();
				if(!submitMsgId.equals("") && submitMsgId != null){
					strSql = "select stat_msg from sms_reportlog where msg_id='" + submitMsgId + "'";
				}
				else{
					strSql = "select stat_msg from sms_reportlog where linkid='" + linkid + "'";
				}
				//String strSql = "select stat_msg from sms_reportlog where linkid='" + linkid + "'";
				System.out.println(strSql);

				ResultSet rs = mydb.executeQuery(strSql);
				if(rs.next()){
					statMsg = rs.getString("stat_msg");
					linkStatsList.add(statMsg);
					templinksList.add(linkid);
					tempSubmitMsgIds.add(submitMsgId);
					tempIdsList.add(new Integer(id));
				}

			}
			System.out.println("the table name is:" + this.tableName);
			if(templinksList.size() > 0){
				//updateMt(linkStatsList,templinksList,tempSubmitMsgIds,tempIdsList);
			}
			//mydb.dbclose();
				//mydb.dbclose();
		}catch(Exception e){
			mydb.dbclose();
			e.printStackTrace();
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
	private  void updateMt(ArrayList statMsgList,ArrayList linkidList,ArrayList submitMsgIdsList,ArrayList idsList){
		String statMsg = "";
		String linkId = "";
		String strSql = "";
		int id = 0;
		String tableName =  this.tableName;
		for(int i = 0;i < linkidList.size();i++){
				statMsg = (String)statMsgList.get(i);
				linkId = (String)linkidList.get(i);
				id = ((Integer)idsList.get(i)).intValue();
				//strSql = "update " + tableName + " set sendstate='" + statMsg + "' where linkid='" + linkId + "'";
				strSql = "update " + tableName + " set sendstate='" + statMsg + "' where id='" + id + "'";
				mydb.executeUpdate(strSql);
		}
		System.out.println("start to del linkid which update finished");
		delete(linkidList,submitMsgIdsList);
	}
	private void delete(ArrayList delLinkIdList,ArrayList submitMsgIdsList){//删除状态表中的已更新过的状态
		String linkId = "";
		String msgId = "";
		String strSql = "";
			for(int i = 0;i < delLinkIdList.size();i++){
				linkId = (String)delLinkIdList.get(i);
				msgId = (String)submitMsgIdsList.get(i);
				if(msgId.equals("") || msgId == null){
					strSql = "delete from sms_reportlog where linkid='" + linkId + "'";
				}
				else{
					strSql = "delete from sms_reportlog where msg_id='" + msgId + "'";	
				}
				mydb.executeUpdate(strSql);
			}
	}
	public void setTableName(String tableName){
		this.tableName = tableName;	
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

