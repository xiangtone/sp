package MonthOrderHandle;
import DBHandle.*;
import java.sql.*;
import java.util.*;
public class MessageHandle {
	public MysqldbT mydb = null;
	private String cpn = "";
	String dbIp = "";
	String dbName = "";
	String dbUser = "";
	String dbPwd = "";
	String dbPort = "";
	String spId = "";
	String serverId = "";
	int cycleDay = 0;//用于设置发送时间
	ArrayList msgsList = new ArrayList();
	public MessageHandle(){
		/*
		String dbIp = "xiangtone_dbip";
		String dbName = "qwxddd_dbname";
		String dbUser = "xiangtone_dbuser";
		String dbPwd = "xiangtone_dbpwd";
		String dbPort = "xiangtone_dbport";
		mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
		*/
		//this.mydb = new 
	}
	public void createDbConnect(){
		mydb = new MysqldbT(this.dbIp,this.dbName,this.dbUser,this.dbPwd,this.dbPort);	
	}
	public String msgHandle(){
		
		//int sendFlag = checkSendState();
		//System.out.println("send flag is:" + sendFlag);
		String nextMsg = "";
		//if(sendFlag == 0){
			nextMsg = getNextMessage();
			insertSendRecord(nextMsg);
		//}
		
		return nextMsg;
		
		
		//String strSql = "select msgid from user where cpn = '" + this.cpn + "' and "
	}
	private void Messages(){
		String strSql = "select id from messages where spid='" + this.spId + "' and serverid ='" + this.serverId + "' order by id asc";
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			while(rs.next()){
				msgsList.add(new Integer(rs.getInt("id")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * 取得本次要下发的信息Id
	 */
	private String getNextMessage(){
		this.Messages();
		String strSql = "select msgid from companys_user  where spid='" + this.spId + "' and serviceid='" + this.serverId + "' and cpn = '" + this.cpn + "'";
		String msg = "";
		try{
			System.out.println(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			int msgId = 0;
		
			if(this.msgsList.size() > 0){
					if(rs.next()){
						msgId = rs.getInt("msgid");
						//System.out.println(msgId + ",the msgsize is:" + this.msgsList.size());
						
						if(msgId < this.msgsList.size()-1){
							msgId = msgId + 1;
							
						}
						else{
							msgId = 0;
						}
						//msg = getMessage(((Integer)this.msgsList.get(msgId)).intValue());
						if(msgId > 0){
							msg = getMessage(((Integer)this.msgsList.get(msgId-1)).intValue());
						}
						else{
							msg = getMessage(((Integer)this.msgsList.get(msgId)).intValue());
						}
					}
					else{
						msg = getMessage(((Integer)this.msgsList.get(msgId)).intValue());
						//insertNewUser();//添加新用户
					}
					updateSendMsg(msgId);//用于更新用户发送状态
					this.msgsList.clear();
			}
			else{
				msg = "谢谢您的关注，精彩内容稍后奉上，敬请期待";	
				updateSendMsg(msgId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return msg;
	}
	/*
	 * 用于取得相应的信息内容
	 */
	private String getMessage(int messageId){
		String strSql = "select msg from messages where id = '" + messageId + "'";
		String retMsg = "";
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			if(rs.next()){
				retMsg = rs.getString("msg");
			}
			else{
				retMsg="谢谢您的关注，精彩内容稍后奉上，敬请期待";	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retMsg;
	}
	/*
	 * 用于检查用户发送状态
	 */
	private int checkSendState(){
		
		String strSql = "select cpn from user where cpn = '" + this.cpn + "'";
		int sendState = 0;
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			String sendDate = "";
			if(rs.next()){
				sendState = getSendState();
			}
			else{
				this.insertNewUser();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sendState;
	}
	private int getSendState(){
		String sendate = FormatSysTime.getCurrentTimeB();
		String strSql = "select sendate from user where cpn = '" + this.cpn + "'";
		String sendDate = "";
		int sendState = 0;
		//System.out.println(sendate);
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			if(rs.next()){
				sendDate = rs.getString("sendate");
				//System.out.println(sendDate + "," + sendate);
				if(sendDate.equals(sendate)){
					sendState = 0;
				}
				else{
					sendState = 1;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return sendState;
	}
	/*
	 * 用于更新用户本次的发送信息
	 */
	private void updateSendMsg(int msgId){
		//String sendate = FormatSysTime.getCurrentTimeB();
		 java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		 Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.DAY_OF_MONTH,+this.cycleDay);
		 String sendate = format.format(cal.getTime());
		String sendTime = FormatSysTime.getCurrentTimeA();
		String strSql = "update companys_user set msgid = '" + msgId + "',sendate = '" + sendate + "',firstsend='1',sendflag = '1',sendedtime = '" + sendTime + "' where spid='" + this.spId + "' and serviceid='" + this.serverId + "' and cpn = '" + this.cpn + "'";
		//System.out.println(strSql);
		try{
			this.mydb.executeUpdate(strSql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void insertNewUser(){
		String sendate = FormatSysTime.getCurrentTimeB();
		String sendTime = FormatSysTime.getCurrentTimeA();
		String strSql = "insert into user set spid = '" + this.spId + "',cpn = '" + this.cpn + "',msgid = '0',sendflag = '0',sendate = '" + sendate + "'";
		try{
			this.mydb.executeUpdate(strSql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void insertSendRecord(String retMsg){
		String strSql = "insert into sendrecord set spid = '" + this.spId + "',serviceid='" + this.serverId + "',cpn = '" + this.cpn + "',msg = '" + retMsg + "',sendtime=now()";
		try{
			this.mydb.executeInsert(strSql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void setCpn(String cpn){
		this.cpn = cpn;
	}
	public void setDbIp(String dbIp){
		this.dbIp = dbIp;
	}
	public void setDbName(String dbName){
		this.dbName = dbName;
	}
	public void setDbUser(String dbUser){
		this.dbUser = dbUser;
	}
	public void setDbPwd(String dbPwd){
		this.dbPwd = dbPwd;
	}
	public void setDbPort(String port){
		this.dbPort = port;
	}
	public void setSpId(String spId){
		this.spId = spId;	
	}
	public void setServerId(String serverId){
		this.serverId = serverId;	
	}
	public void setSendCycleDay(int cycleDay){
		this.cycleDay = cycleDay;	
	}
}
