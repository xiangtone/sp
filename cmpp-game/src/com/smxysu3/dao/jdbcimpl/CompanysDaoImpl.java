package com.smxysu3.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.smxysu3.dao.CompanysDao;
import com.smxysu3.model.Companys;
import com.xiangtone.util.ConnectionSmsgame_platform;
import com.xiangtone.util.DBForMO;
import com.xiangtone.util.DBForRead;

import smsunitl.provsendwitch.ProvinceSendSwitchHandle;
import smsunitl.companylimit.LimitDayAndMonthByCpn;
import smsunitl.cpnprovdepart.*;

public class CompanysDaoImpl implements CompanysDao {
	private static Logger logger = Logger.getLogger(CompanysDaoImpl.class);
	//单例模式
	private static CompanysDaoImpl instance = new CompanysDaoImpl();

	private CompanysDaoImpl() {
	}

	public Companys selectByCompanytag(String companytag, String cpn) {
		// System.out.println("the companytag is:--------------" + companytag);
//		String dbIp = "xiangtone_dbip";
//		String dbName = "mosdb";
//		String dbMtName = "mts";
//		String dbUser = "mosuser";
//		String dbPwd = "mospwd";
//		String dbPort = "xiangtone_dbport";
		String tmpCompanyTag = companytag;
		String provId = "";
		ProvinceHandle ph = new ProvinceHandle();
		ph.setCpn(cpn);
		ph.miscIdCheck();
		provId = ph.getMiscId();
		DBForMO mydb = new DBForMO();
		int sendFlag = 0;
		String sql = "select id,company,cpip,game,addate,flag from companys where companytag = '" + companytag
				+ "' and provid = '" + provId + "'";
		
		// Connection connection = null;
		// PreparedStatement preParedStatement = null;
		ResultSet resultSet = null;
		Companys company = null;
		int provSendSwitch = 0;
		try {
			company = new Companys();
			logger.debug(sql);
			resultSet = mydb.executeQuery(sql);

			if (resultSet.next()) {
				provSendSwitch = this.checkProvince(resultSet.getString("company"), cpn);
				company.setId(resultSet.getInt("id"));
				sendFlag = resultSet.getInt("flag");
				// System.out.println(resultSet.getInt("id"));
				if (provSendSwitch == 41 || provSendSwitch == 42 || provSendSwitch == 43 || provSendSwitch == 44) {
					/*
					 * company.setCompany(resultSet.getString("company") +
					 * "pf"); sendFlag=1;
					 */
					String game = companytag.substring(0, 4);
					boolean checkFlag = this.checkProvPfRound(resultSet.getString("company"), cpn, game);// 用户放量保业务。
					if (checkFlag) {
						company.setCompany(resultSet.getString("company") + ":0");
						sendFlag = 0;
					} else {
						company.setCompany(resultSet.getString("company") + "pf:" + provSendSwitch);
						sendFlag = 1;
					}
				} else if (provSendSwitch == 2) {
					company.setCompany("tltd," + resultSet.getString("company"));
					companytag = companytag.substring(0, 4);
					sendFlag = 0;
				} else if (provSendSwitch == 3) {
					company.setCompany(resultSet.getString("company") + "gp:5");// 灰号码严格监控状态中，已不允许发送
					sendFlag = 1;
				} else if (provSendSwitch == 99) {// 用于白号码
					company.setCompany(resultSet.getString("company") + ":99");
					sendFlag = 0;
				} else {
					String limitType = "";
					LimitDayAndMonthByCpn limit = new LimitDayAndMonthByCpn();
					try {
						limitType = limit.limitByCpn(resultSet.getString("company"), cpn);
					} catch (Exception e) {
						logger.error("",e);
					}
					if (limitType.equals("1")) {
						company.setCompany(resultSet.getString("company") + ":0");
					} else if (limitType.equals("2")) {
						company.setCompany(resultSet.getString("company") + "more:2");
						sendFlag = 1;
					} else {
						company.setCompany(resultSet.getString("company") + "more:3");
						sendFlag = 1;
					}
					// company.setCompany(resultSet.getString("company"));
				}

				company.setCpip(resultSet.getString("cpip"));
				company.setGame(resultSet.getString("game"));
				company.setCompanytag(companytag);
				company.setAddate(resultSet.getString("addate"));
				company.setFlag(sendFlag);
			} else {
				// sql = "select id,company,cpip,game,addate,flag from companys
				// where companytag = '" + companytag.substring(0,6) + "'";

				String tempCompanyTag = "";
				if (companytag.length() >= 6) {
					sql = "select id,company,cpip,game,addate,flag from companys where companytag = '"
							+ companytag.substring(0, 6) + "' and provid = '" + provId + "'";
					tempCompanyTag = companytag.substring(0, 6);
				} else {
					sql = "select id,company,cpip,game,addate,flag from companys where companytag = '"
							+ companytag.substring(0, 5) + "' and provid = '" + provId + "'";
					tempCompanyTag = companytag.substring(0, 5);
				}
				logger.debug(sql);
				resultSet = mydb.executeQuery(sql);
				// preParedStatement = connection.prepareStatement(sql);
				// preParedStatement.setString(1, companytag.substring(0,6));
				// resultSet = preParedStatement.executeQuery();
				// company = new Companys();
				companytag = tempCompanyTag;// companytag.substring(0,6);
				if (resultSet.next()) {

					provSendSwitch = this.checkProvince(resultSet.getString("company"), cpn);
					company.setId(resultSet.getInt("id"));
					sendFlag = resultSet.getInt("flag");
					// System.out.println(resultSet.getInt("id"));
					// if(provSendSwitch == 1){
					if (provSendSwitch == 41 || provSendSwitch == 42 || provSendSwitch == 43 || provSendSwitch == 44) {
						// company.setCompany(resultSet.getString("company") +
						// "pf");
						// sendFlag=1;
						String game = companytag.substring(0, 4);
						boolean checkFlag = this.checkProvPfRound(resultSet.getString("company"), cpn, game);// 用户放量保业务。
						if (checkFlag) {
							company.setCompany(resultSet.getString("company") + ":0");
							sendFlag = 0;
							// sendFlag=1;
						} else {
							company.setCompany(resultSet.getString("company") + "pf:" + provSendSwitch);
							sendFlag = 1;
						}
					} else if (provSendSwitch == 2) {
						company.setCompany("tltd," + resultSet.getString("company"));
						companytag = companytag.substring(0, 4);
						sendFlag = 0;
					} else if (provSendSwitch == 3) {
						company.setCompany(resultSet.getString("company") + "gp:5");// 灰号码严格监控状态中，已不允许发送
						sendFlag = 1;
					} else if (provSendSwitch == 99) {// 用于白号码
						company.setCompany(resultSet.getString("company") + ":99");
						sendFlag = 0;
					} else {
						String limitType = "";
						LimitDayAndMonthByCpn limit = new LimitDayAndMonthByCpn();
						try {
							limitType = limit.limitByCpn(resultSet.getString("company"), cpn);
						} catch (Exception e) {
							logger.error("",e);
						}
						if (limitType.equals("1")) {
							company.setCompany(resultSet.getString("company") + ":0");
						} else if (limitType.equals("2")) {
							company.setCompany(resultSet.getString("company") + "more:2");
							sendFlag = 1;
						} else {
							company.setCompany(resultSet.getString("company") + "more:3");
							sendFlag = 1;
						}
						// company.setCompany(resultSet.getString("company"));
					}
					company.setCpip(resultSet.getString("cpip"));
					company.setGame(resultSet.getString("game"));
					company.setCompanytag(companytag);
					company.setAddate(resultSet.getString("addate"));
					company.setFlag(sendFlag);
				} else {
					//////////////
					sql = "select id,company,cpip,game,addate,flag from companys  where companytag = '"
							+ companytag.substring(0, 4) + "' and provid = '" + provId + "'";
					// preParedStatement = connection.prepareStatement(sql);
					// preParedStatement.setString(1,
					// companytag.substring(0,4));
					logger.debug(sql);
					resultSet = mydb.executeQuery(sql);
					company = new Companys();
					if (resultSet.next()) {

						provSendSwitch = this.checkProvince(resultSet.getString("company"), cpn);
						company.setId(resultSet.getInt("id"));
						sendFlag = resultSet.getInt("flag");
						// System.out.println(resultSet.getInt("id"));
						if (provSendSwitch == 41 || provSendSwitch == 42 || provSendSwitch == 43
								|| provSendSwitch == 44) {
							// company.setCompany(resultSet.getString("company")
							// + "pf");
							// sendFlag=1;
							String game = companytag.substring(0, 4);
							boolean checkFlag = this.checkProvPfRound(resultSet.getString("company"), cpn, game);// 用户放量保业务。
							if (checkFlag) {
								company.setCompany(resultSet.getString("company") + ":0");
								sendFlag = 0;
							} else {
								company.setCompany(resultSet.getString("company") + "pf:" + provSendSwitch);
								sendFlag = 1;
							}
						} else if (provSendSwitch == 2) {
							company.setCompany("tltd," + resultSet.getString("company"));
							sendFlag = 0;
						} else if (provSendSwitch == 3) {
							company.setCompany(resultSet.getString("company") + "gp:5");// 灰号码严格监控状态中，已不允许发送
							sendFlag = 1;
						} else if (provSendSwitch == 99) {// 用于白号码
							company.setCompany(resultSet.getString("company") + ":99");
							sendFlag = 0;
						} else {
							String limitType = "";
							LimitDayAndMonthByCpn limit = new LimitDayAndMonthByCpn();
							try {
								limitType = limit.limitByCpn(resultSet.getString("company"), cpn);
							} catch (Exception e) {
								logger.error("",e);
							}
							if (limitType.equals("1")) {
								company.setCompany(resultSet.getString("company") + ":0");
							} else if (limitType.equals("2")) {
								company.setCompany(resultSet.getString("company") + "more:2");
								sendFlag = 1;
							} else {
								company.setCompany(resultSet.getString("company") + "more:3");
								sendFlag = 1;
							}
							// company.setCompany(resultSet.getString("company"));
						}
						company.setCpip(resultSet.getString("cpip"));
						company.setGame(resultSet.getString("game"));
						company.setCompanytag(companytag.substring(0, 4));
						company.setAddate(resultSet.getString("addate"));
						company.setFlag(sendFlag);
					}
					//////////////
				}
			}
			////////////////////////////////////////// 用户查找默认指令的合作方
			// System.out.println("sql is:::::::::::::::::::::::::::::::::::" +
			////////////////////////////////////////// sql);
			// System.out.println("the company
			////////////////////////////////////////// is:::::::::::::::::::::::::::::::::::::::::::+++++++++++++++++++++"
			////////////////////////////////////////// +
			////////////////////////////////////////// (company.getCompany()));
			companytag = tmpCompanyTag;
			if (company.getCompany() == null) {
				///////////////////
				// System.out.println("the companytag
				/////////////////// is:--------------=============================="
				/////////////////// + companytag);
				// sql = "select id,company,cpip,game,addate,flag from companys
				/////////////////// where companytag = '" + companytag + "' and
				/////////////////// provid = '9999'";
				logger.debug(sql);
				resultSet = mydb.executeQuery(sql);

				if (resultSet.next()) {
					provSendSwitch = this.checkProvince(resultSet.getString("company"), cpn);
					company.setId(resultSet.getInt("id"));
					sendFlag = resultSet.getInt("flag");
					// System.out.println(resultSet.getInt("id"));
					if (provSendSwitch == 41 || provSendSwitch == 42 || provSendSwitch == 43 || provSendSwitch == 44) {
						/*
						 * company.setCompany(resultSet.getString("company") +
						 * "pf"); sendFlag=1;
						 */
						String game = companytag.substring(0, 4);
						boolean checkFlag = this.checkProvPfRound(resultSet.getString("company"), cpn, game);// 用户放量保业务。
						if (checkFlag) {
							company.setCompany(resultSet.getString("company") + ":0");
							sendFlag = 0;
						} else {
							company.setCompany(resultSet.getString("company") + "pf:" + provSendSwitch);
							sendFlag = 1;
						}
					} else if (provSendSwitch == 2) {
						company.setCompany("tltd," + resultSet.getString("company"));
						companytag = companytag.substring(0, 4);
						sendFlag = 0;
					} else if (provSendSwitch == 3) {
						company.setCompany(resultSet.getString("company") + "gp:5");// 灰号码严格监控状态中，已不允许发送
						sendFlag = 1;
					} else if (provSendSwitch == 99) {// 用于白号码
						company.setCompany(resultSet.getString("company") + ":99");
						sendFlag = 0;
					} else {
						String limitType = "";
						LimitDayAndMonthByCpn limit = new LimitDayAndMonthByCpn();
						try {
							limitType = limit.limitByCpn(resultSet.getString("company"), cpn);
						} catch (Exception e) {
							logger.error("",e);
						}
						if (limitType.equals("1")) {
							company.setCompany(resultSet.getString("company") + ":0");
						} else if (limitType.equals("2")) {
							company.setCompany(resultSet.getString("company") + "more:2");
							sendFlag = 1;
						} else {
							company.setCompany(resultSet.getString("company") + "more:3");
							sendFlag = 1;
						}
						// company.setCompany(resultSet.getString("company"));
					}

					company.setCpip(resultSet.getString("cpip"));
					company.setGame(resultSet.getString("game"));
					company.setCompanytag(companytag);
					company.setAddate(resultSet.getString("addate"));
					company.setFlag(sendFlag);
				} else {
					// sql = "select id,company,cpip,game,addate,flag from
					// companys where companytag = '" +
					// companytag.substring(0,6) + "'";

					String tempCompanyTag = "";
					if (companytag.length() >= 6) {
						sql = "select id,company,cpip,game,addate,flag from companys  where companytag = '"
								+ companytag.substring(0, 6) + "' and provid = '9999'";
						tempCompanyTag = companytag.substring(0, 6);
					} else {
						sql = "select id,company,cpip,game,addate,flag from companys  where companytag = '"
								+ companytag.substring(0, 5) + "' and provid = '9999'";
						tempCompanyTag = companytag.substring(0, 5);
					}
					logger.debug(sql);
					resultSet = mydb.executeQuery(sql);
					// preParedStatement = connection.prepareStatement(sql);
					// preParedStatement.setString(1,
					// companytag.substring(0,6));
					// resultSet = preParedStatement.executeQuery();
					// company = new Companys();
					companytag = tempCompanyTag;// companytag.substring(0,6);
					if (resultSet.next()) {

						provSendSwitch = this.checkProvince(resultSet.getString("company"), cpn);
						company.setId(resultSet.getInt("id"));
						sendFlag = resultSet.getInt("flag");
						// System.out.println(resultSet.getInt("id"));
						// if(provSendSwitch == 1){
						if (provSendSwitch == 41 || provSendSwitch == 42 || provSendSwitch == 43
								|| provSendSwitch == 44) {
							// company.setCompany(resultSet.getString("company")
							// + "pf");
							// sendFlag=1;
							String game = companytag.substring(0, 4);
							boolean checkFlag = this.checkProvPfRound(resultSet.getString("company"), cpn, game);// 用户放量保业务。
							if (checkFlag) {
								company.setCompany(resultSet.getString("company") + ":0");
								sendFlag = 0;
								// sendFlag=1;
							} else {
								company.setCompany(resultSet.getString("company") + "pf:" + provSendSwitch);
								sendFlag = 1;
							}
						} else if (provSendSwitch == 2) {
							company.setCompany("tltd," + resultSet.getString("company"));
							companytag = companytag.substring(0, 4);
							sendFlag = 0;
						} else if (provSendSwitch == 3) {
							company.setCompany(resultSet.getString("company") + "gp:5");// 灰号码严格监控状态中，已不允许发送
							sendFlag = 1;
						} else if (provSendSwitch == 99) {// 用于白号码
							company.setCompany(resultSet.getString("company") + ":99");
							sendFlag = 0;
						} else {
							String limitType = "";
							LimitDayAndMonthByCpn limit = new LimitDayAndMonthByCpn();
							try {
								limitType = limit.limitByCpn(resultSet.getString("company"), cpn);
							} catch (Exception e) {
								logger.error("",e);
							}
							if (limitType.equals("1")) {
								company.setCompany(resultSet.getString("company") + ":0");
							} else if (limitType.equals("2")) {
								company.setCompany(resultSet.getString("company") + "more:2");
								sendFlag = 1;
							} else {
								company.setCompany(resultSet.getString("company") + "more:3");
								sendFlag = 1;
							}
							// company.setCompany(resultSet.getString("company"));
						}
						company.setCpip(resultSet.getString("cpip"));
						company.setGame(resultSet.getString("game"));
						company.setCompanytag(companytag);
						company.setAddate(resultSet.getString("addate"));
						company.setFlag(sendFlag);
					} else {
						//////////////
						sql = "select id,company,cpip,game,addate,flag from companys  where companytag = '"
								+ companytag.substring(0, 4) + "' and provid = '9999'";
						// preParedStatement = connection.prepareStatement(sql);
						// preParedStatement.setString(1,
						// companytag.substring(0,4));
						logger.debug(sql);
						resultSet = mydb.executeQuery(sql);
						company = new Companys();
						if (resultSet.next()) {

							provSendSwitch = this.checkProvince(resultSet.getString("company"), cpn);
							company.setId(resultSet.getInt("id"));
							sendFlag = resultSet.getInt("flag");
							// System.out.println(resultSet.getInt("id"));
							if (provSendSwitch == 41 || provSendSwitch == 42 || provSendSwitch == 43
									|| provSendSwitch == 44) {
								// company.setCompany(resultSet.getString("company")
								// + "pf");
								// sendFlag=1;
								String game = companytag.substring(0, 4);
								boolean checkFlag = this.checkProvPfRound(resultSet.getString("company"), cpn, game);// 用户放量保业务。
								if (checkFlag) {
									company.setCompany(resultSet.getString("company") + ":0");
									sendFlag = 0;
								} else {
									company.setCompany(resultSet.getString("company") + "pf:" + provSendSwitch);
									sendFlag = 1;
								}
							} else if (provSendSwitch == 2) {
								company.setCompany("tltd," + resultSet.getString("company"));
								sendFlag = 0;
							} else if (provSendSwitch == 3) {
								company.setCompany(resultSet.getString("company") + "gp:5");// 灰号码严格监控状态中，已不允许发送
								sendFlag = 1;
							} else if (provSendSwitch == 99) {// 用于白号码
								company.setCompany(resultSet.getString("company") + ":99");
								sendFlag = 0;
							} else {
								String limitType = "";
								LimitDayAndMonthByCpn limit = new LimitDayAndMonthByCpn();
								try {
									limitType = limit.limitByCpn(resultSet.getString("company"), cpn);
								} catch (Exception e) {
									logger.error("",e);
								}
								if (limitType.equals("1")) {
									company.setCompany(resultSet.getString("company") + ":0");
								} else if (limitType.equals("2")) {
									company.setCompany(resultSet.getString("company") + "more:2");
									sendFlag = 1;
								} else {
									company.setCompany(resultSet.getString("company") + "more:3");
									sendFlag = 1;
								}
								// company.setCompany(resultSet.getString("company"));
							}
							company.setCpip(resultSet.getString("cpip"));
							company.setGame(resultSet.getString("game"));
							company.setCompanytag(companytag.substring(0, 4));
							company.setAddate(resultSet.getString("addate"));
							company.setFlag(sendFlag);
						}
					}
				}
			}
		} catch (SQLException e) {
			logger.error("",e);
		} finally {
			mydb.close();
		}
		// System.out.println("the company is:" + company.getCompany());
		return company;
	}

	public static CompanysDaoImpl getInstance() {
		return instance;
	}

	public static void setInstance(CompanysDaoImpl instance) {
		CompanysDaoImpl.instance = instance;
	}

	public int checkProvince(String company, String cpn) {
		ProvinceSendSwitchHandle pssh = new ProvinceSendSwitchHandle();
		pssh.setCpn(cpn);
		pssh.setCompany(company);
		pssh.checkSwitch();
		return pssh.getSendSwitch();
	}

	public boolean checkProvPfRound(String company, String cpn, String game) {
		ProvinceSendSwitchHandle pssh = new ProvinceSendSwitchHandle();
		pssh.setCpn(cpn);
		pssh.setCompany(company);
		pssh.setGame(game);
		pssh.checkProvincePfRound();
		return pssh.getProvGameFlag();
	}
}
