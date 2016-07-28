/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

public class sm_deliver_result extends sm_result
{
	public sm_deliver_result()
	{
		
	}
	
	
    	public void readInBytes(byte[] b) //throws Exception
    	{
    		try 
    		{
    			
    			for(int i=0;i<b.length;i++)
    			    System.out.print(b[i]+",");
         		deByteCode=new DeByteCode(b);
         		while(deByteCode.offset<b.length)
         		{
         			byte type = deByteCode.int8();
         			short len = deByteCode.int16();
         			int valueLen = len-3;
         			System.out.println("---type:"+type);
         			System.out.println("---valueLen:  "+valueLen);
         			switch(type)
         			{
         				case 1:
         					mobile_code = deByteCode.asciiz(valueLen);
         					System.out.println("mobile_code:"+mobile_code);
         					break;
         				case 2:
         					game_code = deByteCode.asciiz(valueLen);
         					System.out.println("game_code:"+game_code);
         					break;
         			    case 3:
         			        action_code = deByteCode.asciiz(valueLen);
         				    System.out.println("action_code:"+ action_code);
                            break;
         				case 4:
         					sp_code = deByteCode.asciiz(valueLen);
         					System.out.println("sp_code:"+sp_code);
         					break;
         				case 5:
         					ismg_code = deByteCode.asciiz(valueLen);
         					System.out.println("ismg_code:"+ismg_code);
         					break;
 						default:
         					stat ="01"; //无效的消息类型
         					return ;
         			}
         		}
         		stat = "00"; //成功
         	}
         	catch (Exception e)
         	{
                   System.out.println(e.toString());
            }
    	}
    	
    	public static String mobile_code;
    	public static String game_code;
    	public static String action_code;
    	public static String sp_code;
    	public static String ismg_code;
    	public static String stat;
    	public DeByteCode deByteCode;
    	
    	public String get_mobileCode() { return this.mobile_code;}
    	public String get_gameCode() { return this.game_code;}
    	public String get_actionCode() {return this.action_code;}
    	public String get_spCode() { return this.sp_code;}
    	public String get_ismgCode() { return this.ismg_code;}
    	
    	
}
