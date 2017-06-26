package org.x;

import java.io.File;
import java.io.FileInputStream;
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
import java.util.Properties;

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
	private void init() {
		try {
			Properties Props = new Properties();
			File f = new File("config.properties");

			FileInputStream ins = new FileInputStream(f);
			if (ins != null) {
				Props = new Properties();
				Props.load(ins);
			} else {
				// String strDate = saveLog.formatDate();
				// saveLog.error(strDate+"--API_GET--Can not read the properties
				// file");
				logger.debug("app_name.properties is not exist");
			}
		} catch (Exception e) {
//			logger.error("", e);
			e.printStackTrace();
		}

		return;
	}
	public static void main(String[] args) {
		new Test().init();
		Socket s=new Socket();
		try {
			s.close();
			s=null;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		DBForLocal l=new DBForLocal();
//		DBForRead r=new DBForRead();
//		String sql="SELECT * FROM `tbl_base_users` WHERE id=1";
//		try {
//			ResultSet rs=l.executeQuery(sql);
//			if(rs.next()){
//				System.out.println("l��"+rs.getString(1));
//			}
//			ResultSet rs2=r.executeQuery(sql);
//			if(rs2.next()){
//				System.out.println("r��"+rs2.getString(1));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(ConfigManager.getConfigData("log.initialSize"));
//		try {
//			URL url=new URL("https://www.baidu.com");
//			System.out.println(url);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
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