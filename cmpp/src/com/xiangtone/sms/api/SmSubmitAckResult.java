/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

public class SmSubmitAckResult extends SmResult
{
	public SmSubmitAckResult()
	{
		
	}
	public void readInBytes(byte[] b) //throws Exception
	{
		try 
    		{
    			//System.out.println("b.length"+b.length);
         		deByteCode=new DeByteCode(b);
         		while(deByteCode.offset<b.length)
         		{
         			byte type = deByteCode.int8();
         			short len = deByteCode.int16();
         			int valueLen = len-3;
         			System.out.println("type:"+type);
         			switch(type)
         			{
         				case 100 :
         					error_code = deByteCode.asciiz(valueLen);
         					stat = error_code;
         					System.out.println("stat:"+stat);
         					break;
         					
         				default:
         					stat = "-1"; //·þÎñ·µ»Ø´íÎó
         					return ;
         			}
         		}
         	}
         	catch (Exception e)
         	{
                   //throw new Exception("decoding error");
                   
                   System.out.println(e.toString());
                }
		
		
	}
	public DeByteCode deByteCode;
	public String stat="00";
	public String error_code;
}
