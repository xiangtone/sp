package GameMtHandle;

import java.sql.*;

import org.apache.log4j.Logger;

import com.xiangtone.util.DBForWrite;
import com.xiangtone.util.DBForMO;
import com.xiangtone.util.DBForMT;
import com.xiangtone.util.DBForRead;

public class SmsMoMtHandle {
	private static Logger logger = Logger.getLogger(SmsMoMtHandle.class);
	private DBForMT dbMT;
	private DBForMO dbMO;
	public CompanyMo compMo;
	public CompanyMt compMt;
	// public CompanyCount companyCount;
	private String status = "";

//	public SmsMoMtHandle(String dbIp, String dbName, String dbUser, String dbPwd, String dbPort) {
//		db = new DBForLocal();
//		db = new DBForRead();
//		compMo = new CompanyMo();
//		compMt = new CompanyMt();
//		// companyCount = new CompanyCount();
//	}
	
	public SmsMoMtHandle() {
		dbMT = new DBForMT();
		dbMO = new DBForMO();
		compMo = new CompanyMo();
		compMt = new CompanyMt();
	}

	public void smsCompanyMoHandle() {// 处理上行

		insertMo();
		// getCompanyMoCount();//q取得相应的合作方的业务上行条数的统计记录
		// addCompanyMoCount();//添加上行记录

		// String strSql = "";
	}

