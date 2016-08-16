package smsunitl.companylimit;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.xiangtone.util.DBForMT;

import smsunitl.cpnprovdepart.ProvinceHandle;

public class LimitDayAndMonthByCpn {
	private static Logger logger = Logger.getLogger(LimitDayAndMonthByCpn.class);
	// String dbIp = "xiangtone_dbip";
	// String dbName = "mts";
	// String dbUser = "xiangtone_dbuser";
	// String dbPwd = "xiangtone_dbpwd";
	// String dbPort = "xiangtone_dbport";
	private DBForMT mydb;

	// private static LimitDayAndMonthByCpn singleton = new
	// LimitDayAndMonthByCpn();

	public LimitDayAndMonthByCpn() {
		this.mydb = new DBForMT();
	}

	/**
	 * �����ֻ����� �ͺ�������ѯ�Ƿ񳬳�����
	 * 
	 * @return
	 * @throws Exception
	 */
	public String limitByCpn(String cp, String phone) throws Exception {
		String flag = "";
		int num = 0;
		int limitNumByDay = 0;
		int limitNumByMonth = 0;
		int succNum = 0;
		String province = "0000";
		try {
			/*
			 * String strSql = "select id from whitecpn where cpn='" + phone +
			 * "'"; ResultSet rs= this.mydb.executeQuery(strSql); if(rs.next()){
			 * flag="1"; return flag; }
			 */
			long start = System.currentTimeMillis(); // ��ȡ���ʱ��
			// �ȸ����ֻ������ѯʡ��
			String str0 = "select miscid from mobile_miscid_cmcc where mobile='" + phone.substring(0, 7) + "'";
			logger.debug(str0);
			ResultSet rs0 = this.mydb.executeQuery(str0);
			while (rs0.next()) {
				province = rs0.getString("miscid");
			}
			long end = System.currentTimeMillis(); // ��ȡ���н���ʱ��
			// logger.debug("�����ֻ��������ʡ�ݳ�������ʱ�䣺 "+(end-start)+"ms");
			long start3 = System.currentTimeMillis(); // ��ȡ���ʱ��
			// �����ֻ����룬������ ��ѯ��ǰ���û���
			String table = DateUtil.getCurDate("yyyyMMdd") + "company_mt";
			String str = "select count(distinct linkid) as num from " + table + " where cpn='" + phone
					+ "' and company='" + cp + "' and sendstate='DELIVRD'";
			 logger.debug(str);
			ResultSet rs = this.mydb.executeQuery(str);
			if (rs.next()) {
				num = rs.getInt("num");
			}
			long end3 = System.currentTimeMillis(); // ��ȡ���н���ʱ��
			// logger.debug("�����ֻ����룬������ ��ѯ��ǰ���û����� "+(end3-start3)+"ms");

			long start1 = System.currentTimeMillis(); // ��ȡ���ʱ��

			// �ж��Ƿ���� ������+++ʡ��
			String str2 = "SELECT limitNum as limitNumByDay  FROM limitdaycp where cpId='" + cp + "' and province='"
					+ province + "'";
			logger.debug(str2);
			ResultSet rs2 = this.mydb.executeQuery(str2);

			if (rs2.next()) {
				limitNumByDay = rs2.getInt("limitNumByDay");
			} else {
				// //�ж��Ƿ���� +++ʡ��
				// String str9="SELECT limitNum as limitNumByDay FROM limitdaycp
				// where cpId='' and province='"+province+"'";
				// ResultSet rs9= this.mydb.executeQuery(str9);
				// if(rs9.next()) {
				// while(rs9.next()){
				// limitNumByDay=rs9.getInt("limitNumByDay");
				// }
				// }else{
				String str8 = "SELECT limitNum as limitNumByDay  FROM  limitdaycp where  cpId='" + cp
						+ "' and province = '' ";
				 logger.debug(str8);
				ResultSet rs8 = this.mydb.executeQuery(str8);
				if (rs8.next()) {
					limitNumByDay = rs8.getInt("limitNumByDay");
				} else {
					// ��ѯ�����������ƣ��ٴβ�ѯĬ��ֵ
					if (limitNumByDay == 0) {
						String str7 = "SELECT limitNum as limitNumByDay  FROM limitdaycp where cpId='default' ";
						logger.debug(str7);
						ResultSet rs7 = this.mydb.executeQuery(str7);
						if (rs7.next()) {
							limitNumByDay = rs7.getInt("limitNumByDay");
						}
					}
					long end1 = System.currentTimeMillis(); // ��ȡ���н���ʱ��
					logger.debug("-----------��������ʱ�䣺 " + (end1 - start1) + "ms");
				}
			}
			logger.debug("day limit" + num + ":" + limitNumByDay);
			if (num >= limitNumByDay) {
				// �˾������Ѿ����� ֱ�ӷ��ز����·�
				flag = "2";
			} else {
				String str3 = "select succNum from month_succ_cpn_num where cpn='" + phone + "'";
				logger.debug(str3);
				ResultSet rs3 = this.mydb.executeQuery(str3);
				while (rs3.next()) {
					succNum = rs3.getInt("succNum");
				}
				long start2 = System.currentTimeMillis(); // ��ȡ���ʱ��

				// �ж��Ƿ���� ������+++ʡ��
				String str12 = "SELECT limitNum as limitNumByMonth  FROM limitmonthcp where cpId='" + cp
						+ "' and province='" + province + "'";
				logger.debug(str12);
				ResultSet rs12 = this.mydb.executeQuery(str12);
				if (rs12.next()) {
					limitNumByMonth = rs12.getInt("limitNumByMonth");
				} else {
					// //�ж��Ƿ���� +++ʡ��
					// String str9="SELECT limitNum as limitNumByMonth FROM
					// limitmonthcp where cpId='' and province='"+province+"'";
					// ResultSet rs9= this.mydb.executeQuery(str9);
					// if(rs9.next()) {
					// while(rs9.next()){
					// limitNumByMonth=rs9.getInt("limitNumByMonth");
					// }
					// }else{
					String str8 = "SELECT limitNum as limitNumByMonth  FROM  limitmonthcp where  cpId='" + cp
							+ "' and province=''";
					logger.debug(str8);
					ResultSet rs8 = this.mydb.executeQuery(str8);
					if (rs8.next()) {
						limitNumByMonth = rs8.getInt("limitNumByMonth");
					} else {
						// ��ѯ�����������ƣ��ٴβ�ѯĬ��ֵ
						// logger.debug("the limit month is:" +
						// limitNumByMonth);
						if (limitNumByMonth == 0) {
							String str7 = "SELECT limitNum as limitNumByMonth  FROM limitmonthcp where cpId='default' ";
							logger.debug(str7);
							ResultSet rs7 = this.mydb.executeQuery(str7);
							if (rs7.next()) {
								limitNumByMonth = rs7.getInt("limitNumByMonth");
							}
						}
					}
					long end2 = System.currentTimeMillis(); // ��ȡ���н���ʱ��
					logger.debug("-----------��������ʱ�䣺 " + (end2 - start2) + "ms");
				}
				logger.debug("month limit" + succNum + ":" + limitNumByMonth);
				if (succNum >= limitNumByMonth) {
					// �˾������Ѿ����� ֱ�ӷ��ز����·�
					flag = "3";
				} else {
					flag = "1";
				}
			}
		} catch (Exception e) {
			logger.error("",e);
		} finally {
			mydb.close();
		}
		return flag;
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 3; i++) {

			long start = System.currentTimeMillis(); // ��ȡ���ʱ��
			LimitDayAndMonthByCpn cpn = new LimitDayAndMonthByCpn();
			String a = cpn.limitByCpn("zmrj", "13589905479");
			long end = System.currentTimeMillis(); // ��ȡ���н���ʱ��
			logger.debug(a);
			logger.debug("��" + i + "��������ʱ�䣺 " + (end - start) + "ms");
		}
	}
}
