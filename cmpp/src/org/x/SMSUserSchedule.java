package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.xiangtone.util.DBForRead;

/**
 * this Class operate gamelisttbl
 *
 */
public class SMSUserSchedule {
	private static Logger logger = Logger.getLogger(SMSUserSchedule.class);
	private DBForRead db = new DBForRead();
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private String strSql;
	public static final int SPCODE_LEN = 8; // 基础号长度
	public static final int CORP_LEN = 2;// 媒体号长度
	public static final int GAME_LEN = 3;// 游戏号长度
	public static final int GAME_LEN1 = 2;// 游戏长度为2
	public static final int GAME_LEN2 = 1;// 游戏长度为1
	public static final int GAME_LEN3 = 4;// 游戏长度为4
	public static final int GAME_LEN4 = 5;// 游戏长度为5

	String gameCode = "";
	String spCode = "";
	String gameID = "";
	String corpID = "00";
	String actionCode = "";
	public String serverID = "1000";
	int vcpID = 1;
	String ismgId = "01";

	public String getUSchedGameCode() {
		return gameCode;
	}

	public String getUSchedServerID() {
		return getServerIDbyServerName(getUSchedGameCode());
	}

	public String getUSchedSpCode() {
		return spCode;
	}

	public String getUSchedActionCode() {
		return actionCode;
	}

	public int getUSchedVcpID() {
		return vcpID;
	}

	public String getUSchedCorpID() {
		return corpID;
	}

	public SMSUserSchedule() {
	}