	public boolean checkCpn(String cpn) {// 查询是否是黑号码。是返回TRUE 否返回FALSE
		String strSql = "select cpn_id from blackcpns where cpn='" + cpn + "'";
		boolean checkFlag = true;
		try {
			logger.debug(strSql);
			ResultSet rs = dbMT.executeQuery(strSql);
			if (rs.next()) {
				checkFlag = true;
			} else {
				checkFlag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			checkFlag = true;
			logger.error(strSql,e);
		} finally {
			dbMT.close();
		}
		return checkFlag;
	}

	public void smsCompanyMtHandle() {

		insertMt();

	}

	////////////////////////// 以下用于上行的记录统计的操作//////////////////
	private void insertMo() {
		try {
			String companyName = getCompanyName(this.compMo.getCompany());
			// String strSql = "insert into company_mo set company='" +
			// companyName + "',game='" + this.compMo.getGame() +
			// "',userinput='" + this.compMo.getUserInput() + "',cpn = '" +
			// this.compMo.getCpn() + "',linkid='" + this.compMo.getMsgId()
			// +"',addate='" + FormatSysTime.getCurrentTimeA() +
			// "',comprecstat='" + this.status + "'";
			// System.out.println(strSql);
			// String tableName = FormatSysTime.getCurrentTime("yyyyMMdd") +
			// "company_mo";
			// String strSql = "insert into company_mo set company='" +
			// companyName + "',game='" + this.compMo.getGame() +
			// "',userinput='" + this.compMo.getUserInput() + "',cpn = '" +
			// this.compMo.getCpn() + "',msgid='" + this.compMo.getMsgId()
			// +"',linkid='" + this.compMo.getLinkId() +"',addate='" +
			// FormatSysTime.getCurrentTimeA() + "',comprecstat='-1'";
			String tableName = "tempcompany_mo";
			String strSql = "insert into " + tableName + " set company='" + companyName + "',game='"
					+ this.compMo.getGame() + "',userinput='" + this.compMo.getUserInput() + "',cpn = '"
					+ this.compMo.getCpn() + "',msgid='" + this.compMo.getMsgId() + "',linkid='"
					+ this.compMo.getLinkId() + "',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='-1'";
			logger.debug(strSql);
			dbMO.executeUpdate(strSql);
			String strSqlMos = "insert into company_mos set company='" + companyName + "',game='"
					+ this.compMo.getGame() + "',userinput='" + this.compMo.getUserInput() + "',cpn = '"
					+ this.compMo.getCpn() + "',msgid='" + this.compMo.getMsgId() + "',linkid='"
					+ this.compMo.getLinkId() + "',addate='" + FormatSysTime.getCurrentTimeA() + "',comprecstat='-1'";
			logger.debug(strSqlMos);
			dbMO.executeUpdate(strSqlMos);
		} catch (Exception e) {
			logger.error("",e);
			e.printStackTrace();
		} finally {
			dbMO.close();
		}

	}

	//////////////////// 以下用于下行记录统计的操作//////////////////////////
	private void insertMt() {
		String strSql = null;
		try {
			String pmId = this.checkGameCode(this.compMt.getCpn(), this.compMt.getGame());
			// String[] companysTemp = (this.compMt.getCompany()).split(":");
			String companyName = "";// getCompanyName(this.compMt.getCompany());
			String recp = "0";
			//////////////////////
			if ((this.compMt.getCompany()).indexOf(",") != -1) {
				// System.out.println("---------------存在扣量合作方");
				String companysTemp[] = (this.compMt.getCompany()).split(",");
				companyName = companysTemp[0];
				recp = companysTemp[1];
			} else if ((this.compMt.getCompany()).indexOf(":") != -1) {
				String[] companysTemp = (this.compMt.getCompany()).split(":");
				companyName = companysTemp[0];
			}
			String tableName = FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
			// String strSql = "insert into "+ tableName +" set company='" +
			// companyName + "',game='" + this.compMt.getGame() + "',content='"
			// + this.compMt.getContent() + "',msgid='" + this.compMt.getMsgId()
			// + "',linkid='" + this.compMt.getLinkId() + "',cpn = '" +
			// this.compMt.getCpn() + "',addate='" +
			// FormatSysTime.getCurrentTimeA() + "',sendstate='-3'";
			// String strSql = "insert into tempcompany_mt set company='" +
			// companyName + "',game='" + this.compMt.getGame() + "',content='"
			// + this.compMt.getContent() + "',msgid='" + this.compMt.getMsgId()
			// + "',linkid='" + this.compMt.getLinkId() + "',submit_linkid='" +
			// this.compMt.getLinkId() + "',cpn = '" + this.compMt.getCpn() +
			// "',addate='" + FormatSysTime.getCurrentTimeA() +
			// "',sendstate='-3'";
			strSql = "insert into tempcompany_mt set company='" + companyName + "',game='"
					+ this.compMt.getGame() + "',content='" + this.compMt.getContent() + "',msgid='"
					+ this.compMt.getMsgId() + "',linkid='" + this.compMt.getLinkId() + "',submit_linkid='"
					+ this.compMt.getLinkId() + "',cpn = '" + this.compMt.getCpn() + "',addate='"
					+ FormatSysTime.getCurrentTimeA() + "',sendstate='-3',pmid='" + pmId + "'";
			logger.debug(strSql);
			dbMT.executeUpdate(strSql);
			// strSql = "insert into companyscpns set company='" + companyName +
			// "',cpn = '" + this.compMt.getCpn() + "',month =
			// now(),addate=now()";
			// System.out.println("::::::::::::::::::::::::" + strSql);
			// mydbMT.executeUpdate(strSql);
		} catch (Exception e) {
			logger.error(strSql,e);
			e.printStackTrace();
		} finally {
			dbMT.close();
		}

	}

	// 用于随机取得相应业务Id号。
	// add at 2011-11-24
	private String checkGameCode(String cpn, String game) {
		String tempPmId = "";
		try {

			String strSql = "";
			strSql = "select pmid from tempcompany_mt where cpn='" + cpn + "' and game='" + game + "' and pmid !=''";
			logger.debug(strSql);
			ResultSet rs = dbMT.executeQuery(strSql);
			if (rs.next()) {
				tempPmId = rs.getString("pmid");
			} else {
				String tableName = FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
				strSql = "select pmid from " + tableName + " where cpn='" + cpn + "' and game='" + game
						+ "' and pmid !=''";
				logger.debug(strSql);
				rs = dbMT.executeQuery(strSql);
				if (rs.next()) {
					tempPmId = rs.getString("pmid");
				} else {
					strSql = "select gcode from pm_allimformation ORDER BY rand( ) LIMIT 1";
					logger.debug(strSql);
					rs = dbMT.executeQuery(strSql);
					if (rs.next()) {
						tempPmId = rs.getString("gcode");
					}
				}
			}
			// return tempPmId;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		} finally {
			dbMT.close();
		}
		return tempPmId;
	}

	/////////////////////////////
	private String getCompanyName(String compTag) {
		String strSql = "select company from companys where companytag='" + compTag + "'";
		String retCompanyName = "";
		try {
			ResultSet rs = dbMT.executeQuery(strSql);
			if (rs.next()) {
				retCompanyName = rs.getString("company");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		} finally {
			dbMT.close();
		}
		return retCompanyName;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
