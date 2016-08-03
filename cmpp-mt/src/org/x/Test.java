package org.x;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.common.util.ConfigManager;
import org.common.util.ConnectionService;

import com.xiangtone.util.DBForLocal;

import comsd.commerceware.cmpp.CMPP;
import comsd.commerceware.cmpp.OutOfBoundsException;

public class Test {
	// private static Logger logger = Logger.getLogger(.class);
	private static Logger logger = Logger.getLogger(Test.class);

	public static void main(String[] args) {
		System.out.println(ConfigManager.getConfigData("log.initialSize"));
		String strSql = null;
		String strSql1 = null;
		String strSql2 = null;
		DBForLocal db=new DBForLocal();
		int rs;
		try {
			strSql = "update tbl_base_users set name ='x1' where id=5";
			strSql1 = "update tbl_base_users set name ='y1' where id=6";
			strSql2 = "update tbl_base_users set name ='z1' where id=7";
			logger.debug(strSql);
			db.executeUpdate(strSql);
			db.executeUpdate(strSql1);
			db.executeUpdate(strSql2);
		} catch (Exception e) {
			logger.error(strSql, e);
		}finally {
			db.close();
		}		
		byte[] b = { 1, 2 };
		System.out.println(Arrays.toString(b));

		try {
			
//			Socket socket = new Socket("12", 21);
//			logger.debug(socket);
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