	/**
	 * 取得最终结果 游戏名称
	 *
	 */
	public void getUserDetail(String spcode, String info) {
		try {
			this.serverID = "1000";
			this.vcpID = 1;
			this.spCode = spcode;
			String content = info.toUpperCase().trim();
			logger.debug("spcode:" + spcode + ", info:" + info);
			if (isItemExist(content)) {
				if (this.spCode.length() > 8) {
					this.spCode = this.spCode.substring(0, 8);
					this.spCode += "" + this.gameID;// change at 070201
					this.actionCode = "";
					this.gameCode = content;
					logger.debug("ItemExist");
					return;
				}

			}
			logger.debug("game id:" + this.gameID);
			logger.debug("spcode:" + this.spCode);
			int offset = content.indexOf(" ", 0);// 判断空格
			if (offset > 0) {
				gameCode = content.substring(0, offset);
				actionCode = content.substring(offset + 1);
			} else {
				gameCode = content;
				actionCode = "";
			}
			if (isItemExist(gameCode)) {
				this.gameCode = gameCode;
			} else {
				this.gameCode = "ERROR";
				this.actionCode = content;
			}

			int len = spCode.length();
			logger.debug("this length is:" + len);
			String strspcode = "";
			String strgame = "";
			switch (len) {
			case SPCODE_LEN:
				if (isItemExist(gameCode)) {
					this.gameCode = gameCode;
					this.gameID = this.gameID;
					this.vcpID = this.vcpID;
					this.corpID = "";
					this.spCode = this.spCode + this.corpID + this.gameID;
				} else {
					this.gameCode = "ERROR";
					this.vcpID = 1;
				}
				break;
			/*
			 * case SPCODE_LEN+CORP_LEN: String strspcode =
			 * spCode.substring(0,SPCODE_LEN); String strcorpId =
			 * spCode.substring(SPCODE_LEN,SPCODE_LEN+CORP_LEN);
			 * if(!isCorpIDExist(strcorpId)) { this.corpID = "00"; this.spCode =
			 * strspcode+this.corpID; } if(isItemExist(gameCode)) { this.spCode =
			 * this.spCode+this.gameID; } else { this.vcpID = 1; this.gameCode =
			 * "ERROR"; } break;
			 */
			// case SPCODE_LEN+CORP_LEN+GAME_LEN:

			case SPCODE_LEN + GAME_LEN:
				strspcode = spCode.substring(0, SPCODE_LEN);
				/*
				 * String _strcorp = spCode.substring(SPCODE_LEN,SPCODE_LEN);
				 */
				strgame = spCode.substring(SPCODE_LEN, SPCODE_LEN + GAME_LEN);
				/*
				 * if(!isCorpIDExist(_strcorp)) { this.corpID = "00"; this.spCode =
				 * _strspcode+this.corpID+_strgame; }
				 */
				if (!isGameIDExist(strgame)) {
					this.gameCode = "ERROR";
					this.spCode = strspcode;
				}
				logger.debug("***********this.vcpID:" + this.vcpID);
				logger.debug("***********this.vcpID:" + this.spCode);

				break;
			case SPCODE_LEN + GAME_LEN1:
				strspcode = spCode.substring(0, SPCODE_LEN);
				/*
				 * String _strcorp = spCode.substring(SPCODE_LEN,SPCODE_LEN);
				 */
				strgame = spCode.substring(SPCODE_LEN, SPCODE_LEN + GAME_LEN1);
				/*
				 * if(!isCorpIDExist(_strcorp)) { this.corpID = "00"; this.spCode =
				 * _strspcode+this.corpID+_strgame; }
				 */
				if (!isGameIDExist(strgame)) {
					this.gameCode = "ERROR";
					this.spCode = strspcode;
				}
				logger.debug("***********this.vcpID:" + this.vcpID);
				logger.debug("***********this.vcpID:" + this.spCode);

				break;
			case SPCODE_LEN + GAME_LEN2:
				strspcode = spCode.substring(0, SPCODE_LEN);
				/*
				 * String _strcorp = spCode.substring(SPCODE_LEN,SPCODE_LEN);
				 */
				strgame = spCode.substring(SPCODE_LEN, SPCODE_LEN + GAME_LEN2);
				/*
				 * if(!isCorpIDExist(_strcorp)) { this.corpID = "00"; this.spCode =
				 * _strspcode+this.corpID+_strgame; }
				 */
				if (!isGameIDExist(strgame)) {
					this.gameCode = "ERROR";
					this.spCode = strspcode;
				}
				logger.debug("***********this.vcpID:" + this.vcpID);
				logger.debug("***********this.vcpID:" + this.spCode);

				break;
			case SPCODE_LEN + GAME_LEN3:
				strspcode = spCode.substring(0, SPCODE_LEN);
				/*
				 * String _strcorp = spCode.substring(SPCODE_LEN,SPCODE_LEN);
				 */
				strgame = spCode.substring(SPCODE_LEN, SPCODE_LEN + GAME_LEN3);
				/*
				 * if(!isCorpIDExist(_strcorp)) { this.corpID = "00"; this.spCode =
				 * _strspcode+this.corpID+_strgame; }
				 */
				if (!isGameIDExist(strgame)) {
					this.gameCode = "ERROR";
					this.spCode = strspcode;
				}
				logger.debug("***********this.vcpID:" + this.vcpID);
				logger.debug("***********this.vcpID:" + this.spCode);

				break;
			case SPCODE_LEN + GAME_LEN4:
				strspcode = spCode.substring(0, SPCODE_LEN);
				/*
				 * String _strcorp = spCode.substring(SPCODE_LEN,SPCODE_LEN);
				 */
				strgame = spCode.substring(SPCODE_LEN, SPCODE_LEN + GAME_LEN4);
				/*
				 * if(!isCorpIDExist(_strcorp)) { this.corpID = "00"; this.spCode =
				 * _strspcode+this.corpID+_strgame; }
				 */
				if (!isGameIDExist(strgame)) {
					this.gameCode = "ERROR";
					this.spCode = strspcode;
				}
				logger.debug("***********this.vcpID:" + this.vcpID);
				logger.debug("***********this.vcpID:" + this.spCode);

				break;
			default:

				this.spCode = spCode.substring(0, 8);
				if (isItemExist(gameCode)) {
					this.gameCode = this.gameCode;
					this.corpID = "";
					this.spCode = this.spCode + this.corpID + this.gameID;
				} else {
					this.gameCode = "ERROR";
					this.vcpID = 1;
				}

				break;
			// logger.debug("***********this.vcpID:"+this.vcpID);
			// logger.debug("***********this.vcpID:"+this.spCode);

			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			db.close();
		}

	}

	private boolean isItemExist(String strGameCode) {
		boolean flag = false;
		logger.debug("flag:" + flag);
		try {
			strSql = "select * from sms_gamelist where gamename='" + strGameCode + "' and ismgid='" + ismgId + "'";
			logger.debug(strSql);
			rs = db.executeQuery(strSql);
			if (rs.next()) {
				flag = true;
				this.gameID = new Integer(rs.getInt("gameid")).toString();
				this.gameCode = rs.getString("gamename");
				this.vcpID = rs.getInt("vcpid");
				logger.debug("gameID:" + gameID + ", gameCode:" + gameCode + ", vcpID:" + vcpID);
			}
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
		}
		return flag;
	}

	private boolean isGameIDExist(String gameId) {
		boolean flag = false;
		try {
			strSql = "select * from sms_gamelist where gameid='" + gameId + "' and ismgid='" + ismgId + "'";
			logger.debug("strSql:" + strSql);
			rs = db.executeQuery(strSql);
			if (rs.next()) {
				flag = true;
				this.gameCode = rs.getString("gamename");
				this.gameID = new Integer(rs.getString("gameid")).toString();
				this.vcpID = rs.getInt("vcpid");
				this.serverID = getServerIDbyServerName(this.gameCode);
				// this.gameID = gameId;
			}
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
		}
		return flag;
	}

	/**
	 * 如果存在合作伙伴id 就返回 不存在就使用默认
	 */
	private boolean isCorpIDExist(String id) {
		strSql = "select * from sms_company where corp_id='" + id + "'";
		try {
			rs = db.executeQuery(strSql);
			if (!rs.next())
				return false;
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
		}
		this.corpID = id;
		return true;

	}

	/**
	 * 通过servername 得到serverid
	 *
	 */
	private String getServerIDbyServerName(String servername) {

		strSql = "select * from sms_cost where servername='" + servername + "' limit 1";
		try {
			rs = db.executeQuery(strSql);
			if (rs.next()) {
				String serverid = rs.getString("serverid");
				return serverid;
			}
		} catch (Exception e) {
			logger.error(strSql, e);
		} finally {
		}
		return this.serverID;
	}

	public static void main(String[] args) {
		SMSUserSchedule sms = new SMSUserSchedule();
		sms.getUserDetail(args[0], args[1]);
		String game_code = sms.getUSchedGameCode();
		String sp_code = sms.getUSchedSpCode();
		String action_code = sms.getUSchedActionCode();
		int vcp_id = sms.getUSchedVcpID();
		String corp_id = sms.getUSchedCorpID();
		String server_id = sms.getUSchedServerID();

		logger.debug("game_code:" + game_code);
		logger.debug("sp_code:" + sp_code);
		logger.debug("action_code:" + action_code);
		logger.debug("vcp_id:" + vcp_id);
		logger.debug("corp_id:" + corp_id);
		logger.debug("serverid:" + server_id);
	}

}