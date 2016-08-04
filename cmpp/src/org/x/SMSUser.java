package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import java.sql.*;

import com.xiangtone.util.DBForLocal;
import com.xiangtone.util.DBForLog;
import com.xiangtone.util.FormatSysTime;

/**
 * A System Time format
 *
 */
public class SMSUser {
	private static Logger logger = Logger.getLogger(SMSUser.class);
	public String cpn;
	public int cpnType;
	public String ismgID;
	public String registerTime;
	public String lastVisitTime;
	public int creditMoney;
	public String corpId;
	public String corpSpcode;

	private String strSql = "";
	private DBForLocal dblocal = new DBForLocal();
	private DBForLog dblog = new DBForLog();
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public String getUserCpn() {
		return cpn;
	}

	public String getUserIsmgID() {
		return ismgID;
	}

	public String getUserRegisterTime() {
		return registerTime;
	}

	public String getUserLastVisitTime() {
		return lastVisitTime;
	}

	public int getUserCreditMoney() {
		return creditMoney;
	}

	public String getUserCorpID() {
		return corpId;
	}

	public String getUserCorpSpcode() {
		return corpSpcode;
	}

	public void setUserCpn(String strCpn) {
		this.cpn = strCpn;
	}

	public void setUserCpnType(int cpnType) {
		this.cpnType = cpnType;
	}

	public void setUserIsmgID(String strIsmgID) {
		this.ismgID = strIsmgID;
	}

	public void setUserRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public void setUserLastVisitTime(String lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}

	public void setUserCreditMoney(int credit) {
		this.creditMoney = credit;
	}

	public void setUserCorpID(String strCorpid) {
		this.corpId = strCorpid;
	}

	public void setUserCorpSpcode(String strCorpSpcode) {
		this.corpSpcode = strCorpSpcode;
	}

	public SMSUser() {
	}

	/**
	 * method one insert user log
	 */
	public void insertNewUser() {

		try {
			// 插入
			strSql = "insert into sms_user set ";
			strSql += "cpn='" + this.cpn + "'";
			strSql += ",ismgid='" + this.ismgID + "'";
			strSql += ",corp_id='" + this.corpId + "'";
			strSql += ",corp_spcode='" + this.corpSpcode + "'";
			strSql += ",register_time='" + this.registerTime + "'";
			strSql += ",last_visit_time='" + this.lastVisitTime + "'";
			strSql += ",visit_times=1";
			logger.debug(strSql);
			dblocal.executeUpdate(strSql);
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			dblocal.close();
		}
	}

	/**
	 * 判断该用户是否已经存在
	 *
	 */
	public boolean userIsExist() {
		boolean flag = false;
		try {
			strSql = "select * from sms_user where cpn='" + this.cpn + "'";
			logger.debug(strSql);
			rs = dblog.executeQuery(strSql);
			if (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			dblocal.close();
		}
		return flag;
	}

	/**
	 * 更新
	 *
	 */
	public void updateUserVisitTime(String _cpn) {
		strSql = "update sms_user set last_visit_time='" + FormatSysTime.getCurrentTimeA()
				+ "',visit_times =visit_times +1 ";
		strSql += " where cpn = '" + _cpn + "'";
		logger.debug(strSql);
		try {
			dblocal.executeUpdate(strSql);
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			dblocal.close();
		}

	}

	public void updateUserVisitTime() {
		strSql = "update sms_user set last_visit_time='" + FormatSysTime.getCurrentTimeA()
				+ "',visit_times =visit_times +1 ";
		strSql += " where cpn = '" + this.cpn + "'";
		logger.debug(strSql);
		try {
			dblocal.executeUpdate(strSql);
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			dblocal.close();
		}

	}

	/**
	 * 加钱
	 *
	 */
	public boolean increaseCreditMoney(String cpn, int money) {
		boolean flag = false;
		try {
			strSql = " update sms_user set creditmoney=creditmoney+" + money + " where cpn ='" + cpn + "'";
			logger.debug(strSql);
			dblocal.executeUpdate(strSql);
			flag = true;
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			dblocal.close();
		}
		return flag;
	}

	/**
	 * 减钱
	 *
	 */
	public boolean decreaseCreditMoney(String cpn, int money) {
		boolean flag = false;
		try {

			strSql = "update sms_user set creditmoney=creditmoney-" + money + "  where mobile='" + cpn + "'";
			logger.debug(strSql);
			dblocal.executeUpdate(strSql);
			flag = true;
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			dblocal.close();
		}
		return flag;
	}

	/**
	 * 取用户余额
	 *
	 *
	 */
	public int getCreditMoney(String cpn) {
		int balance = 0;
		try {
			strSql = "select * from sms_user where cpn='" + cpn + "'";
			logger.debug(strSql);
			rs = dblog.executeQuery(strSql);
			if (rs.next()) {
				balance = rs.getInt("creditmoney");
			}
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
			dblocal.close();
		}
		return balance;
	}

}