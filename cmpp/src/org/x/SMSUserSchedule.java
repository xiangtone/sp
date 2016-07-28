package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/

import java.sql.ResultSet;

import com.xiangtone.sql.Mysqldb;

/**
 * this Class operate gamelisttbl
 *
 */
public class SMSUserSchedule {
	/**
	*
	*/
	Mysqldb db;
	ResultSet rs = null;
	String strSql;
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

	/**
	 * get method
	 *
	 */

	public String getUSched_gameCode() {
		return gameCode;
	}

	public String getUSched_serverID() {
		return getServerIDbyServerName(getUSched_gameCode());
	}

	public String getUSched_spCode() {
		return spCode;
	}

	public String getUSched_actionCode() {
		return actionCode;
	}

	public int getUSched_vcpID() {
		return vcpID;
	}

	public String getUSched_corpID() {
		return corpID;
	}

	/**
	 *
	 *
	 */
	public SMSUserSchedule() {
		if (db == null) {
			db = new Mysqldb();
		}
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

			if (isItemExist(content)) {
				if (this.spCode.length() > 8) {
					this.spCode = this.spCode.substring(0, 8);
					this.spCode += "" + this.gameID;// change at 070201
					this.actionCode = "";
					this.gameCode = content;
					return;
				}

			}
			System.out.println(":::::::::::");
			System.out.println(":::::::::::");
			System.out.println(":::::::::::");
			System.out.println("game id:" + this.gameID);
			System.out.println("spcode:" + this.spCode);
			System.out.println(":::::::::::");
			System.out.println(":::::::::::");
			System.out.println(":::::::::::");
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
			System.out.println("----------------------");
			System.out.println("----------------------");
			System.out.println("----------------------");
			System.out.println("----------------------");
			System.out.println("this length is:" + len);
			System.out.println("----------------------");
			System.out.println("----------------------");
			String _strspcode = "";
			String _strgame = "";
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
				_strspcode = spCode.substring(0, SPCODE_LEN);
				/*
				 * String _strcorp = spCode.substring(SPCODE_LEN,SPCODE_LEN);
				 */
				_strgame = spCode.substring(SPCODE_LEN, SPCODE_LEN + GAME_LEN);
				/*
				 * if(!isCorpIDExist(_strcorp)) { this.corpID = "00"; this.spCode =
				 * _strspcode+this.corpID+_strgame; }
				 */
				if (!isGameIDExist(_strgame)) {
					this.gameCode = "ERROR";
					this.spCode = _strspcode;
				}
				System.out.println("***********this.vcpID:" + this.vcpID);
				System.out.println("***********this.vcpID:" + this.spCode);

				break;
			case SPCODE_LEN + GAME_LEN1:
				_strspcode = spCode.substring(0, SPCODE_LEN);
				/*
				 * String _strcorp = spCode.substring(SPCODE_LEN,SPCODE_LEN);
				 */
				_strgame = spCode.substring(SPCODE_LEN, SPCODE_LEN + GAME_LEN1);
				/*
				 * if(!isCorpIDExist(_strcorp)) { this.corpID = "00"; this.spCode =
				 * _strspcode+this.corpID+_strgame; }
				 */
				if (!isGameIDExist(_strgame)) {
					this.gameCode = "ERROR";
					this.spCode = _strspcode;
				}
				System.out.println("***********this.vcpID:" + this.vcpID);
				System.out.println("***********this.vcpID:" + this.spCode);

				break;
			case SPCODE_LEN + GAME_LEN2:
				_strspcode = spCode.substring(0, SPCODE_LEN);
				/*
				 * String _strcorp = spCode.substring(SPCODE_LEN,SPCODE_LEN);
				 */
				_strgame = spCode.substring(SPCODE_LEN, SPCODE_LEN + GAME_LEN2);
				/*
				 * if(!isCorpIDExist(_strcorp)) { this.corpID = "00"; this.spCode =
				 * _strspcode+this.corpID+_strgame; }
				 */
				if (!isGameIDExist(_strgame)) {
					this.gameCode = "ERROR";
					this.spCode = _strspcode;
				}
				System.out.println("***********this.vcpID:" + this.vcpID);
				System.out.println("***********this.vcpID:" + this.spCode);

				break;
			case SPCODE_LEN + GAME_LEN3:
				_strspcode = spCode.substring(0, SPCODE_LEN);
				/*
				 * String _strcorp = spCode.substring(SPCODE_LEN,SPCODE_LEN);
				 */
				_strgame = spCode.substring(SPCODE_LEN, SPCODE_LEN + GAME_LEN3);
				/*
				 * if(!isCorpIDExist(_strcorp)) { this.corpID = "00"; this.spCode =
				 * _strspcode+this.corpID+_strgame; }
				 */
				if (!isGameIDExist(_strgame)) {
					this.gameCode = "ERROR";
					this.spCode = _strspcode;
				}
				System.out.println("***********this.vcpID:" + this.vcpID);
				System.out.println("***********this.vcpID:" + this.spCode);

				break;
			case SPCODE_LEN + GAME_LEN4:
				_strspcode = spCode.substring(0, SPCODE_LEN);
				/*
				 * String _strcorp = spCode.substring(SPCODE_LEN,SPCODE_LEN);
				 */
				_strgame = spCode.substring(SPCODE_LEN, SPCODE_LEN + GAME_LEN4);
				/*
				 * if(!isCorpIDExist(_strcorp)) { this.corpID = "00"; this.spCode =
				 * _strspcode+this.corpID+_strgame; }
				 */
				if (!isGameIDExist(_strgame)) {
					this.gameCode = "ERROR";
					this.spCode = _strspcode;
				}
				System.out.println("***********this.vcpID:" + this.vcpID);
				System.out.println("***********this.vcpID:" + this.spCode);

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
			// System.out.println("***********this.vcpID:"+this.vcpID);
			// System.out.println("***********this.vcpID:"+this.spCode);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				db.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	*
	*
	*/
	private boolean isItemExist(String strGameCode) {

		boolean flag = false;
		try {
			strSql = "select * from sms_gamelist where gamename='" + strGameCode + "' and ismgid='" + ismgId + "'";
			rs = db.execQuery(strSql);
			if (rs.next()) {
				flag = true;
				this.gameID = new Integer(rs.getInt("gameid")).toString();
				this.gameCode = rs.getString("gamename");

				this.vcpID = rs.getInt("vcpid");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return flag;
	}

	/**
	*
	*
	*/
	private boolean isGameIDExist(String gameId) {
		boolean flag = false;
		try {
			strSql = "select * from sms_gamelist where gameid='" + gameId + "' and ismgid='" + ismgId + "'";
			System.out.println("strSql:" + strSql);
			rs = db.execQuery(strSql);
			if (rs.next()) {
				flag = true;
				this.gameCode = rs.getString("gamename");
				this.serverID = getServerIDbyServerName(this.gameCode);
				this.gameID = new Integer(rs.getString("gameid")).toString();
				// this.gameID = gameId;
				this.vcpID = rs.getInt("vcpid");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 如果存在合作伙伴id 就返回 不存在就使用默认
	 */
	private boolean isCorpIDExist(String id) {
		strSql = "select * from sms_company where corp_id='" + id + "'";
		try {
			rs = db.execQuery(strSql);
			if (!rs.next())
				return false;
		} catch (Exception e) {
			System.out.println(e.toString());
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
			ResultSet rs2 = db.execQuery(strSql);
			if (rs2.next()) {
				String _serverid = rs2.getString("serverid");
				return _serverid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.serverID;
	}

	public static void main(String[] args) {
		SMSUserSchedule sms = new SMSUserSchedule();
		sms.getUserDetail(args[0], args[1]);
		String game_code = sms.getUSched_gameCode();
		String sp_code = sms.getUSched_spCode();
		String action_code = sms.getUSched_actionCode();
		int vcp_id = sms.getUSched_vcpID();
		String corp_id = sms.getUSched_corpID();
		String server_id = sms.getUSched_serverID();

		System.out.println("game_code:" + game_code);
		System.out.println("sp_code:" + sp_code);
		System.out.println("action_code:" + action_code);
		System.out.println("vcp_id:" + vcp_id);
		System.out.println("corp_id:" + corp_id);
		System.out.println("serverid:" + server_id);
	}

}