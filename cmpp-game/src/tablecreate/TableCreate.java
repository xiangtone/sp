package tablecreate;
import java.sql.*;
import java.util.*;
public class TableCreate {
	public String db = "";
	public static void main(String[] args){
		
		//System.out.println(month);
		//String tableName = FormatSysTime.getCurrentTime("yyyyMMdd")+"company_mo";



		//
		/*
		int month = Integer.parseInt(args[0]);
		String createTable = args[1];
		TableCreate tc = new TableCreate();
		if(createTable.equals("mo")){
			tc.createMos(month);
		}
		else{
			tc.createMts(month);
		}
		
		*/
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入要操作的数据库");
		String opeDb = scanner.next();
		
		System.out.println("请输入月份:");
		int month = scanner.nextInt();//Integer.parseInt(args[0]);
		System.out.println("请输入要创建的表类别(mo为上行、mt为下行、addrow为增加字段、addindex为增加索引)");
		
		String createTable = scanner.next();//args[1];
		TableCreate tc = new TableCreate();
		if(createTable.equals("mo")){
			tc.createMos(month);
		}
		else if(createTable.equals("addrow")){
			System.out.println("请输入要添加的字段:");
			String rowName = scanner.next();
			tc.setDb(opeDb);
			tc.addRow(month, rowName);
		}
		else if(createTable.equals("addindex")){
			System.out.println("请输入要添加索引的字段:");
			String rowName = scanner.next();
			tc.setDb(opeDb);
			tc.addIndex(month, rowName);
		}
		else{
			tc.createMts(month);
		}
		
	}
	public void createMts(int cmonth){
		/*
		CREATE TABLE `company_mt` (
  `id` int(11) NOT NULL auto_increment,
  `company` varchar(10) NOT NULL default '',
  `game` varchar(10) NOT NULL default '',
  `content` varchar(100) NOT NULL default '',
  `cpn` varchar(15) NOT NULL default '',
  `linkid` varchar(20) NOT NULL default '',
  `msgid` varchar(20) NOT NULL default '',
  `addate` datetime default NULL,
  `sendstate` tinyint(4) NOT NULL default '-3',
  `comprecstat` varchar(10) NOT NULL default '',
  UNIQUE KEY `id` (`id`)
) 
		*/	
		String strSql = "";
		Calendar c = Calendar.getInstance();
		int month = cmonth;//c.get(Calendar.MONTH)+1;
		int year = 2011;//c.get(Calendar.YEAR);
		System.out.println(year);
		String monthStr = "";
		if(month < 10){
			monthStr = "0"+month;
		}
		else{
			monthStr = "" + month;	
		}
		Mysqldb mydb = new Mysqldb("smscompanymts");
		String tableName = "";
		String dayStr = "";
		for(int i = 1;i <= 31;i++){
			if(i < 10){
				dayStr = "0" + i;
			}
			else{
					dayStr = "" + i;
			}
			//submit_linkid 
			//submitmsgid 
			tableName = year+monthStr+dayStr + "company_mt";
			strSql = "CREATE TABLE " + tableName + "(";
			strSql += "`id` int(11) NOT NULL auto_increment,";
			strSql += "`company` varchar(10) NOT NULL default '',";
			strSql += "`game` varchar(10) NOT NULL default '',";
			strSql += " `content` varchar(100) NOT NULL default '',";
			strSql += "`cpn` varchar(15) NOT NULL default '',";
			strSql += "`msgid` varchar(20) NOT NULL default '',";
			strSql += "`linkid` varchar(20) NOT NULL default '',";
			strSql += "`submit_linkid` varchar(20) NOT NULL default '',";
			strSql += "`submitmsgid` varchar(20) NOT NULL default '',";
			strSql += "`addate` datetime default NULL,";
			strSql += "`sendstate` varchar(20) NOT NULL default '0000',";
			strSql += "`comprecstat` varchar(10) NOT NULL default '',";
			strSql += "`province` varchar(4) NOT NULL default '',";
			strSql += "`postcode` varchar(4) NOT NULL default '',";
			strSql += "UNIQUE KEY `id` (`id`),";
			strSql += "INDEX (`msgid`),";
			strSql += "INDEX (`linkid`),";
			strSql += "INDEX (`cpn`),";
			strSql += "INDEX (`company`),";
			strSql += "INDEX (`submitmsgid`)";
			strSql += ");";
		//INDEX ( `name` ) 

		mydb.executeUpdate(strSql);
		
	}
	mydb.dbclose();
}
	public void createMos(int cmonth){
		String strSql = "";
		Calendar c = Calendar.getInstance();
		int month = cmonth;//c.get(Calendar.MONTH)+1;
		int year = 2011;//c.get(Calendar.YEAR);
		System.out.println(year);
		String monthStr = "";
		if(month < 10){
			monthStr = "0"+month;
		}
		else{
			monthStr = "" + month;	
		}
		Mysqldb mydb = new Mysqldb("smscompanymos");
		String tableName = "";
		String dayStr = "";
		for(int i = 1;i <= 31;i++){
			if(i < 10){
				dayStr = "0" + i;
			}
			else{
					dayStr = "" + i;
			}
			tableName = year+monthStr+dayStr + "company_mo";

			strSql = "CREATE TABLE " + tableName + "(";
			strSql += "`id` int(11) NOT NULL auto_increment,";
			strSql += "`company` varchar(10) NOT NULL default '',";
			strSql += "`game` varchar(10) NOT NULL default '',";
			strSql += "`userinput` varchar(20) NOT NULL default '',";
			strSql += "`cpn` varchar(15) NOT NULL default '',";
			strSql += "`msgid` varchar(20) NOT NULL default '',";
			strSql += "`linkid` varchar(20) NOT NULL default '',";
			strSql += "`addate` datetime default NULL,";
			strSql += "`tocompstat` tinyint(4) NOT NULL default '0',";
			strSql += "`comprecstat` varchar(10) NOT NULL default '',";
			strSql += "`province` varchar(10) NOT NULL default '',";
			strSql += "`postcode` varchar(4) NOT NULL default '',";
			strSql += "UNIQUE KEY `id` (`id`),";
			strSql += "KEY `cpn` (`cpn`)";
			strSql += ");";
			//System.out.println(strSql);
			mydb.executeUpdate(strSql);
		
			//System.out.println(strSql);
		}	
			//mydb.dbclose();
	}
	////////////////////////
	///用于增加表中的字段
	//@2010-01-27
	////////////////////////
	public void addRow(int cmonth,String rowName){
		//ALTER TABLE `20100123company_mt` ADD `province` VARCHAR( 10 ) NOT NULL ;
		Calendar c = Calendar.getInstance();
		int month = cmonth;//c.get(Calendar.MONTH)+1;
		int year = 2010;//c.get(Calendar.YEAR);
		System.out.println(year);
		Mysqldb mydb = null;
		String monthStr = "";
		if(month < 10){
			monthStr = "0"+month;
		}
		else{
			monthStr = "" + month;	
		}
		if(this.db.equals("mo")){
			mydb = new Mysqldb("smscompanymos");
		}
		else{
			mydb = new Mysqldb("smscompanymts");
		}
		
		String tableName = "";
		String dayStr = "";
		for(int i = 1;i <= 31;i++){
			if(i < 10){
				dayStr = "0" + i;
			}
			else{
					dayStr = "" + i;
			}
			//submit_linkid 
			//submitmsgid 
			if(this.db.equals("mo")){
				tableName = year+monthStr+dayStr + "company_mo";
			}
			else{
				tableName = year+monthStr+dayStr + "company_mt";
			}
			//tableName = year+monthStr+dayStr + "company_mt";
			String addRowSql = "ALTER TABLE " + tableName + " ADD " + rowName + " VARCHAR( 4 ) NOT NULL";
			mydb.executeUpdate(addRowSql);
		}
		mydb.dbclose();
	}
		public void addIndex(int cmonth,String rowName){
		//ALTER TABLE `20100522company_mt` ADD INDEX ( `cpn` ) 
		Calendar c = Calendar.getInstance();
		int month = cmonth;//c.get(Calendar.MONTH)+1;
		int year = 2010;//c.get(Calendar.YEAR);
		System.out.println(year);
		String monthStr = "";
		if(month < 10){
			monthStr = "0"+month;
		}
		else{
			monthStr = "" + month;	
		}
		Mysqldb mydb = null;//new Mysqldb("smscompanymts");
		if(this.db.equals("mo")){
			mydb = new Mysqldb("smscompanymos");
		}
		else{
			mydb = new Mysqldb("smscompanymts");
		}
		String tableName = "";
		String dayStr = "";
		for(int i = 1;i <= 31;i++){
			if(i < 10){
				dayStr = "0" + i;
			}
			else{
					dayStr = "" + i;
			}
			//submit_linkid 
			//submitmsgid 
			//tableName = year+monthStr+dayStr + "company_mt";
			if(this.db.equals("mo")){
				tableName = year+monthStr+dayStr + "company_mo";
			}
			else{
				tableName = year+monthStr+dayStr + "company_mt";
			}
			String addRowSql = "ALTER TABLE " + tableName + " ADD INDEX(" + rowName + ")";
			mydb.executeUpdate(addRowSql);
		}
		mydb.dbclose();
	}
	public void setDb(String db){
		this.db = db;
	}
}
