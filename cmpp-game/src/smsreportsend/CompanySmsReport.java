package smsreportsend;
import java.util.ArrayList;
public class CompanySmsReport implements Runnable{

	public void run(){
		SmsCompanys smsCompanys = new SmsCompanys();//用于取得相应的合作公司的名称
		ArrayList companysList = new ArrayList();
		while(true){
			smsCompanys.mydb = new Mysqldb();
			companysList = smsCompanys.getCompanys();
			smsCompanys.mydb.dbclose();
			String company = "";
			//SmsReportHandle smsReportHandle = new SmsReportHandle(company);//用于取得相应合作放的短信及相应的状态报告

				//smsReportHandle.reportHandle();
			
			for(int i = 0;i < companysList.size();i++){
				company = (String)companysList.get(i);
				SmsReportHandle smsReportHandle = new SmsReportHandle(company);//用于取得相应合作放的短信及相应的状态报告

				smsReportHandle.reportHandle();
			}
			
			try{
				Thread.currentThread().sleep(1000*5);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
			CompanySmsReport csr = new CompanySmsReport();
			new Thread(csr).start();
	}
}
