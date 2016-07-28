package companytempmthandle;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
public class TempCompanyMtHandle implements Runnable{
	public void run(){
		String checkTime = "";
		String tableName = "";
		long startTime = 0;
		long endTime = 0;
		while(true){
			try{
				startTime = System.currentTimeMillis();
				TempMtHandle tempMtHandle = new TempMtHandle();
				checkTime = this.getCheckTime();
				tableName = this.getUpdateTable();
				tempMtHandle.setCheckTime(checkTime);
				tempMtHandle.setTableName(tableName);
				tempMtHandle.tempMtOperate();
				endTime = System.currentTimeMillis();
				System.out.println("use time is:" + (endTime - startTime)/1000);
				try{
					Thread.currentThread().sleep(1000 * 10);
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
			
		}
	}
	private void updateTempMt(){
		try{
			System.out.println("Start to backUp tempMt");
			

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private String getCheckTime(){
		String checkTime = "";
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。 
		cal.add(Calendar.SECOND,-30);

	    java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    checkTime = format.format(cal.getTime());
		return checkTime;
	}
	private String getUpdateTable(){
		String tableName = FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
		
		return tableName;
	}
	public static void main(String[] args){
		TempCompanyMtHandle tempMtHandle = new TempCompanyMtHandle();
		new Thread(tempMtHandle).start();
	}
}
