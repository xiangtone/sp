package com.xiangtone.sms.webapi;
public class sm_submit_result extends sm_result
{
	public sm_submit_result()
	{
		
	}
	
	
    	public void readInBytes(byte[] b) //throws Exception
    	{
    		try 
    		{
         		deCharCode=new DeByteCode(b);
         		while(deCharCode.offset<b.length)
         		{
         			String type = deCharCode.asciiz(2);
         			String len = deCharCode.asciiz(4);
         			int valueLen =Integer.parseInt(len);
         			int itype = Integer.parseInt(type);
                                 //System.out.print("itype:"+itype);
         			switch(itype)
         			{
         				case 1:
         					vcp_id = deCharCode.asciiz(valueLen);
         					break;
         				case 2:
         					server_code = deCharCode.asciiz(valueLen);
         					System.out.println("server_code:"+server_code);
         					break;
         				case 3:
         				        media_type = deCharCode.asciiz(valueLen);
         				        System.out.println("media_type:"+media_type);
                              
                                                 break;
         				case 4:
         					server_type = deCharCode.asciiz(valueLen);
         					System.out.println("server_type:"+server_type);
         					break;
         				case 5:
         					dest_cpn = deCharCode.asciiz(valueLen);
         					System.out.print("dest_cpn:"+dest_cpn);
         					break;
         				case 6:
         					fee_cpn  = deCharCode.asciiz(valueLen);
         					System.out.println("fee_cpn:"+ fee_cpn);
         					break;
         				case 7: 
         					fee_type = deCharCode.asciiz(valueLen);
         					System.out.println("fee_type:"+ fee_type);
         					break;
         			
         				case 8:
         					fee_code = deCharCode.asciiz(valueLen);
         					System.out.println("fee_code:"+ fee_code);
         					break;
         			
         				case 9: 
         				        content = deCharCode.getBytes(valueLen);
         				        System.out.println("content："+new String(content));
         				        
         					break;
         				case 11: prov_code = deCharCode.asciiz(valueLen);
         				         System.out.println("prov_code:"+ prov_code);
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
                   //throw new Exception("decoding error");
                   
                   System.out.println(e.toString());
                }
    	}
    	
    	public static String vcp_id;
    	public static String server_code="08887";
    	public static String media_type;
    	public static String server_type;
    	public static String dest_cpn;
    	public static String fee_cpn;
    	public static String fee_type;
    	public static String fee_code;
    	public static String prov_code="01";
    	public static String  stat="09";
    	public static byte[]  content;
    	public DeByteCode deCharCode;
    	
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
    	
}
