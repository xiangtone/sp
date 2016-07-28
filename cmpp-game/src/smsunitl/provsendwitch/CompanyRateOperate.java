package smsunitl.provsendwitch;
/*
 * 该类用于对合作方进行扣量处理
 */
import java.util.Arrays;

import smsunitl.configfile.*;
import smsunitl.util.*;
import java.util.*;
///////////////////////////
import java.sql.*;
import companymtdb.MysqldbT;
///////////////////////////
public class CompanyRateOperate{
	private String companyName = "";
	private String rptCompany = "";//状态报告扣量公司
	private int lostRate = 0;
	private String provId = "";
	private boolean lostFlag = false;
	public MysqldbT mydb = null;
	double bytP = 5;   
    double sngBegin = 1;   
    double sngEnd = 10;   
    double sngPB = 2;   
    double sngPE = 3;   
    int tempCount = 0;
	public void checkLostOperate(){
				//String tempLostRates = (String)ConfigManager.getInstance().getConfigItem(this.companyName + "lost","0");
			
				int lostRate = this.companyMolostRate();//Integer.parseInt(tempLostRates);
				System.out.println(this.companyName + "lost rate is:" + lostRate);
				//System.out.println(this.companyName + "lost rate is:" + lostRate);
				//System.out.println(this.companyName + "lost rate is:" + lostRate);
				if(lostRate > 0){
					bytP = lostRate;
					sngEnd = lostRate;
				       Rand r = new Rand();   
				       double randNum = 0; 
				       randNum = r.GetRndNumP(sngBegin, sngEnd, sngPB, sngPE, bytP);
				       System.out.println("rand num is:::::::::::::::::::" + randNum);
				       if(randNum >= 2 && randNum <=3 ){
				        	this.lostFlag = true;
				        }
				        else{
				        		this.lostFlag = false;
				        }
				}
	}
	///////////////////////////////////////add at 101213
	public void checkRptLostOperate(){

			//String tempLostRates = (String)ConfigManager.getInstance().getConfigItem(this.rptCompany + "reptlost","0");
			//reptswitch 是否是统一扣量的开关，0为开，既扣统一的量，1按照各个公司安排的进行扣量
			String lostRateSwitch = (String)ConfigManager.getInstance().getConfigItem("reptswitch","0");
			String tempLostRates = "";
			if(lostRateSwitch.equals("0")){
				tempLostRates = (String)ConfigManager.getInstance().getConfigItem("reptlost","0");
				//System.out.println("统一扣量。。。。。。。。。。。。。。。。。");
			}
			else{
				tempLostRates = (String)ConfigManager.getInstance().getConfigItem(this.rptCompany + "reptlost","0");
				//System.out.println("各自扣量。。。。。。。。。。。。。。。。。");
			}
				int lostRate = Integer.parseInt(tempLostRates);
				System.out.println(this.rptCompany + " lostRate:" + lostRate);
				if(lostRate > 0){
					bytP = lostRate;
					sngEnd = lostRate;
				       Rand r = new Rand();   
				       double randNum = 0; 
				       randNum = r.GetRndNumP(sngBegin, sngEnd, sngPB, sngPE, bytP);
							 //System.out.println("randNum is:" + randNum);
				       if(randNum >= 2 && randNum <=3 ){
				        	this.lostFlag = true;
				        	randNum = 0;
				        }
				        else{
				        	this.lostFlag = false;
				        }
				}
	}
	///////////////////////////////////////
//查看合作方mo扣量值
private int companyMolostRate(){
	String dbIp = "xiangtone_dbip";
	String dbName = "mts";
	String dbUser = "xiangtone_dbuser";
	String dbPwd = "xiangtone_dbpwd";
	String dbPort = "xiangtone_dbport";
	int moLost = 0;
	this.mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);	
	String strSql = "select moLostNum from lostCpByProvince where cpid='" + this.companyName + "' and province='" + this.provId + "'";
	try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			if(rs.next()){
				moLost = rs.getInt("moLostNum");
			}
			else{
				moLost = 0;
			}
		}catch(Exception e){
			e.printStackTrace();
			moLost = 0;
		}
		finally{
				try{
					this.mydb.close();
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		System.out.println("the mo lost rate is:" + moLost);
		return moLost;
}
///////////////////////////////////////
///////////////////////////////////////
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	public void setRptCompany(String rptCompany){
				this.rptCompany = rptCompany;
  }
	public void setProvId(String provId){
		this.provId = provId;
	}
	public void setLostRate(int lostRate){
		this.lostRate = lostRate;
	}
	public void setLostFlag(boolean lostFlag){
		this.lostFlag = lostFlag;
	}
	public boolean getLostFlag(){
		return this.lostFlag;
	}
	public int getLostRate(){
		return this.lostRate;
	}

	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		CompanyRateOperate cro = new CompanyRateOperate();
		cro.setCompanyName("sjnz");
		cro.setProvId("0015");
		//for(int i = 0;i < 20000;i++){
			cro.checkLostOperate();
		//}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
		System.out.println("the count is:" + cro.tempCount);
		//System.out.println("the flag is:" + cro.getLostFlag());
	}
}
