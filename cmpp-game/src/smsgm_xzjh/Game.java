package smsgm_xzjh;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.smxysu3.manager.CompanyMtMsgManager;
import com.smxysu3.manager.CompanysManager;
import com.smxysu3.manager.MoDataManager;
import com.smxysu3.manager.SendInfoManager;
import com.smxysu3.manager.impl.CompanyMtMsgManagerImpl;
import com.smxysu3.manager.impl.CompanysManagerImpl;
import com.smxysu3.manager.impl.MoDataManagerImpl;
import com.smxysu3.manager.impl.SendInfoManagerImpl;
import com.smxysu3.model.Companys;
import com.smxysu3.model.MoData;
import com.xiangtone.util.ConfigManager;

import GameMtHandle.SmsMoMtHandle;

public class Game {
	String ismgid = "01";
	String serverId = "1005";
	String retMsg = "";
	private String company = "";
	private CompanysManager companysManager = CompanysManagerImpl.getInstance();
	// 默认jdbc实现
	private MoDataManager moDataManager = MoDataManagerImpl.getInstance();
	// 默认jdbc实现
	private SendInfoManager sendInfoManager = SendInfoManagerImpl.getInstance();
	// 默认jdbc实现
	private CompanyMtMsgManager companyMtMsgManager = CompanyMtMsgManagerImpl.getInstance();

