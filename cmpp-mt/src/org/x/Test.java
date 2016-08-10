package org.x;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.common.util.ConfigManager;
import org.common.util.ConnectionService;

import com.xiangtone.util.DBForLocal;
import com.xiangtone.util.DBForRead;

import comsd.CMPP;
import comsd.OutOfBoundsException;

public class Test {
	// private static Logger logger = Logger.getLogger(.class);
	private static Logger logger = Logger.getLogger(Test.class);

	public static void main(String[] args) {
		DBForLocal l=new DBForLocal();
		DBForRead r=new DBForRead();
		String sql="SELECT * FROM `tbl_base_users` WHERE id=1";
		try {
			ResultSet rs=l.executeQuery(sql);
			if(rs.next()){
				System.out.println("l£º"+rs.getString(1));
			}
			ResultSet rs2=r.executeQuery(sql);
			if(rs2.next()){
				System.out.println("r£º"+rs2.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ConfigManager.getConfigData("log.initialSize"));
		try {
			URL url=new URL("https://www.baidu.com");
			System.out.println(url);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		String strSql = null;
//		String strSql1 = null;
//		String strSql2 = null;
//		DBForLocal dbLocal = new DBForLocal();
//		DBForLog dbLog = new DBForLog();
//
//		int rs;
//		try {
//			strSql = "update aa set msgid ='x1' where id=0";
//			strSql1 = "update aa set msgid ='y1' where id=0";
//			strSql2 = "select msgid from aa";
//			dbLocal.executeUpdate(strSql);
//			dbLocal.executeUpdate(strSql1);
//			dbLog.executeQuery(strSql2);
//		} catch (Exception e) {
//			logger.error(strSql, e);
//		} finally {
//			dbLog.close();
//			dbLocal.close();
//		}
		byte[] b = { 1, 2 };
		System.out.println(Arrays.toString(b));

		try {

			// Socket socket = new Socket("12", 21);
			// logger.debug(socket);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		logger.error("e");
		logger.info("i");
		logger.debug("d");
		logger.warn("w");
		// logger.error("e");
		// logger.error("e");
		// logger.error("e");
		
	}
}
