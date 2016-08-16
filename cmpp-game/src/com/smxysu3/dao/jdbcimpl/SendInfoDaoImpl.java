package com.smxysu3.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.smxysu3.dao.SendInfoDao;
import com.smxysu3.model.SendInfo;
import com.xiangtone.util.DBForMO;

public class SendInfoDaoImpl implements SendInfoDao {
	private static Logger logger = Logger.getLogger(SendInfoDaoImpl.class);
	//µ¥Àý
	private static SendInfoDaoImpl instance = new SendInfoDaoImpl();
	
	private SendInfoDaoImpl(){}
	
	public SendInfo selectByCompany(String company) {
		String sql = "select * from sendinfo where company = ?";
		DBForMO db=new DBForMO();
		PreparedStatement preParedStatement = null;
		ResultSet resultSet=null;
		SendInfo sendInfo = null;
		try {
			preParedStatement = db.iniPreparedStatement(sql);
			preParedStatement.setString(1, company);
			resultSet = preParedStatement.executeQuery();
			sendInfo = new SendInfo();
			while(resultSet.next()){
			sendInfo.setId(resultSet.getInt("id"));
			sendInfo.setCompany(company);
			sendInfo.setMtMessage(resultSet.getString("mtmessage"));
			sendInfo.setSendTime(resultSet.getString("sendtime"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.close();
		}
		return sendInfo;
	}

	public static SendInfoDaoImpl getInstance() {
		return instance;
	}

}
