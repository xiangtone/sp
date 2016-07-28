package newmosendhandle;
import java.util.ArrayList;
import java.sql.*;

public class MoCounter {
	public Mysqldb mydb = new Mysqldb();
	private int moNum = 0;
	private ArrayList sendMosList = new ArrayList();
	private String company = "";
	public void checkMo(){
		//String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mo";
		//String strSql = "select game,msgid,linkid,cpn,userinput from company_mo where company='" + this.company + "' and tocompstat=0 order by id desc limit 30";
		//System.out.println(strSql);
		String tableName = "tempcompany_mo";//add at 090806
		String strSql = "select game,userinput,cpn,msgid,linkid,addate from " + tableName + " where company='" + this.company + "' and tocompstat=0 order by id desc limit 30";

		try{
			ResultSet rs = mydb.executeQuery(strSql);
			int cpnType = 1;
			ArrayList tmepMosList = new ArrayList();
			SpCodeHandle spCodeHandle = new SpCodeHandle();
			while(rs.next()){
				/*
				 *
				 * mo.setMsgId(msgId);
				mo.setMobile(cpn);
				mo.setMobileType("" + cpnType);
				mo.setUserType("" + userType);
				mo.setGwId("cshdyd");
				mo.setSpCode("106650081");
				mo.setServiceType(serverId);
				mo.setLinkId(linkid);
				mo.setMoMsg(userInput);
				mo.setFeeFlag("1");
				mo.setCpProductId("101011");
				 */
				//if(this.checkCpn(rs.getString("cpn"))){
				//	cpnType = 3;
				//}
				Mo mo = new Mo();
				mo.setMsgId(rs.getString("msgid"));
				mo.setMobile(rs.getString("cpn"));
				//mo.setMobile("0");
				mo.setUserType("" + cpnType);
				mo.setGwId("cshdyd");
				spCodeHandle.setGame(rs.getString("game"));
				spCodeHandle.searchSpcode();
				mo.setSpCode(spCodeHandle.getSpcode());
				/*
				if(rs.getString("game").equals("4002")){
					mo.setSpCode("106650082");
				}
				else if(rs.getString("game").equals("1011")){
					mo.setSpCode("106650082");
				}
				else if(rs.getString("game").equals("1010")){
						mo.setSpCode("106650081");
				}
				else{
						mo.setSpCode("10665008");
				}
				*/
				mo.setServiceType(rs.getString("game"));
				mo.setLinkId(rs.getString("linkid"));
				mo.setMoMsg(rs.getString("userinput"));
				mo.setFeeFlag("1");
				if(rs.getString("userinput").length() > 2){
					mo.setCpProductId(rs.getString("game") + rs.getString("userinput").substring(0,2));
				}
				else if(rs.getString("userinput").length() > 1 && rs.getString("userinput").length() < 2 ){
					mo.setCpProductId(rs.getString("game") + rs.getString("userinput").substring(0,1));
				}
				else{
					mo.setCpProductId(rs.getString("game") + rs.getString("userinput"));
				}
				mo.setAddate(rs.getString("addate"));
				tmepMosList.add(mo);
			}
			spCodeHandle.mydb.dbclose();
			//System.out.println("the size is:" + tmepMosList.size());
			setMoNum(tmepMosList.size());
			setMosList(tmepMosList);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public boolean checkCpn(String cpn){//查询是否是黑号码。是返回TRUE 否返回FALSE
		Mysqldb mydb1 = new Mysqldb();
		String strSql = "select cpn_id from blackcpns where cpn='" + cpn + "'";
		boolean checkFlag = true;
		try{
				ResultSet rs = mydb1.executeQuery(strSql);
				if(rs.next()){
					checkFlag = true;
				}
				else{
					checkFlag = false;
				}
		}catch(Exception e){
			mydb1.dbclose();
			e.printStackTrace();
			checkFlag = true;
		}
		mydb1.dbclose();
		return checkFlag;
	}
	
	//将update变为insert
	public void updateSendStat(String stat){
		String strSql = "";
		for(int i = 0;i < this.sendMosList.size();i++){
			Mo mo = (Mo)this.sendMosList.get(i);
			//String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mo";
				String tableName = "tempcompany_mo";//add at 090806
			strSql = "update "+ tableName +" set tocompstat=1,comprecstat='" + stat+ "' where linkid='" + mo.getLinkId()+ "'";
			//"insert into company_mosend set company='" + this.company + "',game='1010',userinput='" + this.compMo.getUserInput() + "',cpn = '" + this.compMo.getCpn() + "',msgid='" + this.compMo.getMsgId() +"',linkid='" + this.compMo.getLinkId() +"',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='-1'";

			//strSql = "insert into company_mosend set company='" + this.company + "',game='1010',userinput='" + mo.getMoMsg() + "',cpn='" + mo.getMobile() + "',msgid = '" + mo.getMsgId() + "',linkid='" + mo.getLinkId()+ "',addate=now(),tocompstat=1,comprecstat='" + stat+ "'";
			//this.mydb.executeUpdate(strSql);
			//strSql = "delete from company_mo where linkid='" + mo.getLinkId()+ "'";
			this.mydb.executeUpdate(strSql);
		}
	}
	public void updateMoRecStat(String stat,String linkid){//用于深讯和的接收状态更新
			String strSql = "";
			String tableName = "tempcompany_mo";//add at 090806
			strSql = "update "+ tableName +" set tocompstat='1',comprecstat='" + stat+ "' where linkid='" + linkid+ "'";
			System.out.println(strSql);
			this.mydb.executeUpdate(strSql);
		}

	/*
	////////////////////////
	public void updateSendStat(String stat){
		String strSql = "";
		for(int i = 0;i < this.sendMosList.size();i++){
			Mo mo = (Mo)this.sendMosList.get(i);
			String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mo";
			strSql = "insert into moreceive set company='" + this.company + "',tocompstat=1,comprecstat='" + stat+ "',linkid='" + mo.getLinkId()+ "',recdate=now()";
			//"insert into company_mosend set company='" + this.company + "',game='1010',userinput='" + this.compMo.getUserInput() + "',cpn = '" + this.compMo.getCpn() + "',msgid='" + this.compMo.getMsgId() +"',linkid='" + this.compMo.getLinkId() +"',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='-1'";

			//strSql = "insert into company_mosend set company='" + this.company + "',game='1010',userinput='" + mo.getMoMsg() + "',cpn='" + mo.getMobile() + "',msgid = '" + mo.getMsgId() + "',linkid='" + mo.getLinkId()+ "',addate=now(),tocompstat=1,comprecstat='" + stat+ "'";
			//this.mydb.executeUpdate(strSql);
			//strSql = "delete from company_mo where linkid='" + mo.getLinkId()+ "'";
			this.mydb.executeUpdate(strSql);
		}
	}
	*/
	///////////////////////
	public void setCompany(String company){
		this.company = company;
	}
	public void setMoNum(int moNum){
		this.moNum = moNum;
	}
	public void setMosList(ArrayList mosList){
		this.sendMosList = mosList;
	}

	public int getMoNum(){
		return this.moNum;
	}
	public ArrayList getMosList(){
		return this.sendMosList;
	}

}
