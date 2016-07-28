package newmosendhandle;
import java.io.*;
import java.util.ArrayList;
public class MoSenderHandle implements Runnable{
	public void run(){
		CompanysHandle companysHandle = new CompanysHandle();
		ArrayList companys = null;
		MoSenderFactory moSenderFactory = new MoSenderFactory();
		
		ArrayList mosList = null;
		//while(true){
			companys = companysHandle.listsCompanys();
			//System.out.println("company is:" + companys.size());
			try{
				
			
			for(int i = 0;i < companys.size();i++){
				System.out.println("companys is:" + (String)companys.get(i));
	
				if((!((String)companys.get(i)).equals("szpwd"))||(!((String)companys.get(i)).equals("szmk"))||(!((String)companys.get(i)).equals("wry"))||(!((String)companys.get(i)).equals("hcjw"))||(!((String)companys.get(i)).equals("smt"))){
							moSenderFactory.setCompany((String)companys.get(i));
							try{
								MoSender moSender = moSenderFactory.createMoSender();
								new Thread(moSender).start();
								//moSender.sendMo();
								Thread.currentThread().sleep(500);
							}catch(Exception e){
								e.printStackTrace();
							}
				}
				if(((String)companys.get(i)).equals("tltd")){
						MoSender moSender = moSenderFactory.createMoSender();
						new Thread(moSender).start();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			
			System.out.println("各合作方线程启动完毕");
			
			
		//}
	}
	public static void main(String[] args){
		MoSenderHandle moSenderHandle = new MoSenderHandle();
		new Thread(moSenderHandle).start();
	}
}
