package com.smxysu3.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.smxysu3.dao.CompanyMtMsgDao;
import com.smxysu3.model.CompanyMtMsg;
import com.xiangtone.util.DBForWrite;
import com.xiangtone.util.DBForMO;
import com.xiangtone.util.DBForRead;

public class CompanyMtMsgDaoImpl implements CompanyMtMsgDao {
	private static Logger logger = Logger.getLogger(CompanyMtMsgDaoImpl.class);
	private static CompanyMtMsgDaoImpl instance = new CompanyMtMsgDaoImpl();

	private CompanyMtMsgDaoImpl() {
	}

	public CompanyMtMsg select(String company, String serverId) {
		// System.out.println("company is:" + company);
		// System.out.println("serverId is:" + serverId);
		// String sql = "select id,company,serverId,mtmsg,createTime from
		// companymtmsg where company = ? and serverId = ? order by rand() limit
		// 1";
		/*
		 * System.out.println("CompanyMtMsg sql is:" + sql); Connection
		 * connection = null; PreparedStatement preParedStatement = null;
		 * ResultSet resultSet = null; CompanyMtMsg companyMtMsg= new
		 * CompanyMtMsg(); try { connection =
		 * JDBCServiceProvider.getConnection(); preParedStatement =
		 * connection.prepareStatement(sql); preParedStatement.setString(1,
		 * company); preParedStatement.setString(2, serverId); resultSet =
		 * preParedStatement.executeQuery(); if(resultSet.next()){
		 * companyMtMsg.setId(resultSet.getInt("id"));
		 * companyMtMsg.setCompany(company); companyMtMsg.setServerId(serverId);
		 * companyMtMsg.setMtmsg(resultSet.getString("mtmsg"));
		 * companyMtMsg.setCreateTime(resultSet.getString("createTime")); }
		 */
		// String sql = "select id,company,serverId,mtmsg,createTime from
		// companymtmsg where company = '" + company + "' and serverId = '" +
		// serverId + "' order by rand() limit 1";
		/////////////////////////
		String sql = "";
		if (company.indexOf(",") != -1) {

			String stringarray[] = company.split(",");

			company = stringarray[1];
			sql = "select id,company,serverId,mtmsg,createTime from companymtmsg where company = '" + company
					+ "' and serverId  like '%" + serverId + "%' order by rand() limit 1";
		} else if (company.indexOf(":") != -1) {
			String stringarray[] = company.split(":");
			company = stringarray[0];
			sql = "select id,company,serverId,mtmsg,createTime from companymtmsg where company = '" + company
					+ "' and serverId  like '%" + serverId + "%' order by rand() limit 1";

		} else {
			sql = "select id,company,serverId,mtmsg,createTime from companymtmsg where company = '" + company
					+ "' and serverId = '" + serverId + "'  order by rand() limit 1";
		}
		logger.debug(sql);
		DBForMO mydb = new DBForMO();
		CompanyMtMsg companyMtMsg = new CompanyMtMsg();
		try {

			ResultSet resultSet = mydb.executeQuery(sql);
			if (resultSet.next()) {
				companyMtMsg.setId(resultSet.getInt("id"));
				companyMtMsg.setCompany(company);
				companyMtMsg.setServerId(serverId);
				companyMtMsg.setMtmsg(resultSet.getString("mtmsg"));
				companyMtMsg.setCreateTime(resultSet.getString("createTime"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(sql, e);
		} finally {
			mydb.close();
		}
		return companyMtMsg;
	}

	public static CompanyMtMsgDaoImpl getInstance() {
		return instance;
	}

	public void insert(CompanyMtMsg companyMtMsg) {
		String sql = "insert into companymtmsg (`id`,`company`,`serverId`,`mtmsg`,`createTime`)"
				+ " values (null,?,?,?,?)";
		DBForMO dbForLocal=new DBForMO();
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = dbForLocal.iniPreparedStatement(sql);
			prepareStatement.setString(1, companyMtMsg.getCompany());
			prepareStatement.setString(2, companyMtMsg.getServerId());
			prepareStatement.setString(3, companyMtMsg.getMtmsg());
			prepareStatement.setString(4, companyMtMsg.getCreateTime());
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbForLocal.close();
		}

	}

}
