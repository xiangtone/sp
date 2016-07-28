package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.net.*;
import com.xiangtone.sms.api.*;
import com.xiangtone.util.*;
/**
*
*
*/
public class MessageGame 
{

	private Properties Props = new Properties();
    private Vector destSet = new Vector(1,5);
	private Vector feeSet = new Vector(1,5);
	private Vector msgSet = new Vector(1,5);
	private Vector costSet = new Vector(1,5);
	private Vector linkidSet = new Vector(1,5);//add at 061121
	private Vector cpntypeSet = new Vector(1,5);
	private Vector ismgSet = new Vector(1,5);
	private Vector msgIdSet = new Vector(1,5);//add at 08-11-27
	//public mytools tools = new mytools(); 
	
	Socket s =null;
	String ismgid = "00";

	
	
	/**
	*
	*
	*/
	public MessageGame(String ismgid) 
	{
		this.ismgid = ismgid;
		init(ismgid);
	}
	/**
	*
	*
	*/
	private  void  init(String ismgStr)
	{
		try{
	  	    
	  	    File f = null;
	  	    /*
			if(ismgStr.equals("01"))
			{
				f = new File("app_name_bj.properties");
			}
			else if(ismgStr.equals("06"))
			{
				System.out.println("app_name_ln.properties.....");
				f = new File("app_name_ln.properties");
			}
			else if(ismgStr.equals("08"))
			{
				f = new File("app_name_hl.properties");
			}
			else if(ismgStr.equals("15"))
			{
				f = new File("app_name_sd.properties");
			}
			else if(ismgStr.equals("19"))
			{
				f = new File("app_name_gd.properties");
			}
			else
			{
				f = new File("app_name.properties");
			}
			*/
			System.out.println("app_name_fj.properties.....");
				f = new File("app_name_fj.properties");
			
	  	   
	  	    FileInputStream ins = new  FileInputStream(f);
		    if(ins!=null)
		    {                                                    
	   			Props = new Properties();                             
				Props.load(ins);
		    }else{	
		    	//String strDate = saveLog.formatDate();
		    	//saveLog.error(strDate+"--API_GET--Can not read the properties file");
		    	System.out.println("app_name.properties is not exist");
		    }
    	}catch(Exception e)
    	{
    		//String strDate = saveLog.formatDate();
    		//saveLog.error(strDate+"--API_GET--read profile.ini file error!--"+e.toString());
    	}
    	
    	return ;
	}
	
	/******************************************************************
	功能：构造函数::
	  输入参数说明：
	  1.内存中队列的句柄；
	******************************************************************/
	//public int dispatchGame(String cpn,String itemname,String itemaction,String ismgId,String linkid,String cpntype,Vector destSet,Vector feeSet,Vector msgSet,Vector costSet,Vector ismgSet,Vector linkidSet,Vector cpntypeSet)
	public int dispatchGame(String cpn,String itemname,String itemaction,String ismgId,String linkid,int cpnType,String msgId,Vector destSet,Vector feeSet,Vector msgSet,Vector costSet,Vector ismgSet,Vector linkidSet,Vector cpnTypeSet,Vector msgIdSet)
	{
		int feeType = 1;
		int flag = 0;
		destSet.clear();
		feeSet.clear();
		msgSet.clear();
		costSet.clear();
		ismgSet.clear();
		cpnTypeSet.clear();
	  msgIdSet.clear();
		//传入的信息
		itemname = itemname.toUpperCase();
		itemaction = itemaction.toUpperCase();
		
		//返回KEY VALUE，取得类名
		String className = Props.getProperty(itemname, "paramName"); 
		//取得文件中的对应方法名的串
		String classMethod= Props.getProperty(itemname+".METHOD", "paramName");
		System.out.println("itemname:"+itemname+"---itemaction:"+itemaction+"--classname:"+className+"---classMethod:"+classMethod);			
	
//		if(className.equals("paramName"))
//			className="ERROR";
//		if(classMethod.equals("paramName"))
//			classMethod="execMain";
//		
		if(!className.equals("paramName") && !classMethod.equals("paramName"))
		{
		  try{
		    Class toRun = Class.forName(className);
		     //定义方法名
        	Method mainMethod=toRun.getDeclaredMethod(classMethod,new Class[]{String.class,String.class,String.class,String.class,Integer.class,String.class,Vector.class,Vector.class,Vector.class,Vector.class,Vector.class,Vector.class,Vector.class,Vector.class});
        	//激活方法返回结果
			feeType=Integer.parseInt(mainMethod.invoke(toRun.newInstance(), new Object[]{cpn,itemaction,ismgId,linkid,cpnType,msgId,destSet,feeSet,msgSet,costSet,ismgSet,linkidSet,cpntypeSet,msgIdSet}).toString());

                System.out.println("feetype"+feeType);
		  }catch(Exception e)
		  {
		  	//String strDate = saveLog.formatDate();
		  	//saveLog.error(strDate+"--API_GET--dynamic create class invoke funtion --"+e.toString());
		  	System.out.println(e.toString());
		  	e.printStackTrace();
		  }
		
		}
		else
		{ System.out.println("派发错误:没有该游戏");
			 System.out.println("ismgid:"+this.ismgid);
			 if(this.ismgid.equals("00"))
			    return feeType;
			   try
		  	   {
		  	     	if(flag == 1)
		  	       	 return feeType;
		  	   	     flag++;
		  	   		itemname = "ERROR";
		  	   		// dispatchGame(cpn,itemname,itemaction,ismgId,destSet,feeSet,msgSet,costSet,ismgSet);
    		   	}
		  	   	catch(Exception eee)
		  	   	{
		  	   		eee.printStackTrace();
		  	   	}
			
		}			
		return feeType;
	}
	
