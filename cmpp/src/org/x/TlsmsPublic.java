package org.x;

import org.apache.log4j.Logger;

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
public class TlsmsPublic {
	private static Logger logger = Logger.getLogger(.class);
	public static void main(String[] args){
		try{
			SMSIsmgInfo info = new SMSIsmgInfo("config.ini");
			info.loadParam();
			info.printParam();
			
			SMSActiveTest sdc = new SMSActiveTest();
			new Thread(sdc).start();
			
			SMSRecive sr = new SMSRecive();
			new Thread(sr).start();
			
			//VCPServer server = new VCPServer(8900);
			//new Thread(server).start();
			/*
			TestServer ts = new TestServer(8110);//平台检测服务程序
			new Thread(ts).start();
			OrderServer os = new OrderServer();//定购服务启动
			new Thread(os).start();
			*/
		}catch(Exception e){
			System.out.println(">>>>>>平台出错");
	    	e.printStackTrace();
		}
	}
	
}
