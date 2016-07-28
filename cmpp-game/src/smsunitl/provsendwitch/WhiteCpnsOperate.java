package smsunitl.provsendwitch;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.sql.*;
import companymtdb.MysqldbT;
import smsunitl.configfile.CompanysConfigManager;
public class WhiteCpnsOperate {
	public static List whiteCpnsList;
	public static long lastModify;
	private List tempWhiteCpnsList;
	private String cpn = "";//手机号码
	String dbIp = "xiangtone_dbip";
	String dbName = "mts";
	String dbUser = "xiangtone_dbuser";
	String dbPwd = "xiangtone_dbpwd";
	String dbPort = "xiangtone_dbport";
	public MysqldbT mydb = null;
	public boolean whiteCpnFlag = false;//判断是否是白号码标识
	public void  init(){
		if(whiteCpnsList == null){
			//whiteCpnsList = new ArrayList();
			String whiteCpns = (String)CompanysConfigManager.getInstance().getConfigItem("whitecpns","0000");
			//System.out.println(whiteCpns);
			String[] tempCpns = whiteCpns.split(",");
			this.whiteCpnsList = Arrays.asList(tempCpns);
			System.out.println("is null.................");
			String fileName = "/home/smsdevp/qwsms/tlconfig.ini";//System.getProperty("user.dir") + File.separator + "config.ini";
			File f = new File(fileName);
			lastModify = f.lastModified();
		}
		/*
		else{
			System.out.println("not null");
		}
		*/
		tempWhiteCpnsList = this.whiteCpnsList;
	}
	public boolean checkModify(){
		boolean checkFlag = false;
		String fileName = "/home/smsdevp/qwsms/tlconfig.ini";//System.getProperty("user.dir") + File.separator + "config.ini";
		File f = new File(fileName);
		if(f.lastModified() > this.lastModify){
			checkFlag =  true;
		}
		return checkFlag;
		//if(f.lastModified())
	}
	//add at 2012-05-02 
	//用于判断是否是白号码的操作
	public void checkWhiteCpn(){
		//boolean whiteCpnFlag = false;
		this.mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
		String strSql = "select id from whitecpn where cpn='" + this.cpn + "'";
		//System.out.println(strSql);
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			if(rs.next()){
				this.whiteCpnFlag = true;
			}
			else{
				this.whiteCpnFlag = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			this.whiteCpnFlag = false;
		}
		finally{
				try{
					this.mydb.close();
				}catch(Exception e){
					e.printStackTrace();
				}
		}
	}
	public List getWhiteCpnsList(){
		return this.tempWhiteCpnsList;
	}
	public void setCpn(String cpn){
		this.cpn = cpn;
	}
	public boolean getWhiteCpnFlag(){
		return this.whiteCpnFlag;	
	}
}
