// Decompiled by DJ v2.9.9.61 Copyright 2000 Atanas Neshkov  Date: 2003-1-22 10:19:47
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cmppe_deliver_result.java

package comsd.commerceware.cmpp;

import java.io.PrintStream;

// Referenced classes of package com.commerceware.cmpp:
//            cmppe_result

public final class cmppe_deliver_result extends cmppe_result
{

    public cmppe_deliver_result()
    {
        msg_id = new byte[8];
        //submit_time = new byte[10];
        //done_time = new byte[10];
        //src_addr = new byte[21];
        //dst_addr = new byte[21];
        //svc_type = new byte[10];
       // short_msg = new byte[161];
       // reserve = new byte[8];
        super.pack_id = 5;
    }

    

   public void readInBytes(byte[] b) throws Exception
    {
      try{
    	  System.out.println("the b length is:" + b.length);
         deByteCode=new DeByteCode(b);

         msg_id = deByteCode.getBytes(8);
         
         dst_addr = deByteCode.asciiz(21);
         svc_type = deByteCode.asciiz(10);
         System.out.println("dst_addr" + dst_addr);
         //System.out.println("svc_type:"+svc_type);
         tp_pid = deByteCode.int8();
         tp_udhi = deByteCode.int8();
         data_coding = deByteCode.int8();
        // System.out.println("data_coding:"+data_coding);
         //src_addr = deByteCode.asciiz(21);
         src_addr = deByteCode.asciiz(32);
         src_type = deByteCode.int8();
         System.out.println("src_addr:"+src_addr);
         registered_delivery = deByteCode.int8();
         System.out.println("registered_delivery:"+registered_delivery);
         sm_len = deByteCode.int8();
         int bb = sm_len;
         if(sm_len<0)
                 bb+=256;
         System.out.println("sm_len:"+bb);
         if(registered_delivery ==0)
         {
        	 //short_msg = deByteCode.getBytes(bb);
        	 short_msg = deByteCode.getBytes(bb);
        	   linkid = deByteCode.asciiz(20);
		         if(linkid==null){
		        	 linkid = "";
		         }
          }
          else
          {
          	msg_id2 = deByteCode.getBytes(8);
          	//System.out.println("msg_id2:"+new String(msg_id2));
          	
          	//for(int i=0;i<msg_id2.length;i++)
               //System.out.print(","+msg_id2[i]);
          	status_from_rpt = deByteCode.getBytes(7);
          	
          	submit_time = deByteCode.getBytes(10);
          	//System.out.println("submit_time:"+new String(submit_time));
          	done_time = deByteCode.getBytes(10);
          	////////////////////////change at 061116
          	//dest_cpn = deByteCode.getBytes(21);
          	dest_cpn = deByteCode.getBytes(32);
          	///////////////////////
          	smsc_seq = deByteCode.getBytes(4);	
          	//linkid = deByteCode.asciiz(20);
          	linkid = deByteCode.asciiz(20);
       			if(linkid==null){
		        	 linkid = "";
		         }
          } 
         //System.out.println("deByteCode length is:" + deByteCode.length);
         //System.out.println("message length is:" + short_msg.length);
         //reserve = deByteCode.asciiz(8);
         //byte[] linkids = deByteCode.getBytes(20);
       		
         System.out.println("linkid is" + linkid);
         STAT = 0;
         }
         catch (Exception e)
         {
        	 e.printStackTrace();
            throw new Exception("decoding error" + e.toString());
         }
    }
    public int seq;
    //public  int msg_id0;
   // public int msg_id1;
    public static byte msg_id[];
    public  static String dst_addr;
    public  static String svc_type;
    public  static  byte tp_pid;
    public  static byte tp_udhi;
    public  static byte data_coding;
    public  static String src_addr;
    public  static byte src_type;//add at 061116
    public  static byte registered_delivery;
    public  static byte sm_len;
    public  static byte short_msg[];
    public  String sshort_msg;//
    public static String linkid;//add at 061116
    public String reserve;
 
    
    public byte proto_id;
    public byte status_rpt;
    public byte priority;
   
    public DeByteCode deByteCode;
    
    public static byte msg_id2[];
    public  static byte status_from_rpt[];
    public static byte submit_time[];
    public static byte done_time[];
    public static byte dest_cpn[];
    public byte smsc_seq[];
    
    public static int STAT=-1;
    
    public byte get_tp_udhi()
    {
    	return tp_udhi;
    }
    public byte get_tp_pid()
    {
    	return tp_pid;
    }
    
    public String getSPCode()
    {
    	 return dst_addr;
    }
    public String getCpn()
    {
    	return src_addr;
    }
    
    public byte getsrcType(){
    	return src_type;
    }
    
    public byte getFmt()
    {
    	return data_coding;
    }
    
    public  byte getLen()
    {
    	 return sm_len;
    }
    public byte[] getMessage()
    {
    	return short_msg;
    	
    }
    public String getServerType()
    {
    	return svc_type;
     }
     public String getLinkId(){
     	return linkid;	
     }
     public byte getRegistered_delivery()
     {
     	  return registered_delivery;
     }
     
     public String get_msg_id()
     {
     	return  Bytes8ToLong(msg_id2);
     	//return msg_id2;
     }
     public String get_stat()
     {
     	return  new String(status_from_rpt);
     }
     
     public String get_submit_time()
     {
     	return  new String(submit_time);
     }
      public String get_done_time()
     {
     	return  new String(done_time);
     }
       public String get_dest_cpn()
     {
     	return  new String(dest_cpn);
     }
     
     
     public  String Bytes8ToLong(byte abyte0[])
    {
    		for(int i = 0;i < abyte0.length;i++){
    			System.out.print(abyte0[i] + ",");	
    		}
        long l = (0xff & abyte0[0]) << 56 | (0xff & abyte0[1]) << 48 | (0xff & abyte0[2]) << 40 | (0xff & abyte0[3]) << 32 | (0xff & abyte0[4]) << 24 | (0xff & abyte0[5]) << 16 | (0xff & abyte0[6]) << 8 | 0xff & abyte0[7];
        System.out.println("l:"+l);
        return (new Long(l)).toString();
    }
     
     public void printAll()
     {
     	System.out.println("---data_coding : "+ data_coding);
     	System.out.println("---cpn: "+ src_addr);
     	System.out.println("---spcode:"+dst_addr);
     	System.out.println("---short_msg:"+ short_msg);
     	System.out.println("---svc_type:"+svc_type);
     	System.out.println("---registered_delivery:"+registered_delivery);
     	System.out.println("---tp_udhi:"+ tp_udhi);
     	System.out.println("---linkid:"+ linkid);
     }
    	

    
}
