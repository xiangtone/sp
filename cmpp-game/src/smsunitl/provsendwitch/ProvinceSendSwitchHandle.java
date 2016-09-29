package smsunitl.provsendwitch;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

import smsunitl.cpnprovdepart.*;

import com.xiangtone.util.DBForMT;
import com.xiangtone.util.FormatSysTime;//add at 2011-08-02

public class ProvinceSendSwitchHandle {
	private static Logger logger = Logger.getLogger(ProvinceSendSwitchHandle.class);
	private String provId = "";
	private String postCode = "";
	private int sendSwitch;
	private String cpn = "";
	private String company = "";
	private String game = "";
	private boolean provGameFlag = false;
	public DBForMT mydb = null;
	// String dbIp = "xiangtone_dbip";
	// String dbName = "mts";
	// String dbUser = "xiangtone_dbuser";
	// String dbPwd = "xiangtone_dbpwd";
	// String dbPort = "xiangtone_dbport";
	CompanyRateOperate companyRateOperate;

	public ProvinceSendSwitchHandle() {
		this.mydb = new DBForMT();
		this.companyRateOperate = new CompanyRateOperate();
	}

	public void checkSwitch() {
		try {
			long startTime = System.currentTimeMillis();
			int tempSwitch = -1;
			WhiteCpnsOperate whiteCpnsOperate = new WhiteCpnsOperate();

			/////////////////////////////
			/*
			 * if(whiteCpnsOperate.checkModify()){
			 * whiteCpnsOperate.whiteCpnsList = null; //whiteCpnsOperate.init();
			 * try{ logger.debug("config file is modify");
			 * //Thread.currentThread().sleep(1000 * 6);
			 * 
			 * }catch(Exception e){ e.printStackTrace(); } }
			 * ///////////////////////////// whiteCpnsOperate.init(); List
			 * whiteCpnsList =
			 * whiteCpnsOperate.getWhiteCpnsList();//whiteCpnsOperate.
			 * getWhiteCpnsList();//Arrays.asList(tempCpns);//whiteCpnsOperate.
			 * getWhiteCpnsList();//Arrays.asList(tempCpns); //logger.debug(
			 * "the white cpn size is:" + whiteCpnsList.size()); int index =
			 * whiteCpnsList.indexOf(this.cpn); long whitecpnTime =
			 * System.currentTimeMillis();
			 * 
			 * if(index >= 0){ tempSwitch = 0; }
			 */
			whiteCpnsOperate.setCpn(this.cpn);
			whiteCpnsOperate.checkWhiteCpn();
			boolean whiteCpnsFlag = whiteCpnsOperate.getWhiteCpnFlag();
			// if(index >= 0){
			if (whiteCpnsFlag) {
				tempSwitch = 99;
			} else {
				/////////////////////////////////////////////
				ProvinceHandle ph = new ProvinceHandle();
				ph.setCpn(this.cpn);
				ph.miscIdCheck();
				this.provId = ph.getMiscId();
				this.postCode = ph.getPostCode();
				// logger.debug("the provId is:::::::::::::::::" +
				// this.provId);
				// Reader reader =
				// Resources.getResourceAsReader("SqlMapConfig.xml");
				// SqlMapClient sqlMap =
				// SqlMapClientBuilder.buildSqlMapClient(reader);
				// int tempSwitch =
				// ((Integer)sqlMap.queryForObject("checkprovswitch",
				// this.provId)).intValue();
				tempSwitch = checkSendSwitch();
				//////////////////////////////
				// 用于灰号码操作
				if (this.checkGrayCpn()) {
					tempSwitch = 3;
				}
				//////////////////////////////
				if (tempSwitch == 0) {
					this.companyRateOperate.setCompanyName(this.company);
					this.companyRateOperate.setProvId(this.provId);
					this.companyRateOperate.checkLostOperate();
					if (this.companyRateOperate.getLostFlag()) {
						tempSwitch = 2;
					}
				}
			}
			/////////////////////////////////////////////
			this.setSendSwitch(tempSwitch);
			long endTime = System.currentTimeMillis();
			logger.debug("use total time:" + (startTime - endTime) / 1000);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private int checkSendSwitch() {
		// 41 省份关闭，42省份到量，43 0-8点关闭，44地市关闭
		String strSql = "select sendflag,cityflag,flag from companyprovswitch where  miscid='" + provId
				+ "' and company='" + this.company + "'";
		logger.debug(strSql);
		int sendSwitch = 0;
		int citySwitch = 1;
		String flag = "";
		try {
			ResultSet rs = this.mydb.executeQuery(strSql);
			if (rs.next()) {
				sendSwitch = rs.getInt("sendflag");
				citySwitch = rs.getInt("cityflag");
				flag = rs.getString("flag");
				if (sendSwitch == 1 && flag.equals("H")) {
					sendSwitch = 41;
				} else if (sendSwitch == 1 && flag.equals("A")) {
					sendSwitch = 42;
				} else if (sendSwitch == 1 && flag.equals("Y")) {
					sendSwitch = 43;
				}
			}
			if (sendSwitch == 0 && citySwitch == 1) {
				sendSwitch = checkCompanyCitySendSwitch();
				if (sendSwitch == 1) {
					sendSwitch = 44;
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			mydb.close();
		}
		return sendSwitch;
	}

	private int checkCompanyCitySendSwitch() {
		int companyCitySendSwitch = 0;
		String strSql = "select sendflag from companycityswitch where company = '" + this.company + "' and city='"
				+ this.postCode + "'";
		try {
			logger.debug(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			if (rs.next()) {
				companyCitySendSwitch = rs.getInt("sendflag");
			}
			// logger.debug(strSql);
			logger.debug("the city sendflag is:" + companyCitySendSwitch);
		} catch (Exception e) {
			companyCitySendSwitch = 1;
			logger.error("", e);
		} finally {
			mydb.close();
		}
		return companyCitySendSwitch;
	}

	/////////////////////////////////////////////
	private boolean checkGrayCpn() {
		// 用于检查灰号码
		String table = "blackcpn";
		String strSql = "select state from " + table + " where cpn='" + this.cpn + "' and state !='3' limit 1";
		// logger.debug("the graycpn is:::::::::::::::::" + strSql);
		boolean grayFlag = false;
		try {
			logger.debug(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			if (rs.next()) {
				int state = rs.getInt("state");
				if (state == 2) {
					int mtNum = this.checkMtNum();
					if (mtNum > 1) {
						grayFlag = true;
					}
				}
			}
		} catch (Exception e) {
			grayFlag = false;
			logger.error("", e);
		} finally {
			mydb.close();
		}
		return grayFlag;
	}

	// 查询相应号码已有下行条数
	private int checkMtNum() {
		int mtNum = 0;
		String mtTable = FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";// 20110715company_mt
		String strSql = "select count(*) as count from " + mtTable + " where cpn='" + this.cpn
				+ "' and sendstate='DELIVRD'";
		try {
			logger.debug(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			if (rs.next()) {
				mtNum = rs.getInt("count");
			}
		} catch (Exception e) {
			mtNum = 0;
			logger.error("", e);
		} finally {
			mydb.close();
		}
		return mtNum;
	}

	// 保业务用
	public void checkProvincePfRound() {
		try {
			ProvinceHandle ph = new ProvinceHandle();
			ph.setCpn(this.cpn);
			ph.miscIdCheck();
			this.provId = ph.getMiscId();
			int provGameIndex = this.privGameIndex();
			int userIndex = this.userGameIndex();
			int currentGameNum = this.checkGameNum();// 用于统计当天业务数据
			// logger.debug("the result is::::::" + provGameIndex + ":" +
			// currentGameNum + ":" +userIndex + ":" + currentGameNum);
			if (currentGameNum < provGameIndex && provGameIndex > 0) {
				int userCurrentNum = this.checkMtNum();
				// logger.debug("the other is::::::" + userIndex + ":" +
				// userCurrentNum);
				this.provGameFlag = true;

				if (userCurrentNum < userIndex) {
					this.provGameFlag = true;
				} else {
					this.provGameFlag = false;
				}

			} else {
				this.provGameFlag = false;
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private int checkGameNum() {
		int mtNum = 0;
		String mtTable = FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";// 20110715company_mt
		String strSql = "select count(distinct linkid) as count from " + mtTable + " where game='" + this.game
				+ "' and sendstate='DELIVRD' and province='" + this.provId + "'";
		logger.debug(strSql);
		try {
			ResultSet rs = this.mydb.executeQuery(strSql);
			if (rs.next()) {
				mtNum = rs.getInt("count");
			}
		} catch (Exception e) {
			mtNum = 0;
			logger.error("", e);
		} finally {
			mydb.close();
		}
		// logger.debug("the count
		return mtNum;
	}

	// 提取合作方相应省份业务计划收入
	private int privGameIndex() {
		int limit = 0;
		String operateDate = FormatSysTime.getCurrentTime("yyyy-MM-dd");
		String strSql = "select limitnum from privgameindex where prov='" + this.provId + "' and game='" + this.game
				+ "' and company='" + this.company + "' and opendate='" + operateDate + "'";
		logger.debug(strSql);
		try {
			ResultSet rs = this.mydb.executeQuery(strSql);
			if (rs.next()) {
				limit = rs.getInt("limitnum");
			}
		} catch (Exception e) {
			limit = 0;
			logger.error("", e);
		} finally {
			mydb.close();
		}
		return limit;
	}

	// 获取每个用户的允许的值
	private int userGameIndex() {
		int limit = 0;
		String operateDate = FormatSysTime.getCurrentTime("yyyy-MM-dd");
		String strSql = "select userlimit from privgameindex where prov='" + this.provId + "' and game='" + this.game
				+ "' and company='" + this.company + "' and opendate='" + operateDate + "'";
		try {
			logger.debug(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			if (rs.next()) {
				limit = rs.getInt("userlimit");
			}
		} catch (Exception e) {
			e.printStackTrace();
			limit = 0;
			logger.error("", e);
		} finally {
			mydb.close();
		}
		return limit;
	}

	//////////////////////////////////////////////
	public void setCpn(String cpn) {
		this.cpn = cpn;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public void setSendSwitch(int sendSwitch) {
		this.sendSwitch = sendSwitch;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public int getSendSwitch() {
		return this.sendSwitch;
	}

	// add at 2011-12-06
	public boolean getProvGameFlag() {
		return this.provGameFlag;
	}

	public static void main(String[] args) {
		ProvinceSendSwitchHandle pss = new ProvinceSendSwitchHandle();
		String cpn = "13859905479";
		pss.setCpn(cpn);
		pss.checkSwitch();
		logger.debug("the switch is:" + pss.getSendSwitch());
	}
}
