package tableoperate;
/*
 * 用于对旧的表进行更名同时创建新的表
 */
import java.sql.*;
import DBHandle.MysqldbT;
public class TableOperate {
	private String tableName = "";
	
			String dbIp = "xiangtone_dbip";
			String dbName = "xiangtone_platdbname";
			String dbUser = "xiangtone_dbuser";
			String dbPwd = "xiangtone_dbpwd";
			String dbPort = "xiangtone_dbport";
	MysqldbT mydb;
	public void connectDb(){
		this.mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
	}
	//对表进行更名
	public void reNameTableName(){
		System.out.println("db name is:" + this.dbName);
		String newName = this.tableName + FormatSysTime.getCurrentTime("yyyyMMdd");
		//RENAME TABLE `smsgm_qw_mztd`.`mz_prize2` TO `smsgm_qw_mztd`.`mz_prize` ;

		String strSql = "RENAME TABLE " + this.tableName + " To " + newName;
		System.out.println(strSql);
		this.mydb.executeQuery(strSql);
		
	}
	//创建表
	public void createTable(){
		//mydb = new Mysqldb(this.dbName);
		System.out.println(this.mydb);
		String strSql = "create table " + this.tableName + "(";
		strSql += "`id` int(11) NOT NULL auto_increment,";
		strSql += "`ismgid` char(2) NOT NULL default '',";
		strSql += "`msg_id` char(20) NOT NULL default '0',";
		strSql += "`linkid` char(20) NOT NULL default '',";
		strSql += "`spcode` char(20) NOT NULL default '',";
		strSql += "`dest_cpn` char(15) NOT NULL default '',";
		strSql += "`src_cpn` char(15) NOT NULL default '',";
		strSql += "`submit_time` datetime NOT NULL default '0000-00-00 00:00:00',";
		strSql += "`done_time` datetime NOT NULL default '0000-00-00 00:00:00',";
		strSql += "`stat` tinyint(4) NOT NULL default '0',";
		strSql += "`stat_msg` char(10) NOT NULL default '',";
		strSql += "UNIQUE KEY `id` (`id`),";
		strSql += "INDEX (`msg_id`),";
		strSql += "INDEX (`linkid`)";
		strSql += ");";
		this.mydb.executeUpdate(strSql);
	}
	public void dbClose(){
		try{
			this.mydb.close();
		}catch(Exception e){
			try{
				this.mydb.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally{
			try{
				this.mydb.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
	}
	public void setDbName(String dbName){
		this.dbName = dbName;
	}
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public static void main(String[] args){
		
		TableOperate tableRename = new TableOperate();
		tableRename.setDbName("sms_platform");
		tableRename.setTableName("sms_tempreportlog");
		tableRename.connectDb();
		System.out.println("start to rename table" + tableRename.tableName);
		tableRename.reNameTableName();
		System.out.println("start to create new table:" + tableRename.tableName);
		tableRename.createTable();
		System.out.println("operate finished");
	}
}