	public int execMain(String cpn, String itemaction, String ismgid, String linkid, Integer cpnType, String msgId,
			Vector cpnSet, Vector cpnGet, Vector msgSet, Vector costSet, Vector ismgSet, Vector linkidSet, Vector cpntypeSet,
			Vector msgIdSet) {
		String userInput = itemaction;
		try { // 可能要编码
			/////////////////////
			// String userInput = itemaction;
			String tableName = "tempcompany_mo";// GameMtHandle.FormatSysTime.getCurrentTime("yyyyMMdd")
																					// + "company_mo";
			if (linkid == null) {
				linkid = "";
			}
			smsProcess(cpn, itemaction, linkid, msgId, tableName);
			retMsg = getRetMsg();

			if (!retMsg.equals("")) {
				insertMt(cpn, userInput, retMsg, linkid);
				msgAdd(cpn, retMsg, serverId, linkid, msgId, cpnType, cpnSet, cpnGet, msgSet, costSet, ismgSet, linkidSet,
						cpntypeSet, msgIdSet);
			}
			return 0;
			/////////////////////
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

	///////////////////////////// change at 100222
	public CompanysManager getCompanysManager() {
		return companysManager;
	}

	public MoDataManager getMoDataManager() {
		return moDataManager;
	}

	public String getRetMsg() {
		return this.retMsg;
	}

	public SendInfoManager getSendInfoManager() {
		return sendInfoManager;
	}

	public String getServerid() {
		return serverId;
	}

	private void insertMo(String cpn, String itemaction, String linkid, String msgId, Companys company,
			String tableName) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = fmt.format(new Date());
		MoData mo = new MoData();
		mo.setCpn(cpn);
		mo.setLinkid(linkid);
		mo.setUserinput(itemaction);
		mo.setGame(serverId);
		mo.setAddate(date);
		mo.setCompany(company.getCompany());
		mo.setMsgid(msgId);
		mo.setTocompstat((short) 0);
		mo.setComprecstat("-1");
		moDataManager.add(mo, tableName);
	}

	// 用于下行入库
	private void insertMt(String cpn, String userInput, String retMsg, String linkid) {
		String dbIp = "xiangtone_dbip";
		String dbMoName = "mos";
		String dbMtName = "mts";
		String dbUser = "joyfulUser";
		String dbPwd = "joyfulPwd";
		String dbPort = "xiangtone_dbport";

		String mtCompany = "";
		SmsMoMtHandle smsMtHandle = new SmsMoMtHandle(dbIp, dbMtName, dbUser, dbPwd, dbPort);
		try {
			if (userInput.length() > 1) {
				mtCompany = serverId + userInput.substring(0, 2);
			} else {
				mtCompany = serverId + userInput;
			}
			smsMtHandle.compMt.setCompany(this.company);
			smsMtHandle.compMt.setGame(serverId);
			smsMtHandle.compMt.setCpn(cpn);
			smsMtHandle.compMt.setLinkId(linkid);
			smsMtHandle.compMt.setContent(retMsg);
			smsMtHandle.compMt.setMsgId("");
			smsMtHandle.smsCompanyMtHandle();
			smsMtHandle.mydb.close();
		} catch (Exception e) {
			try {
				smsMtHandle.mydb.close();
			} catch (Exception e1) {
				System.out.println("................");
				e1.printStackTrace();
			}

		} finally {
			try {
				smsMtHandle.mydb.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	private boolean isSend(Companys company) {
		boolean flag = false;
		if (company.getFlag() == 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;

	}

	private void smsProcess(String cpn, String itemaction, String linkid, String msgId, String tableName) {

		Companys company = companysManager.select(serverId + itemaction, cpn);
		System.out.println("the company is:" + company.getCompany());
		// company.setGame(serverId);
		this.company = company.getCompany();
		int moTotal = countTotalMo(cpn, serverId, tableName);
		int moLimit = Integer
				.parseInt((String) ConfigManager.getInstance().getConfigData("molimit", " molimit not found!"));// 10;//上行的上限
		// System.out.println("total num is:" + moTotal);
		if (moTotal < moLimit) {
			if (isSend(company)) {
				checkRetMsg(company);
			}
		} else {
			company.setCompany(company.getCompany() + "more");
		}
		insertMo(cpn, itemaction, linkid, msgId, company, tableName);
	}

	public void checkRetMsg(Companys mtcompany) {
		this.retMsg = companyMtMsgManager.selectByCompanyAndServerId(this.company, mtcompany.getCompanytag()).getMtmsg();
	}

	// 用于统计出每用户每天上行量
	private int countTotalMo(String cpn, String serverId, String tableName) {
		tableName = GameMtHandle.FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mo";
		return moDataManager.countByServerIdAndCpn(serverId, cpn, tableName);
	}

	public void setCompanysManager(CompanysManager companysManager) {
		this.companysManager = companysManager;
	}

	public void setMoDataManager(MoDataManager moDataManager) {
		this.moDataManager = moDataManager;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public void setSendInfoManager(SendInfoManager sendInfoManager) {
		this.sendInfoManager = sendInfoManager;
	}

	public void setServerid(String serverId) {
		this.serverId = serverId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public CompanyMtMsgManager getCompanyMtMsgManager() {
		return companyMtMsgManager;
	}

	public void setCompanyMtMsgManager(CompanyMtMsgManager companyMtMsgManager) {
		this.companyMtMsgManager = companyMtMsgManager;
	}

	private void processCount(String serverId, String cpn, String tableName, int limit) {
		int total = countTotalByNow(serverId, cpn, tableName);
		if (total > limit) {
			company += "more";
		}
	}

	private int countTotalByNow(String serverId, String cpn, String tableName) {

		return moDataManager.countByServerIdAndCpn(serverId, cpn, tableName);
	}

	public void msgAdd(String cpn, String msg, String serverid, String linkId, String msgId, int cpnType, Vector cpnSet,
			Vector cpnGet, Vector msgSet, Vector costSet, Vector ismgSet, Vector linkidSet, Vector cpntypeSet,
			Vector msgIdSet) {
		cpnSet.add(cpn);
		cpnGet.add(cpn);
		msgSet.add(msg);
		costSet.add(serverid);
		linkidSet.add(linkId);
		ismgSet.add(ismgid);
		msgIdSet.add(msgId);
		cpntypeSet.add(cpnType);
	}
}
