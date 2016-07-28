package org.x;
/*

 *Copyright 2003 Xiamen Xiangtone Co. 

 *All right reserved.

 */



public class GameServer implements Runnable 

{

	protected String str[][] ={{"","SHJKJLB","SYSAUTO","106697076"},

														}; 

    protected MessageGame sms = new MessageGame("00"); 

    public void run()

    {    	

	       

	        

        while(true)

        {

        		

        	try 

        	{

        			

        		for(int i=0;i<str.length;i++) 

        		{

        			String mobile_code = str[i][0];

        			String game_code   = str[i][1];

        			String action_code = str[i][2];

        			String sp_code = str[i][3];

        			String ismg_code="";

        			String linkId = "";

        			int cpnType = 0;

        			String msgId = "";

        			sms.multiDispatch(mobile_code,game_code,action_code,sp_code,ismg_code,linkId,cpnType,msgId);

        		}

        		//Thread.currentThread().sleep(1000 * 60 * 1);//60000
			Thread.currentThread().sleep(1000 * 10 * 1);//60000


        	}

        	catch(Exception e) 

        	{

        		System.out.println(e.toString());

        	}

        		

        }

    }

     

}

