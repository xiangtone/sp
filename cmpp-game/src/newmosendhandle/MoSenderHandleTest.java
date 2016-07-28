package newmosendhandle;
/*
*该类用于对新合作方进行相应的测试
*/
import java.io.*;
import java.util.ArrayList;
public class MoSenderHandleTest implements Runnable{
	public void run(){
		CompanysHandle companysHandle = new CompanysHandle();
		ArrayList companys = null;
		MoSenderFactory moSenderFactory = new MoSenderFactory();
		
		ArrayList mosList = null;
		String company="szpwd";
		//while(true){
			

			moSenderFactory.setCompany(company);
							try{
								MoSender moSender = new SzpwdMoSender(company);//moSenderFactory.createMoSender();
								new Thread(moSender).start();
								//moSender.sendMo();
								Thread.currentThread().sleep(1000 * 2);
							}catch(Exception e){
								e.printStackTrace();
							}

			System.out.println("---------------------------------------");
			try{
				//Thread.currentThread().sleep(5000);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		//}
	}
	public static void main(String[] args){
		MoSenderHandleTest moSenderHandle = new MoSenderHandleTest();
		new Thread(moSenderHandle).start();
	}
}
