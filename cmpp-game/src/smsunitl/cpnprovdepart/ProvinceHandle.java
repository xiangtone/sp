package smsunitl.cpnprovdepart;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.smxysu3.dao.jdbcimpl.MoDataDaoImpl;
import com.xiangtone.util.DBForMT;

public class ProvinceHandle {
	private static Logger logger = Logger.getLogger(ProvinceHandle.class);
	private String miscId;
	private String postCode;
	private String cpn;
	// String dbIp = "xiangtone_dbip";
	// String dbName = "mts";
	// String dbUser = "xiangtone_dbuser";
	// String dbPwd = "xiangtone_dbpwd";
	// String dbPort = "xiangtone_dbport";
	public DBForMT mydb = null;

	public ProvinceHandle() {
		this.mydb = new DBForMT();
	}

	public void miscIdCheck() {
		String province = "0000";
		try {
			// Reader reader =
			// Resources.getResourceAsReader("SqlMapConfig.xml");
			// SqlMapClient sqlMap =
			// SqlMapClientBuilder.buildSqlMapClient(reader);
			String tempCpn = this.cpn.substring(0, 7);
			checkProvince(tempCpn);// (String)sqlMap.queryForObject("checkprovbycpn",
									// cpn);
			// this.setMiscId(province);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private void checkProvince(String tempCpn) {
		String strSql = "select miscid,postcode from mobile_miscid_cmcc where mobile='" + tempCpn + "'";
		String tempMiscId = "0000";
		try {
			logger.debug(strSql);
			ResultSet rs = this.mydb.executeQuery(strSql);
			if (rs.next()) {
				this.miscId = rs.getString("miscid");
				this.postCode = rs.getString("postcode");
			}
		} catch (Exception e) {
			logger.error(strSql, e);
			tempMiscId = "0000";
		} finally {
			this.mydb.close();
		}
	}

	public void setCpn(String cpn) {
		this.cpn = cpn;
	}

	public void setMiscId(String miscId) {
		this.miscId = miscId;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCpn() {
		return this.cpn;
	}

	public String getMiscId() {
		return this.miscId;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public static void main(String[] args) {
		ProvinceHandle ph = new ProvinceHandle();
		String cpn = "13859905479";
		ph.setCpn(cpn);
		ph.miscIdCheck();
		System.out.println(ph.getMiscId());
	}
}
