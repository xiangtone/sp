package org.x;
/*
 * Created on 2006-11-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class tlsms_publictest1 {
	public static void main(String[] args){
		try{
			SMSIsmgInfo info = new SMSIsmgInfo("config.ini");
			info.loadParam();
			info.printParam();
			/*
			SMSActiveTest sdc = new SMSActiveTest();
			new Thread(sdc).start();
			*/
			SMSReciveTest1 sr = new SMSReciveTest1();
			new Thread(sr).start();
			
			//VCPServer server = new VCPServer(8900);
			//new Thread(server).start();
			/*
			TestServer ts = new TestServer(8110);//ƽ̨���������
			new Thread(ts).start();
			OrderServer os = new OrderServer();//������������
			new Thread(os).start();
			*/
		}catch(Exception e){
			System.out.println(">>>>>>ƽ̨����");
	    	e.printStackTrace();
		}
	}
	
}
