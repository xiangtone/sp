package comsd.commerceware.cmpp;

import java.io.PrintStream;

// Referenced classes of package com.commerceware.cmpp:
//            OutOfBoundsException
public final class cmppe_submit
{

    public cmppe_submit()
    {
        msg_id = new byte[8];
        service_id = new byte[10];
        //fee_terminal_id = new byte[21];
        fee_terminal_id = new byte[32];
        msg_src = new byte[6];
        feetype = new byte[2];
        feecode = new byte[6];
        valid_time = new byte[17];
        at_time =  new byte[17];
        src_terminal_id = new byte[21];
        //src_terminal_id = new byte[32];
        dest_terminal_id = new byte[100][32];
        linkid = new byte[20];
        //reserve = new byte[8];
    }

    public void set_msgid(byte b[])    
    {
        int j;
        for(j = 0; j < 8; j++)
        {
            msg_id[j] = b[j];
        }
    }
    
    public void set_pktotal(byte b)
    {
    	pk_total = b;
    }
    public void set_pknumber(byte b)
    {
    	pk_number = b;
    }
    public void set_registered(byte b)
    {
    	registered_delivery = b;
    }
    public void set_msglevel(byte b)
    {
    	msg_level = b;
    }
    
    public void set_serviceid(byte b[])
    {
    	int j;
        for(j = 0; j < 10; j++)
            service_id[j] = b[j];
           
        
    }
    
    public void set_feeusertype(byte b)
    {
    	fee_usertype = b;
    }
    
    public void set_feeterminalid(byte[] b)
    {
    	int j;
        for(j = 0; j < 32; j++)
            fee_terminal_id[j] = b[j];
           
        
    }
    public void set_tppid(byte b)
    {
    	tp_pid = b;
    }
    public void set_tpudhi(byte b)
    {
    	tp_udhi = b;
	}
	public void set_msgfmt(byte b)
    {
    	msg_fmt = b;
    }
    
    public void set_msgsrc(byte[] b)
    {
    	int j;
        for(j = 0; j < 6; j++)
            msg_src[j] = b[j];
    }
    
    public void set_feetype(byte[] b)
    {
    	int j;
        for(j = 0; j < 2; j++)
            feetype[j] = b[j];
    }
    
    public void set_feecode(byte[] b)
    {
    	int j;
        for(j = 0; j < 6; j++)
            feecode[j] = b[j];
    }

    public void set_validtime(byte[] b)
    {
    	int j;
        for(j = 0; j < 17; j++)
            valid_time[j] = b[j];
    	
    }
    public void set_attime(byte[] b)
    {
    	int j;
        for(j = 0; j < 17; j++)
            at_time[j] = b[j];
    	
    }
    public void set_srcterminalid(byte[] b)
    {
    	int j;
        for(j = 0; j < 21; j++)
            src_terminal_id[j] = b[j];
    	
    }
    public  void set_destusrtl(byte b)
    {
    	destusr_tl = b;
    }
    public void  set_destterminalid(byte[][] b)
       throws OutOfBoundsException
    {
    	try
    	{
        for(int i = 0; i < b.length; i++)
        {
            if(b[i][0] == 0)
               break;
            int j;
            for(j = 0; j < 32; j++)
            {
               //System.out.println(b[i].length);
            	if(j>b[i].length-1)
                {
                	dest_terminal_id[i][j] = 0x0;
                }
                else
                {
                dest_terminal_id[i][j] = b[i][j];
                }
               // if(dest_terminal_id[i][j] == 0)
                 //   break;
                // System.out.print(dest_terminal_id[i][j]+",;;");
            }

            
        }
    }
       catch(Exception e)
       {
       	   System.out.println(e.toString());
}

    }
    public void set_msglength(byte b)
    {
    	msg_length = b;
    }
    
    public void set_msgcontent(byte[] b)
    {
    	msg_content = new byte[b.length];
    	try
    	{
    	int j;
        for(j = 0; j < b.length; j++)
        {
           msg_content[j] = b[j];
    	}
       }
     catch(Exception e)
    {
    	System.out.println("dfdf");}
    }
    public void set_destterminaltype(byte b){
    	dest_terminal_type = b;
    }
    public void set_feeterminaltype(byte b){
    	fee_terminal_type = b;
    }
    public void set_linkid(byte[] b)
    {
    	
    	int j;
        for(j = 0; j < 20; j++)
           linkid[j] = b[j];
    }
    

   /* protected void printAllField()
    {
        try
        {
            String s = null;
            s = new String(icp_id);
            System.out.println("icp_id:" + s);
            s = null;
            s = new String(svc_type);
            System.out.println("svc_type:" + s);
            s = null;
            System.out.println("fee_type:" + fee_type);
            s = null;
            System.out.println("info_fee:" + info_fee);
            System.out.println("proto_id:" + proto_id);
            s = null;
            System.out.println("msg_mode:" + msg_mode);
            System.out.println("priority:" + priority);
            s = null;
            s = new String(validate);
            System.out.println("validate:" + s);
            s = null;
            s = new String(schedule);
            System.out.println("schedule:" + s);
            System.out.println("fee_utype:" + fee_utype);
            s = null;
            s = new String(fee_user);
            System.out.println("fee_user:" + s);
            s = null;
            s = new String(src_addr);
            System.out.println("src_addr:" + s);
            System.out.println("du_count:" + du_count);
            s = null;
            for(int i = 0; i < 100; i++)
            {
                if(dst_addr[i][0] == 0)
                    break;
                s = new String(dst_addr[i]);
                System.out.println("dst_addr:" + s);
            }

            System.out.println("data_coding:" + data_coding);
            System.out.println("sm_len:" + sm_len);
            s = null;
            s = new String(short_msg, "GB2312");
            System.out.println("short_msg:" + s);
        }
        catch(Exception _ex) { }
    }
*/
    protected byte msg_id[];
    protected byte pk_total;
    protected byte pk_number;
    protected byte registered_delivery;
    protected byte msg_level;
    protected byte service_id[];
    protected byte fee_usertype;
    protected byte fee_terminal_id[];
    protected byte tp_pid;
    protected byte tp_udhi;
    protected byte msg_fmt;
    protected byte msg_src[];
    protected byte feetype[];
    protected byte feecode[];
    protected byte valid_time[];
    protected byte at_time[];
    protected byte src_terminal_id[];
    protected byte destusr_tl;
    protected byte dest_terminal_id[][];
    protected byte msg_length;
    protected byte msg_content[];
    //change at 061123
    protected byte linkid[];
    protected byte fee_terminal_type;
    protected byte dest_terminal_type;
    //
    //protected byte reserve[];
}
