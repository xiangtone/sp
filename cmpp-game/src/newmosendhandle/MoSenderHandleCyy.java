package newmosendhandle;
/*
*该类用于对新合作方进行相应的测试
*/
import java.io.*;
import java.util.ArrayList;
public class MoSenderHandleCyy implements Runnable{
	public void run(){
		CompanysHandle companysHandle = new CompanysHandle();
		ArrayList companys = null;
		MoSenderFactory moSenderFactory = new MoSenderFactory();
		
		ArrayList mosList = null;
		String company="cyy";
		while(true){
			

			moSenderFactory.setCompany(company);
							try{
								MoSender moSender = new CyyMoSender(company);//moSenderFactory.createMoSender();
								new Thread(moSender).start();
								//moSender.sendMo();
								Thread.currentThread().sleep(2000);
							}catch(Exception e){
								e.printStackTrace();
							}

			System.out.println("---------------------------------------");
			try{
				//Thread.currentThread().sleep(5000);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	public static void main(String[] args){
		MoSenderHandleCyy moSenderHandle = new MoSenderHandleCyy();
		new Thread(moSenderHandle).start();
	}
}
