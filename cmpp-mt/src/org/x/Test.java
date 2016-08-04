//package org.x;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.common.util.ConfigManager;
//import org.common.util.ConnectionService;
//
//import com.xiangtone.util.DBForLocal;
//import com.xiangtone.util.DBForLog;
//
//import comsd.commerceware.cmpp.CMPP;
//import comsd.commerceware.cmpp.OutOfBoundsException;
//
//public class Test {
//	// private static Logger logger = Logger.getLogger(.class);
//	private static Logger logger = Logger.getLogger(Test.class);
//
//	public static void main(String[] args) {
//		System.out.println(ConfigManager.getConfigData("log.initialSize"));
//
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
//		PreparedStatement ps = dbLocal.getPreparedStatement(strSql);
//		System.out.println(ps.toString() + "\n" + dbLocal.getPreparedStatement(strSql).toString());
//		String strSql3 = "select msgid from aa";
//		try {
//			dbLog.getPreparedStatement(strSql3).executeQuery();
//		} catch (SQLException e1) {
//			logger.error(strSql, e1);
//		} finally {
//			dbLog.close();
//		}
//		dbLog.close();
//		byte[] b = { 1, 2 };
//		System.out.println(Arrays.toString(b));
//
//		try {
//
//			// Socket socket = new Socket("12", 21);
//			// logger.debug(socket);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e, e);
//		}
//		logger.error("e");
//		logger.info("i");
//		logger.debug("d");
//		logger.warn("w");
//		// logger.error("e");
//		// logger.error("e");
//		// logger.error("e");
//	}
//}
