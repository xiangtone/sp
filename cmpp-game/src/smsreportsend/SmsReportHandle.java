package smsreportsend;
import java.sql.*;
import java.util.ArrayList;
public class SmsReportHandle {
	private String company = "";
	Mysqldb mydb;
	ArrayList linkIdList = new ArrayList();
	ArrayList msgIdList = new ArrayList();
	ArrayList gamesList = new ArrayList();
	ArrayList statReportList = new ArrayList();
	public SmsReportHandle(String company){
		this.company = company;
		this.mydb = new Mysqldb();
	}
	public void reportHandle(){
		String strSql = "select game,linkid,msgid from company_mt where company='" + this.company + "' and sendstate = -3";
		try{
			System.out.println(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			while(rs.next()){
				//System.out.println("the msg is:" + rs.getString("msgid"));
				gamesList.add(rs.getString("game"));
				linkIdList.add(rs.getString("linkid"));
				msgIdList.add(rs.getString("msgid"));
			}
			if(gamesList.size() > 0){
				getSendStat();

				send2Company();
			}
			else{
				System.out.println(this.company + " no data");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			this.mydb.dbclose();
		}
		this.mydb.dbclose();
	}
	private void getSendStat(){//取得相应的发送状态
		String strSql = "";
		String linkId = "";
		try{
			//int linkSize = linkIdList.size();
			ArrayList newMsgIdList = new ArrayList();
			ArrayList newGamesList = new ArrayList();
			ArrayList newLinkIdList = new ArrayList();
			System.out.println("the linkId size is:" + linkIdList.size());
			for(int i = 0;i < linkIdList.size();i++){
				linkId = (String)linkIdList.get(i);
				strSql = "select stat_msg from sms_reportlog where linkid = '" + linkId + "'";
				//System.out.println(strSql);
				ResultSet rs = this.mydb.executeQuery(strSql);
				if(rs.next()){
					//System.out.println("add stat");
					newMsgIdList.add((String)msgIdList.get(i));
					newGamesList.add((String)gamesList.get(i));
					newLinkIdList.add((String)(String)linkIdList.get(i));
					statReportList.add(rs.getString("stat_msg"));
				}
				/*
				else{
					//noStatList.add(i);
					
					System.out.println(linkId + "is not stat");
					//System.out.println("remove" + i);
					
				}
				*/
			}
			linkIdList = newLinkIdList;
			msgIdList = newMsgIdList;
			gamesList = newGamesList;
/*
			for(int j = 0;j < linkIdList.size();j++){
				
				System.out.println("have stat:" + (String)linkIdList.get(j));
			}
*/			
			//System.out.println("games size is:" + gamesList.size());
		//System.out.println("games size is:" + msgIdList.size());
		//System.out.println("games size is:" + statReportList.size());
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	private void send2Company(){//用于将状态报告发送给合作方
		CompanyUrl companyUrl = new CompanyUrl("config.ini",company);
		companyUrl.loadParam();
		String compUrl = companyUrl.getCompanyUrl();
		System.out.println(company + "compUrl is:" + compUrl);
		System.out.println("gamesList size is:" + gamesList.size());
		System.out.println("msgIdList size is:" + msgIdList.size());
		System.out.println("statReportList size is:" + statReportList.size());
		if(!compUrl.equals("paramName")){
			
		System.out.println("Star send to" + compUrl);
		//String companyUrl = (String)ConfigManager.getInstance().getConfigItem(this.company,"xiangtone_serverip not found!");
		String strSql = "";
		int price = 0;
		String xmlReport = "";
		try{
					ArrayList compRecStat = new ArrayList();
					for(int i = 0;i < this.statReportList.size();i++){
						strSql = "select price from companygames where gamecode='" + (String)gamesList.get(i) + "'";
						ResultSet rs = this.mydb.executeQuery(strSql);
						System.out.println("msgId is:" + (String)msgIdList.get(i));
						if(rs.next()){
							price = rs.getInt("price");
						}
						xmlReport = orgainzeXmlReport(price,(String)msgIdList.get(i),(String)statReportList.get(i));
						SendMsgHttp proxy = new SendMsgHttp(compUrl);
						System.out.println("the xml is:" + xmlReport);
						
						 proxy.setBody(xmlReport.getBytes("UTF-8"));
						 String status = "-1";
						 
						 status = proxy.exec();
						 if (status == null || !status.equals("0")) {
			                status = proxy.exec();
						 }
						 
						 compRecStat.add(status);
						 
						 
					}
					updateStat(compRecStat);
					try{
						Thread.currentThread().sleep(800);
					}catch(Exception e){
						e.printStackTrace();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
		}
	}
	private void updateStat(ArrayList compRecStat){//更新发送状态
		String strSql = "";
		try{
			for(int i = 0;i < this.linkIdList.size();i++){
				strSql = "update company_mt set sendstate = 0,comprecstat='" + (String)compRecStat.get(i) + "' where linkid = '" + (String)this.linkIdList.get(i) + "'";
				this.mydb.executeUpdate(strSql);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private String orgainzeXmlReport(int price,String msgId,String statMsg){
		
		String statXml = "<pwd_message_list version=\"3.0\">";
		try{
			statXml += "<message_type>SMS_PT</message_type>";
		statXml += "<data>";
		statXml += "<message>";
		statXml += "<msgid>" + msgId + "</msgid>";
		statXml += "<feecode>" + price + "</feecode>";
		statXml += "<reportstat>" + statMsg + "</reportstat>";
		statXml += "</message>";
		statXml += "</data>";
		statXml += "</pwd_message_list>";
		}catch(Exception e){
			e.printStackTrace();
		}
		
		

		return statXml;
	}
}
