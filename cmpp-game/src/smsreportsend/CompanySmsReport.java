package smsreportsend;
import java.util.ArrayList;
public class CompanySmsReport implements Runnable{

	public void run(){
		SmsCompanys smsCompanys = new SmsCompanys();//����ȡ����Ӧ�ĺ�����˾������
		ArrayList companysList = new ArrayList();
		while(true){
			smsCompanys.mydb = new Mysqldb();
			companysList = smsCompanys.getCompanys();
			smsCompanys.mydb.dbclose();
			String company = "";
			//SmsReportHandle smsReportHandle = new SmsReportHandle(company);//����ȡ����Ӧ�����ŵĶ��ż���Ӧ��״̬����

				//smsReportHandle.reportHandle();
			
			for(int i = 0;i < companysList.size();i++){
				company = (String)companysList.get(i);
				SmsReportHandle smsReportHandle = new SmsReportHandle(company);//����ȡ����Ӧ�����ŵĶ��ż���Ӧ��״̬����

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
