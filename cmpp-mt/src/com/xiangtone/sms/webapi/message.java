package com.xiangtone.sms.webapi;
import java.io.*;
import java.net.Socket;
public class message
{
  
    public message(){}
	
    protected boolean readHead(DataInputStream in, sm_pack p)
        throws Exception
    {
        try
        {
            byte[] m_type = new byte[4];
            byte[] m_len = new byte[4];
            int i;
            for(i=0;i<4;i++)
            {
	           m_type[i] = (byte)in.read();
	       //System.out.println("sss"+m_type[i]);
            }
             p.pk_head.pk_type = Integer.parseInt(new String(m_type));
            System.out.println("p.pk_head.pk_type:"+p.pk_head.pk_type);
            for(i=0;i<4;i++) {
            	 m_len[i] = (byte)in.read();  
			}
            p.pk_head.pk_len = Integer.parseInt(new String(m_len));
            
            System.out.println("readHead ...pk_cmd:"+p.pk_head.pk_type);
        }
        catch(Exception e)
        {
            throw e;
        }
        return true;
    }
    protected void sendHeader(sm_header ch, DataOutput out)
        throws Exception
    {
        try
        {
        ByteCode bc = new ByteCode(4);
        bc.addAsciiz("0002",4);
        bc.addAsciiz("0016",4);
 
        
        out.writeChars(String.valueOf(bc.getChars()));
        }
        catch(Exception e)
        {

            System.out.println("send Head Exception"+e.getMessage());
            throw e;
        }
    }
    
    public void readPa(conn_desc con)
    {
    	
        sm_result sr = null;
       
        try
        {
            sr = readResPack(con);
            
            switch(sr.pack_type)
            {
            case 1: //StateCode.sm_submit
                sm_submit_result ssr = (sm_submit_result)sr;
                System.out.println("----receiver vcp ------stat="+ssr.stat);
                System.out.println("----receiver vcp submit message------");
                sm_send_submit_ack(con,ssr.stat);
                break;
               
            default:
                System.out.println("---------Error packet-----------");
                break;
            }
        }
        catch(Exception e)
        {  System.out.println("errror...");
           System.out.println("erere"+e.getMessage());
            e.printStackTrace();
            System.out.println("have a exception");
        }
    }
    
    public void sm_send_submit_ack(conn_desc conn,String ack_code)
        throws Exception
    {
        sm_header ch = new sm_header();
        try
        {
            //DataOutputStream out = new DataOutputStream(conn.sock.getOutputStream());
             //PrintWriter pw = new PrintWriter(out,true);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conn.sock.getOutputStream())), true);
            ch.pk_type = 2;//sm_submit_ack
            ch.pk_len = 8+2+4+2;
         //   sendHeader(ch, out);
            
            //ByteCode ack = new ByteCode(2);
           // ack.addAsciiz(StateCode.ACK_CODE,2); //add Type
           // ack.addAsciiz("0002",4) ; //add length
         //   ack.addAsciiz(ack_code,2); //add value string
              System.out.println("send:::"+ack_code);
      //      out.writeBytes("123456\n");
        pw.println(ack_code);    
        pw.flush();
        }
        catch(Exception e)
        {
            throw e;
        }
       
    }
    
    public sm_result readResPack(conn_desc conn)
        throws Exception, UnknownPackException
    {
        DataInputStream in = null;
        sm_result sr = new sm_result();
        sm_pack pack = new sm_pack();
        
        in = new DataInputStream(conn.sock.getInputStream());
       
    /*   byte[] temp1 = new byte[133]; 	
	for(int i=0; i<133; i++) {
		temp1[i] = (byte)in.read();	
	}
	System.out.println(new String(temp1, 0, 133));
*/
        readHead(in, pack); //read header
            

        switch(pack.pk_head.pk_type)
        {
        
        	case 1: 
        	    System.out.println("------- Case 1 -------");
        	    sm_submit_result ssr = new sm_submit_result();
        	    try
        	    {
        	    	  System.out.println("total_len:" +pack.pk_head.pk_len);
        	          byte packbuf[] = new byte[pack.pk_head.pk_len -8];
					 System.out.println("bodylen:"+packbuf.length);      
                    for(int i=0;i<packbuf.length;i++)
        	          {
        	           	packbuf[i]=(byte)in.read();
        	           	//System.out.println(i+":"+packbuf[i]+",");
        	          }
					 System.out.println(new String(packbuf));
        	          ssr.readInBytes(packbuf); // 处理信息体
        	          ssr.pack_type = 1;
        	          return ssr;
        	    }catch (Exception e1)
        	    {
        	            throw new UnknownPackException();
        	    }
        	    
        	 default: 
        	        //throw new UnknowPackException();
        	 	break;
         

        }

        UnknownPackException un = new UnknownPackException();
        throw un;
    }


}
