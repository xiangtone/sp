package com.smxysu3.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;

import com.smxysu3.dao.MoDataDao;
import com.smxysu3.model.MoData;
import com.smxysu3.util.JDBCServiceProvider;
import DBHandle.*;
import smsunitl.provsendwitch.ProvinceSendSwitchHandle;//add at 2010-04-23 用于对省份进行判断是否为可下发省份
import smsunitl.provsendwitch.WhiteCpnsOperate;
public class MoDataDaoImpl implements MoDataDao {
	
	private static MoDataDaoImpl instance = new MoDataDaoImpl();
	
	private MoDataDaoImpl(){}

	public void insert(MoData moData,String tableName) {
		String sql = "insert  into "+tableName+" "+"(id,company,game,userinput,cpn,msgid,linkid,addate,tocompstat,comprecstat,recp,mostate)" +" values (null,?,?,?,?,?,?,?,?,?,?,?)";
				//System.out.println("the sql is:" + sql);
				Connection connection = null;
				PreparedStatement prepareStatement = null;
				try{
					//添加了对省份过滤程序
					
					moData.setRecp("0");
					if(moData.getCompany().indexOf(",")!=-1){
							System.out.println("---------------存在扣量合作方");
							String stringarray[]=moData.getCompany().split(",");
							moData.setCompany(stringarray[0]);
							moData.setRecp(stringarray[1]);
							moData.setMoState(1);
					}
					else if(moData.getCompany().indexOf(":")!=-1){
						String stringarray[]=moData.getCompany().split(":");
						///////////////////////
						///////////////////////
						moData.setCompany(stringarray[0]);
						moData.setMoState(Integer.parseInt(stringarray[1]));
						if(stringarray[0].endsWith("pf")){
							moData.setTocompstat((short)1);
							moData.setComprecstat("-2");//-2为不允许上行省份
						}
					}
					//////////////////////////
					connection = JDBCServiceProvider.getConnection();
					//connection.setAutoCommit(false);
					prepareStatement = connection.prepareStatement(sql);
					prepareStatement.setString(1, moData.getCompany());
					prepareStatement.setString(2, moData.getGame());
					prepareStatement.setString(3, moData.getUserinput());
					prepareStatement.setString(4, moData.getCpn());
					prepareStatement.setString(5, moData.getMsgid());
					prepareStatement.setString(6, moData.getLinkid());
					prepareStatement.setString(7, moData.getAddate());
					prepareStatement.setInt(8, moData.getTocompstat());
					prepareStatement.setString(9, moData.getComprecstat());
					prepareStatement.setString(10,moData.getRecp());
					prepareStatement.setInt(11,moData.getMoState());
					prepareStatement.executeUpdate();
			}catch (SQLException e){
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}finally{
				try {
					//connection.setAutoCommit(true);
					prepareStatement.close();
					JDBCServiceProvider.colseConnection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	}

	public static MoDataDaoImpl getInstance() {
		return instance;
	}

	public int countByGameAndCpn(String serverId, String cpn,String tableName) {
		int total = 0;
		String dbIp = "xiangtone_dbip";
		String dbName = "mosdb";
		String dbMtName = "mts";
		String dbUser = "mosuser";
		String dbPwd = "mospwd";
		String dbPort = "xiangtone_dbport";
			
		WhiteCpnsOperate whiteCpnOperate = new WhiteCpnsOperate();
		whiteCpnOperate.setCpn(cpn);
		whiteCpnOperate.checkWhiteCpn();
		if(whiteCpnOperate.getWhiteCpnFlag()){
			total = 1;
			//System.out.println(cpn + "  whitecpns************************************");
		}
		else{
			//System.out.println(cpn + " not whitecpns::::::::::::::::::::::");
			String sql = "select count(*)  as count from  "+tableName+"  where cpn = '" + cpn + "'";
			
				MysqldbT mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
			try{
				ResultSet rs =  mydb.executeQuery(sql);
				if(rs.next()){
					total = rs.getInt("count");
				}
			/*
			Connection connection = null;
			PreparedStatement prepareStatement = null;
			ResultSet rs = null;
			
			connection = JDBCServiceProvider.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, serverId);
			prepareStatement.setString(2, cpn);
			rs = prepareStatement.executeQuery();
			
			while(rs.next()){
				total = rs.getInt(1);
			}
			*/
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try {
					mydb.close();
				
					//resultSet.close();
					//preParedStatement.close();
					//JDBCServiceProvider.colseConnection(connection);
				}catch(Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				try{
					mydb.close();
					//prepareStatement.close();
					//JDBCServiceProvider.colseConnection(connection);
				}catch(SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			}
		}
		return total;
	}

	

}
