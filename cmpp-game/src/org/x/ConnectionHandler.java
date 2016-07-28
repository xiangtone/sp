package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
import java.io.*;
import java.net.*;
import com.xiangtone.sms.api.*;
import com.xiangtone.util.*;
/**
*
*
*/
public class ConnectionHandler implements Runnable 
{
    protected Socket socketToHandle;
    protected conn_desc con ;
    protected message mess = new message();
    protected sm_deliver_result sm = new sm_deliver_result();
    protected mysqldb db;
   
    
    /**
    *
    *
    */
    public ConnectionHandler(Socket aSocketToHandle) 
    {
        socketToHandle = aSocketToHandle;
        con = new conn_desc(socketToHandle);
        db = new mysqldb();  
    }
    public void run() 
    {
        try 
        {
        	
        	mess.readPa(con); //读取提交信息
        	System.out.println("sm.stat:"+sm.stat);
        	//sm.stat = "00";
        	if(sm.stat.equals("00")) //mo信息上来了
        	{
        		
                sm.stat = "01";
        		String mobile_code = sm.get_mobileCode();
        		
        		String game_code = sm.get_gameCode();
        	
        		String action_code = sm.get_actionCode();
        	
        		String sp_code =sm.get_spCode();
        		
        		String ismg_code = sm.get_ismgCode();
        		String msgId = sm.get_msgId();
        		/////////add at 061121
        		System.out.println(":::::::::::::::::::::::::");
        		System.out.println(":::::::::::::::::::::::::");
        		System.out.println(":::::::::::::::::::::::::");
        		System.out.println(":::::::::::::::::::::::::");
        		System.out.println("mobile_code:" + mobile_code);
        		System.out.println("game_code:"  + game_code);
        		System.out.println("action_code:" + action_code);
        		System.out.println("msgId" + msgId);
        		System.out.println(":::::::::::::::::::::::::");
        		System.out.println(":::::::::::::::::::::::::");
        		System.out.println(":::::::::::::::::::::::::");
        		String linkid = sm.get_linkId();
        		int cpn_type = sm.get_cpntype();
        		int vcpid = 1; //xiangtone vcpid =2;
        		String deliverTime = FormatSysTime.getCurrentTimeA();
        		//String linkid = sm.get_linkId();//add at 061120 
        		mobile_code = mobile_code.trim();
        		game_code = game_code.trim().toUpperCase();
        		if(action_code == null)
        			action_code = "";
        		action_code = action_code.trim().toUpperCase();
   
        		System.out.println("M:"+mobile_code+"--G:"+game_code+"--Cmd:"+action_code);
        		
        		
        		
        		 //游戏派发 并发送
        		System.out.println("mobile_code..."+mobile_code);
        		System.out.println("game_code:"+game_code);
        		System.out.println("action_code:"+action_code);
        		System.out.println("sp_code:"+sp_code);
        		System.out.println("ismg_code:"+ismg_code);
        		MessageGame sms = new MessageGame(ismg_code);
			    sms.multiDispatch(mobile_code,game_code,action_code,sp_code,ismg_code,linkid,cpn_type,msgId);//add msgId at 08-11-27
			    
			    //日志处理
			    MessageDeliver deliver = new MessageDeliver();
			    deliver.set_vcpid(vcpid); //2: 代表翔通
			    deliver.set_ismgCode(ismg_code);
			    deliver.set_spCode(sp_code);
			    deliver.set_mobileCode(mobile_code);
			    deliver.set_gameCode(game_code);
			    deliver.set_actionCode(action_code);
			    deliver.set_deliverTime(deliverTime);
			    deliver.insertMOLog();
			    System.out.println("insert mo log...ok!!");	
        	}
        	
            
        }
        catch (Exception e) 
        {
            System.out.println("Error handling a client: " + e);
            e.printStackTrace();
        }
    }   
}