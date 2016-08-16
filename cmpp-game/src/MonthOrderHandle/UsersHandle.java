package MonthOrderHandle;

/*
 * 用于取得订购包月的用户手机号码
 */
import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.xiangtone.util.DBForMonth;

import smsgm_adlx.Game;

public class UsersHandle {
	private static Logger logger = Logger.getLogger(UsersHandle.class);
	private DBForMonth mydb = null;
	private String feeCode = "";
	private String spId = "";

	public UsersHandle() {
		// String dbIp = "xiangtone_dbip";
		// String dbName = "smsmonthdb";
		// String dbUser = "xiangtone_dbuser";
		// String dbPwd = "xiangtone_dbpwd";
		// String dbPort = "xiangtone_dbport";
		mydb = new DBForMonth();
	}

	// 用于取得第一次订购的用户的信息
	public ArrayList getFirstOrderUsers() {
		String sendDate = FormatSysTime.getCurrentTimeB();
		String strSql = "select cpn from  companys_user where serviceid = '" + this.feeCode + "' and spid = '"
				+ this.spId + "' and state = '1' and firstsend='0' and sendate <= '" + sendDate + "' or serviceid = '"
				+ this.feeCode + "' and spid = '" + this.spId + "' and state = '3' and firstsend='0' and sendate <= '"
				+ sendDate + "' order by id asc limit 100";
		ArrayList usersList = new ArrayList();
		try {
			logger.debug(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			while (rs.next()) {
				usersList.add(rs.getString("cpn"));
			}
		} catch (Exception e) {
			logger.error(strSql,e);
		} finally {
			mydb.close();
		}

		return usersList;
	}

	public ArrayList getOrderUsers() {
		String sendDate = FormatSysTime.getCurrentTimeB();
		String strSql = "select cpn from companys_user where serviceid = '" + this.feeCode + "' and spid = '"
				+ this.spId + "' and state = '1' and firstsend='1' and sendate <= '" + sendDate + "' or serviceid = '"
				+ this.feeCode + "' and spid = '" + this.spId + "' and state = '3' and firstsend='1' and sendate <= '"
				+ sendDate + "' limit 100";
		logger.debug(strSql);
		ArrayList usersList = new ArrayList();
		try {
			ResultSet rs = this.mydb.executeQuery(strSql);
			while (rs.next()) {
				usersList.add(rs.getString("cpn"));
			}
		} catch (Exception e) {
			logger.error(strSql,e);
		} finally {
			mydb.close();
		}

		return usersList;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}
}
