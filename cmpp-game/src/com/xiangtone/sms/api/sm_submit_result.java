/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

public class sm_submit_result extends sm_result
{
	public sm_submit_result()
	{
		
	}
	
	
    	public void readInBytes(byte[] b) //throws Exception
    	{
    		try 
    		{
    			//System.out.println("b.length"+b.length);
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
         					vcp_id = deByteCode.asciiz(valueLen);
         					System.out.println("vcp_id:"+vcp_id);
         					break;
         				case 2:
         					server_code = deByteCode.asciiz(valueLen);
         					System.out.println("server_code:"+server_code);
         					break;
         					
         			    case 3: prov_code = deByteCode.asciiz(valueLen);
         				       System.out.println("prov_id:"+ prov_code);
                               break;
         				case 4:
         					server_type = deByteCode.asciiz(valueLen);
         					System.out.println("server_type:"+server_type);
         					break;
         				case 5:
         					dest_cpn = deByteCode.asciiz(valueLen);
         					System.out.println("dest_cpn:"+dest_cpn);
         					break;
         				case 6:
         					fee_cpn = deByteCode.asciiz(valueLen);
         					System.out.println("fee_cpn:"+ fee_cpn);
         					break;
         				case 7: 
         					fee_type = deByteCode.asciiz(valueLen);
         					System.out.println("fee_type:"+ fee_type);
         					break;
         			
         				case 8:
         					fee_code = deByteCode.asciiz(valueLen);
         					System.out.println("fee_code:"+ fee_code);
         					break;
         			   case 9:
         				        media_type = deByteCode.asciiz(valueLen);
         				        System.out.println("media_type:"+media_type);
         				        break;
         				case 10: 
         				        content = deByteCode.getBytes(valueLen);
         				        System.out.println("content："+new String(content));
         				        break;
         				case 11:
         				       registered_delivery = deByteCode.asciiz(valueLen);
         				       System.out.println("registered_delivery:"+registered_delivery);
         				        
         					break;
         				case 12:
         					  vcp_pwd = deByteCode.asciiz(valueLen);
         					  System.out.println("vcp_pwd:"+vcp_pwd);
         				
                                            
         				default:
         					stat ="01"; //无效的消息类型
         					return ;
         			}
         		}
         		stat = "00"; //成功
         	}
         	catch (Exception e)
         	{
                   //throw new Exception("decoding error");
                   
                   System.out.println(e.toString());
                }
    	}
    	
    	public static String vcp_id;//1
    	public static String server_code="08887";//2
    	public static String prov_code="01";//3
    	public static String server_type;//4
    	public static String dest_cpn;//5
    	public static String fee_cpn;//6
    	public static String fee_type;//7
    	public static String fee_code;//8
    	public static String media_type;//9
    	public static byte[]  content;//10
    	public static String registered_delivery;//11
    	public static String vcp_pwd;//12
    	
    	
    	public static String  stat="09";
    	public DeByteCode deByteCode;
    	
    	public String get_vcp_id()
    	{
    		return vcp_id;
    	}
    	public String get_server_code()
    	{
    		return this.server_code;
    	}
    	public String get_media_type()
    	{
    		return this.media_type;
    	}
    	public String get_fee_type()
    	{
    		return this.fee_type;
    	}
    	
    	public String get_server_type()
    	{
    		return server_type;
    	}
    	public String get_dest_cpn()
    	{
    		return dest_cpn;
    	}
    	public String get_fee_cpn()
    	{
    		return fee_cpn;
    	}
    	public String get_fee_code()
    	{
    		return fee_code;
    	}
    	public byte[] get_content()
    	{
    		return content;
    	}
    	public String get_stat()
    	{
    		return stat;
    	}
    	public String get_prov_code()
    	{
    		return prov_code;
    	}
    	public String get_delivery()
    	{
    		return registered_delivery;
    	}
    	public String get_vcp_pwd()
    	{
    		return  vcp_pwd;
    	}
}
