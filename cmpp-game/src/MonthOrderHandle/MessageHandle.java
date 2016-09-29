package MonthOrderHandle;

import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.xiangtone.util.DBForMonth;

public class MessageHandle {
	private static Logger logger = Logger.getLogger(MessageHandle.class);
	private DBForMonth mydb = null;
	private String cpn = "";
	String spId = "";
	String serverId = "";
	int cycleDay = 0;// �������÷���ʱ��
	ArrayList msgsList = new ArrayList();

	public MessageHandle() {
		/*
		 * String dbIp = "xiangtone_dbip"; String dbName = "qwxddd_dbname";
		 * String dbUser = "xiangtone_dbuser"; String dbPwd = "xiangtone_dbpwd";
		 * String dbPort = "xiangtone_dbport"; mydb = new
		 * MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
		 */
		// this.mydb = new
	}

	public void createDbConnect() {
		mydb = new DBForMonth();
	}

	public String msgHandle() {

		// int sendFlag = checkSendState();
		// logger.debug("send flag is:" + sendFlag);
		String nextMsg = "";
		// if(sendFlag == 0){
		nextMsg = getNextMessage();
		insertSendRecord(nextMsg);
		// }

		return nextMsg;

		// String strSql = "select msgid from user where cpn = '" + this.cpn +
		// "' and "
	}

	private void Messages() {
		String strSql = "select id from messages where spid='" + this.spId + "' and serverid ='" + this.serverId
				+ "' order by id asc";
		logger.debug(strSql);
		try {
			ResultSet rs = this.mydb.executeQuery(strSql);
			while (rs.next()) {
				msgsList.add(new Integer(rs.getInt("id")));
			}
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			mydb.close();
		}
	}

	/*
	 * ȡ�ñ���Ҫ�·�����ϢId
	 */
	private String getNextMessage() {
		this.Messages();
		String strSql = "select msgid from companys_user where spid='" + this.spId + "' and serviceid='" + this.serverId
				+ "' and cpn = '" + this.cpn + "'";
		String msg = "";
		try {
			logger.debug(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			int msgId = 0;

			if (this.msgsList.size() > 0) {
				if (rs.next()) {
					msgId = rs.getInt("msgid");
					// logger.debug(msgId + ",the msgsize is:" +
					// this.msgsList.size());

					if (msgId < this.msgsList.size() - 1) {
						msgId = msgId + 1;

					} else {
						msgId = 0;
					}
					// msg =
					// getMessage(((Integer)this.msgsList.get(msgId)).intValue());
					if (msgId > 0) {
						msg = getMessage(((Integer) this.msgsList.get(msgId - 1)).intValue());
					} else {
						msg = getMessage(((Integer) this.msgsList.get(msgId)).intValue());
					}
				} else {
					msg = getMessage(((Integer) this.msgsList.get(msgId)).intValue());
					// insertNewUser();//������û�
				}
				updateSendMsg(msgId);// ���ڸ����û�����״̬
				this.msgsList.clear();
			} else {
				msg = "лл���Ĺ�ע�����������Ժ���ϣ������ڴ�";
				updateSendMsg(msgId);
			}
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			mydb.close();
		}
		return msg;
	}

	/*
	 * ����ȡ����Ӧ����Ϣ����
	 */
	private String getMessage(int messageId) {
		String strSql = "select msg from messages where id = '" + messageId + "'";
		String retMsg = "";
		try {
			logger.debug(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			if (rs.next()) {
				retMsg = rs.getString("msg");
			} else {
				retMsg = "лл���Ĺ�ע�����������Ժ���ϣ������ڴ�";
			}
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			mydb.close();
		}
		return retMsg;
	}

	/*
	 * ���ڼ���û�����״̬
	 */
	private int checkSendState() {

		String strSql = "select cpn from user where cpn = '" + this.cpn + "'";
		int sendState = 0;
		try {
			logger.debug(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			String sendDate = "";
			if (rs.next()) {
				sendState = getSendState();
			} else {
				this.insertNewUser();
			}
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			mydb.close();
		}
		return sendState;
	}

	private int getSendState() {
		String sendate = FormatSysTime.getCurrentTimeB();
		String strSql = "select sendate from user where cpn = '" + this.cpn + "'";
		String sendDate = "";
		int sendState = 0;
		// logger.debug(sendate);
		try {
			logger.debug(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			if (rs.next()) {
				sendDate = rs.getString("sendate");
				// logger.debug(sendDate + "," + sendate);
				if (sendDate.equals(sendate)) {
					sendState = 0;
				} else {
					sendState = 1;
				}
			}

		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			mydb.close();
		}
		return sendState;
	}

	/*
	 * ���ڸ����û����εķ�����Ϣ
	 */
	private void updateSendMsg(int msgId) {
		// String sendate = FormatSysTime.getCurrentTimeB();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, +this.cycleDay);
		String sendate = format.format(cal.getTime());
		String sendTime = FormatSysTime.getCurrentTimeA();
		String strSql = "update companys_user set msgid = '" + msgId + "',sendate = '" + sendate
				+ "',firstsend='1',sendflag = '1',sendedtime = '" + sendTime + "' where spid='" + this.spId
				+ "' and serviceid='" + this.serverId + "' and cpn = '" + this.cpn + "'";
		logger.debug(strSql);
		try {
			this.mydb.executeUpdate(strSql);
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			mydb.close();
		}
	}

	private void insertNewUser() {
		String sendate = FormatSysTime.getCurrentTimeB();
		String sendTime = FormatSysTime.getCurrentTimeA();
		String strSql = "insert into user set spid = '" + this.spId + "',cpn = '" + this.cpn
				+ "',msgid = '0',sendflag = '0',sendate = '" + sendate + "'";
		try {
			logger.debug(strSql);
			this.mydb.executeUpdate(strSql);
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			mydb.close();
		}
	}

	private void insertSendRecord(String retMsg) {
		String strSql = "insert into sendrecord set spid = '" + this.spId + "',serviceid='" + this.serverId
				+ "',cpn = '" + this.cpn + "',msg = '" + retMsg + "',sendtime=now()";
		try {
			logger.debug(strSql);
			this.mydb.executeUpdate(strSql);
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			mydb.close();
		}
	}

	public void setCpn(String cpn) {
		this.cpn = cpn;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public void setSendCycleDay(int cycleDay) {
		this.cycleDay = cycleDay;
	}
}
