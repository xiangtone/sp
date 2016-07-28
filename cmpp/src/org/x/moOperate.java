package org.x;
//package threadpool;
import com.xiangtone.util.MyTools;
import com.xiangtone.util.IntByteConvertor;
import com.xiangtone.util.FormatSysTime;
import comsd.commerceware.cmpp.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import blackcpncheck.BlackCpnCheck;
public class moOperate extends Thread{
	public cmppe_deliver_result cd;
	private boolean runningFlag;
	// public SMSoperate handle;
	 public int threadNum = 0;
	 public String ISMGID = "";
	public moOperate(int threadNum,String ismsgid){
		runningFlag = false;
		this.ISMGID = ismsgid;
		this.threadNum = threadNum;
		//handle = new SMSoperate();
		System.out.println("thread " + threadNum + "started.");
		
	}

	 public boolean isRunning()
	 {
	 return runningFlag;
	 }
	 public synchronized void setRunning(boolean flag)
	 {
	 runningFlag = flag;
	 if(flag)
	 this.notify();
	 }
	 public synchronized void run(){
		 try{
		 	
			 while(true) {
				 if(!runningFlag){
					 this.wait();
				 }
				 else{
				 ///////////////////////
				 SMSoperate handle = new SMSoperate();
				 System.out.println("thread " + threadNum + "started.");
				  				// String recmsgId = MyTools.Bytes2HexString(cd.get_msg_id());
				  				//System.out.println("msgid value is:" + cd.get_msg_id().intValue());
				  				String msg_id      = IntByteConvertor.getLong(cd.msg_id,0) + "";//MyTools.Bytes2HexString(cd.get_msg_id());
	    		      	
	    		      	
	    		        String str_ismgid = this.ISMGID;
	    		        String str_spcode = cd.getSPCode();
	    		        String str_cpn    = cd.getCpn();
	    		        int 	cpn_type = cd.getsrcType();//add 061121
	    		        int    i_len      = cd.getLen();
	    		        int    i_fmt      = cd.getFmt();
	    		        int    i_tp_udhi  = cd.get_tp_udhi();
	    		        String str_svc_type  = cd.getServerType();
	    		        String link_id = cd.getLinkId();
	    		        System.out.println("content:" + cd.getMessage());
									
	    		        byte[] str_content=cd.getMessage();
	    		        int    i_report_flag = cd.getRegistered_delivery(); //状态报告標
			 		        if(i_report_flag == 1)
	    		      	{
	    		      		System.out.println("状态报告信息....");
	    		      	
	    		      		String report_dest_cpn = cd.get_dest_cpn();
	    		      		msg_id = IntByteConvertor.getLong(cd.msg_id2,0) + "";
	    		      		String submit_time = cd.get_submit_time();
	    		      		String done_time   = cd .get_done_time();
	    		      		String stat2       = cd.get_stat();
	    		      		System.out.println("stat2:"+stat2);
	    		      		//myLogger.info(FormatSysTime.getCurrentTimeA() + "report msg--spcode:" + str_spcode +" cpn:" + report_dest_cpn.trim() + " msgid:" + msg_id + " submit_time:" + submit_time +" done_time:" + done_time + " stat_dev:" + stat2);
	    		      		int stat_dev=0;
	    		      		if(stat2.equals("DELIVRD"))
	    		      			stat_dev=0;
	    		      		else
	    		      			stat_dev=-1;
	    		      	
	    		      		handle.receiveReport(this.ISMGID,msg_id,link_id,report_dest_cpn,str_spcode,str_cpn,submit_time,done_time,stat_dev,stat2);
	    		      		setRunning(false);
	    		      		continue;
	    		      	}
	    		      	else{
	    		      		//myLogger.info(FormatSysTime.getCurrentTimeA() + " new msg--spcode:" + str_spcode +" cpn:" + str_cpn.trim() + " linkId:" + link_id + " content:" +  new String(str_content));	
	    		      	}
	    		      	cd.printAll(); //打印mo消息;

	    		      	///////////////////////////////
									///////////////////////////////
										//用于屏蔽黑号码
										BlackCpnCheck bc = new BlackCpnCheck();
										if(bc.checkCpn(str_cpn)){
											
										}
										else{
									///////////////////////////////	
	    		      		handle.setDeliver_ismgID(str_ismgid);
									  handle.setDeliver_msgID(msg_id);
										handle.setDeliver_spCode(str_spcode);
										handle.setDeliver_serverID(str_svc_type);
										handle.setDeliver_fmt(i_fmt);
										handle.setDeliver_srcCpn(str_cpn);
										handle.setDeliver_srcCpnType(cpn_type);//手机号码的类型 伪码还是明码。
										handle.setDeliver_contentLen(i_len);
										handle.setDeliver_content(str_content);
										handle.setDeliver_linkId(link_id);
										
	    		      		handle.receiveDeliver();
	    		      	}//add at 2009-03-05 屏蔽黑号码
										//Thread.currentThread().sleep(200);
				////////////////////////
					 setRunning(false);
				 }
			 }
		 }
		 catch(InterruptedException e){
		 	Logger myLogger = Logger.getLogger("MsgSendLogger");
	  	Logger mySonLogger = Logger.getLogger("myLogger.mySonLogger");
    	PropertyConfigurator.configure("log4j.properties");
    	myLogger.info(FormatSysTime.getCurrentTimeA() + " moOperate Exception:" + e.toString());	

			 System.out.println("Interrupt");
		 }
	}
}