	//如果短信长度太长，那么就分隔
	   public String[] splitConent(String smContent)
      {
             int nLen = smContent.length();
			 int n1 = (nLen+69)/70;
			  System.out.println("::::短信条数："+n1);
			String[] str1 = new String[n1];	
			for( int j=0; j<n1; j++)
			{
				int j1 = j*70;
				int j2 = (j+1)*70;
				
				if( j<(n1-1) )
				str1[j] = smContent.substring(j1,j2);
				else
				str1[j] = smContent.substring(j1);
			}
			return str1;
       }
	
    //public void multiDispatch(String cpn,String gameCode,String gameAction,String spCode,String ismgCode)
    public void multiDispatch(String cpn,String gameCode,String gameAction,String spCode,String ismgCode,String linkid,int cpn_type,String msgId)
    {
    	
    	//int temp_cpntype = cpn_type;
    	//int feetype = dispatchGame(cpn,gameCode,gameAction,ismgCode,linkid,temp_cpntype,destSet,feeSet,msgSet,costSet,ismgSet,linkidSet,cpntypeSet);
    	System.out.println("linkid is:" + linkid);
    	int feetype = dispatchGame(cpn,gameCode,gameAction,ismgCode,linkid,cpn_type,msgId,destSet,feeSet,msgSet,costSet,ismgSet,linkidSet,cpntypeSet,msgIdSet);
    	int i1=destSet.size();
        int i2=feeSet.size();
        int i3=msgSet.size();
        int i4=costSet.size();
        int i6=linkidSet.size();
        int i7=cpntypeSet.size();
        int i5=ismgSet.size();
        int i8=msgIdSet.size();
        System.out.println("i1:"+i1+"  i2:"+i2+"  i3:"+i3+"  i4:"+i4+"  i5:"+i5  +"  i6:"+i6  +"  i7:" + i7);
        String str_feeType = (new Integer(feetype)).toString();
        try
        {
        	for( int i=0; i< i1 && i<i2 && i<i3 && i<i4 && i < i6 && i < i7 && i < i8; i++)
            {
            		
           		String destcpn = (String)destSet.get(i);
           		String feecpn  = (String)feeSet.get(i);             
		        String content = (String)msgSet.get(i);
                String serverID = (String)costSet.get(i); //费用服务id
                String _ismgID   = ismgCode;
                       _ismgID   = (String)ismgSet.get(i); 
                String _msgId = (String)msgIdSet.get(i); 
                System.out.println("------msgId value is:" + _msgId);
                int temp_cpntype =((Integer)cpntypeSet.get(i)).intValue();     
               	/*
                System.out.println(":::::::::::::::::::");
                System.out.println(":::::::::::::::::::");
                System.out.println(":::::::::::::::::::");
                System.out.println(":::::::::::::::::::");
                System.out.println("destcpn:" + destcpn);
                System.out.println("feecpn:" + feecpn);
                System.out.println("content:" + content);
                System.out.println("serverID:" + serverID);
                System.out.println("msgId" + _msgId);
                System.out.println(":::::::::::::::::::");
                System.out.println(":::::::::::::::::::");
                System.out.println(":::::::::::::::::::");
                System.out.println(":::::::::::::::::::");
                 */
                String vcpID="1"; //xiangtone vcpid
                String sp_code = spCode;
                String provID = _ismgID;
                String mediaType = "1"; //文本
                
                String delivery ="0";//不需要状态报告
              	String cpnlinkid = (String)linkidSet.get(i);//add at 061121
              	//String tempcpn_type = (String)cpntypeSet.get(i);//add at 061121
              	int usercpn_type = 0;//Integer.parseInt(tempcpn_type);//add at 061121
               String str2[] = splitConent(content);
                    for(int j=0;j<str2.length;j++)
                    { 
                    	MessageSubmit ms = new MessageSubmit();
                    	ms.set_destCpn(destcpn);
						ms.set_feeCpn(feecpn); 
						ms.set_cpnType(temp_cpntype);
						ms.set_linkid(cpnlinkid);
						ms.set_content(str2[j]);  
						ms.set_serverID(serverID); 
						ms.set_vcpID(vcpID);    
						ms.set_provID(provID) ;                         
						ms.set_spCode(sp_code);   
						ms.set_feeType(str_feeType);  
						//ms.set_feeCode(String str)  
						ms.set_mediaType(mediaType);
						ms.set_delivery(delivery) ;
						ms.set_gameCode(gameCode);
						ms.set_msgId(_msgId);
						ms.set_sendTime(FormatSysTime.getCurrentTimeA());
                    	ms.send_result_to_smsPlatform();
                    	ms.insertMTlog();
                    	System.out.println("insert mt log ...OK!!");
       					      
                    }         
                              
                              
            }//end for        
                              
                              
       }                      
       catch(Exception e)     
       {                      
       	System.out.println(e+"---MultiDp Switch error!");
       }
       
       finally
       	{
       		try
       		{
       			if(s!=null)
       				s.close();
       		}
       		catch(IOException e2)
       		{
       			System.out.println(e2.toString());
       		}
       	}
    }
  
}