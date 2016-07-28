package com.smxysu3.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.smxysu3.dao.SendInfoDao;
import com.smxysu3.model.SendInfo;
import com.smxysu3.util.JDBCServiceProvider;

public class SendInfoDaoImpl implements SendInfoDao {
	
	//µ¥Àý
	private static SendInfoDaoImpl instance = new SendInfoDaoImpl();
	
	private SendInfoDaoImpl(){}
	

	public SendInfo selectByCompany(String company) {
		String sql = "select * from sendinfo where company = ?";
		Connection connection = null;
		PreparedStatement preParedStatement = null;
		ResultSet resultSet = null;
		SendInfo sendInfo = null;
		try {
		connection = JDBCServiceProvider.getConnection();
			preParedStatement = connection.prepareStatement(sql);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				resultSet.close();
				preParedStatement.close();
				JDBCServiceProvider.colseConnection(connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sendInfo;
	}


	public static SendInfoDaoImpl getInstance() {
		return instance;
	}


	

}
