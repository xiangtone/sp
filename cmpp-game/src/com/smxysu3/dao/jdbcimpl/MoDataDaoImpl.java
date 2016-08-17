package com.smxysu3.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.smxysu3.dao.MoDataDao;
import com.smxysu3.model.MoData;
import com.xiangtone.util.DBForMO;
import com.xiangtone.util.DBForRead;

import smsunitl.provsendwitch.ProvinceSendSwitchHandle;//add at 2010-04-23 用于对省份进行判断是否为可下发省份
import smsunitl.provsendwitch.WhiteCpnsOperate;

public class MoDataDaoImpl implements MoDataDao {

	private static Logger logger = Logger.getLogger(MoDataDaoImpl.class);

	private static MoDataDaoImpl instance = new MoDataDaoImpl();

	private MoDataDaoImpl() {
	}

	public void insert(MoData moData, String tableName) {
		String sql = "insert into " + tableName + " "
				+ "(id,company,game,userinput,cpn,msgid,linkid,addate,tocompstat,comprecstat,recp,mostate)"
				+ " values (null,?,?,?,?,?,?,?,?,?,?,?)";
		logger.debug(sql);
		DBForMO db=new DBForMO();
		PreparedStatement prepareStatement = null;
		try {
			// 添加了对省份过滤程序

			moData.setRecp("0");
			if (moData.getCompany().indexOf(",") != -1) {
				System.out.println("---------------存在扣量合作方");
				String stringarray[] = moData.getCompany().split(",");
				moData.setCompany(stringarray[0]);
				moData.setRecp(stringarray[1]);
				moData.setMoState(1);
			} else if (moData.getCompany().indexOf(":") != -1) {
				String stringarray[] = moData.getCompany().split(":");
				///////////////////////
				///////////////////////
				moData.setCompany(stringarray[0]);
				moData.setMoState(Integer.parseInt(stringarray[1]));
				if (stringarray[0].endsWith("pf")) {
					moData.setTocompstat((short) 1);
					moData.setComprecstat("-2");// -2为不允许上行省份
				}
			}
			//////////////////////////
//			connection = JDBCServiceProvider.getConnection();
			// connection.setAutoCommit(false);
			prepareStatement = db.iniPreparedStatement(sql);;
			prepareStatement.setString(1, moData.getCompany());
			prepareStatement.setString(2, moData.getGame());
			prepareStatement.setString(3, moData.getUserinput());
			prepareStatement.setString(4, moData.getCpn());
			prepareStatement.setString(5, moData.getMsgid());
			prepareStatement.setString(6, moData.getLinkid());
			prepareStatement.setString(7, moData.getAddate());
			prepareStatement.setInt(8, moData.getTocompstat());
			prepareStatement.setString(9, moData.getComprecstat());
			prepareStatement.setString(10, moData.getRecp());
			prepareStatement.setInt(11, moData.getMoState());
			logger.debug("Company" + moData.getCompany() + ", Game" + moData.getGame() + ", Userinput"
					+ moData.getUserinput() + ", Cpn" + moData.getCpn() + ", Msgid" + moData.getMsgid() + ", Linkid"
					+ moData.getLinkid() + ", Addate" + moData.getAddate() + ", Tocompstat" + moData.getTocompstat()
					+ ", Comprecstat" + moData.getComprecstat() + ", Recp" + moData.getRecp() + ", MoState"
					+ moData.getMoState());
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			db.close();
		}
	}

	public static MoDataDaoImpl getInstance() {
		return instance;
	}

	public int countByGameAndCpn(String serverId, String cpn, String tableName) {
		int total = 0;
//		String dbIp = "xiangtone_dbip";
//		String dbName = "mosdb";
//		String dbMtName = "mts";
//		String dbUser = "mosuser";
//		String dbPwd = "mospwd";
//		String dbPort = "xiangtone_dbport";

		WhiteCpnsOperate whiteCpnOperate = new WhiteCpnsOperate();
		whiteCpnOperate.setCpn(cpn);
		whiteCpnOperate.checkWhiteCpn();
		if (whiteCpnOperate.getWhiteCpnFlag()) {
			total = 1;
		} else {
			String sql = "select count(*) as count from  " + tableName + "  where cpn = '" + cpn + "'";

			DBForMO mydb = new DBForMO();
			try {
				logger.debug(sql);
				ResultSet rs = mydb.executeQuery(sql);
				if (rs.next()) {
					total = rs.getInt("count");
				}
			} catch (SQLException e) {
				logger.error("", e);
			} finally {
				mydb.close();
			}
		}
		return total;
	}

}
