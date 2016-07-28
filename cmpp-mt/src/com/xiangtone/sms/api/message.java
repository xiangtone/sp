/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;
import java.io.*;
import java.net.Socket;
public class message
{
  
    public message(){}
    
    
    
     //connect xtsms platform server
	    public void connect_to_server(String host, int port, conn_desc conn)
        	throws IOException
	   {
			Socket s = null;
			try
			{
	    		s = new Socket(host, port);
	    		s.setSoTimeout(0x927c0);
            		System.out.println(s.toString());
			}
			catch(IOException e)
			{
	   	 	  throw e;
			}
			conn.sock = s;
	
		}



        //disconnect from xtsms platform server
		public void disconnect_from_server(conn_desc conn)
		{
			try
			{
				conn.sock.getOutputStream().close();
				conn.sock.getInputStream().close();
				conn.sock.close();
				conn.sock = null;
				//conn.sock.close();
			}
			catch(Exception e)
			{
				return;
			}
		}	
		
		// send sm_deliver
		 public void send_sm_deliver(conn_desc conn,sm_deliver deliver)
		throws IOException
		{
			DataOutputStream out = null;
			try
			{
				out = new DataOutputStream(conn.sock.getOutputStream());
	
				byte[] buf = deliver.getBytes(); //信息体 message body
				int body_len = buf.length; //信息体长度
				byte[] header = new byte[8]; //信息头
				ByteCode bc = new ByteCode(8);
			    System.out.println(8+body_len);
				bc.AddInt(8+body_len);    //信息头  add total length 
			    bc.AddInt(StateCode.sm_deliver); //信息头 add  message type 
			    //bc.AddInt(3);
			    bc.AddBytes(buf);//信息体 add message body
		    	out.write(bc.getBytes());
		    	out.flush();
			
			}catch(IOException e)
			{
	               out = null;
	               throw e;
			}
			 catch(Exception e)
			{
				System.out.println(e.toString());
			}
		
		}

	
	// send sm_submit
	  public void send_sm_submit(conn_desc conn,sm_submit sub)
		throws IOException
		{
			DataOutputStream out = null;
			try
			{
				out = new DataOutputStream(conn.sock.getOutputStream());
	
				byte[] buf = sub.getBytes(); //信息体 message body
				int body_len = buf.length; //信息体长度
				byte[] header = new byte[8]; //信息头
				ByteCode bc = new ByteCode(8);
			
				bc.AddInt(8+body_len);    //信息头  add total length 
			    bc.AddInt(StateCode.sm_submit); //信息头 add  message type 
			    //bc.AddInt(3);
			    bc.AddBytes(buf);//信息体 add message body
		    	out.write(bc.getBytes());
		    	out.flush();
			
			}catch(IOException e)
			{
	               out = null;
	               throw e;
			}
			 catch(Exception e)
			{
				System.out.println(e.toString());
			}
		
		}
	   
	// read message head
    protected boolean readHead(DataInputStream in, sm_pack p)
        throws IOException
    {
        try
        {
            p.pk_head.pk_len = in.readInt();
            p.pk_head.pk_cmd = in.readInt();
            System.out.println("readHead ...pk_cmd:"+p.pk_head.pk_cmd);
        }
        catch(IOException e)
        {
            throw e;
        }
        return true;
    }
    //send message head
    protected void sendHeader(sm_header ch, DataOutput out)
        throws IOException
    {
        try
        {
        ByteCode bc = new ByteCode(4); //4 bytes of head 

        bc.AddInt32(ch.pk_len);
        bc.AddInt32(ch.pk_cmd);
        
        out.write(bc.getBytes());
        }
        catch(IOException e)
        {

            System.out.println("send Head Exception"+e.getMessage());
            throw e;
        }
    }
    
    //read respack
    public void readPa(conn_desc conn)
    {
    	
        sm_result sr = null;
       
        try
        {
            sr = readResPack(conn);
            
            switch(sr.pack_cmd)
            {
            case 1: //StateCode.sm_submit
                sm_submit_result ssr = (sm_submit_result)sr;
                System.out.println("----receiver vcp ------stat="+ssr.stat);
                System.out.println("----receiver vcp submit message------");
                sm_send_submit_ack(conn,ssr.stat);
                break;
            case 2: //StatCode.sm_submit_ack
            	sm_submit_ack_result ssra = (sm_submit_ack_result)sr;
            	System.out.println("--------"+ssra.stat);
            	break;
            case 3: 
                 sm_deliver_result sdr = (sm_deliver_result)sr;
                 System.out.println(" ------receiver platform-----stat ="+ sdr.stat);
            	 sm_send_deliver_ack(conn,sdr.stat);
                 break;
            case 4:
            	  sm_deliver_ack_result sdar = (sm_deliver_ack_result)sr;
            	  System.out.println("---------deliver_ack-----:"+sdar.stat);
            	  break;
            	
               
            default:
                System.out.println("---------Error packet-----------");
                break;
            }
        }
        catch(Exception e)
        {
           System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("have a exception");
        }
        finally
        {
        	try
        	{
        		if(conn.sock != null)
        			conn.sock.close();
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
    }
    
    //send sm_submit_ack 
    public void sm_send_submit_ack(conn_desc conn,String ack_code)
        throws Exception
    {
        sm_header ch = new sm_header();
        try
        {
            DataOutput out = new DataOutputStream(conn.sock.getOutputStream());
            ch.pk_cmd = 2;//sm_submit_ack
            int len1 = ack_code.getBytes().length;
            ch.pk_len = 8+3+len1;
            sendHeader(ch, out);
            
            ByteCode ack = new ByteCode(3);
            ack.AddByte(StateCode.ACK_CODE); //add Type
            ack.AddInt16((short)(3+len1)) ; //add length
            ack.addAsciiz(ack_code,len1); //add value string
            byte[] b = ack.getBytes();
            for(int i=0;i<b.length;i++)
            	System.out.print(b[i]+",");
            out.write(ack.getBytes());
            
        }
        catch(Exception e)
        {
            throw e;
        }
       
    }
     //send sm_deliver_ack 
    public void sm_send_deliver_ack(conn_desc conn,String ack_code)
        throws Exception
    {
        sm_header ch = new sm_header();
        try
        {
            DataOutput out = new DataOutputStream(conn.sock.getOutputStream());
            ch.pk_cmd = 4;//sm_deliver_ack
            int len1 = ack_code.getBytes().length;
            ch.pk_len = 8+3+len1;
            sendHeader(ch, out);
            
            ByteCode ack = new ByteCode(3);
            ack.AddByte(StateCode.ACK_CODE); //add Type
            ack.AddInt16((short)(3+len1)) ; //add length
            ack.addAsciiz(ack_code,len1); //add value string
            byte[] b = ack.getBytes();
            for(int i=0;i<b.length;i++)
            	System.out.print(b[i]+",");
            out.write(ack.getBytes());
            
        }
        catch(Exception e)
        {
            throw e;
        }
       
    }
    
    public sm_result readResPack(conn_desc conn)
        throws IOException, UnknownPackException
    {
        DataInputStream in = null;
        sm_result sr = new sm_result();
        sm_pack pack = new sm_pack();
        
        in = new DataInputStream(conn.sock.getInputStream());
       
        	
        readHead(in, pack); //read header
        //System.out.println("total_len:" +pack.pk_head.pk_len);
        byte packbuf[] = new byte[pack.pk_head.pk_len -8];
        in.read(packbuf); //read body message    
				////////////////////add at 061206
				//for(int k = 0;k < packbuf.length;k++){
				//	System.out.print(packbuf[k] + " ");	
				//}
				//////////////////////////////////
        switch(pack.pk_head.pk_cmd)
        {
        
        	case 1: 
        	    System.out.println("------- Case 1 -------");
        	    sm_submit_result ssr = new sm_submit_result();
        	    try
        	    {
        	          ssr.readInBytes(packbuf); // 处理信息体
        	          ssr.pack_cmd = 1;
        	          return ssr;
        	    }catch (Exception e1)
        	    {
        	            throw new UnknownPackException();
        	    }
        	    //break;
        	case 2:
        	     System.out.println("-------case 2--------");
        	     sm_submit_ack_result ssra = new sm_submit_ack_result();
        	     try
        	     {
        	     	ssra.readInBytes(packbuf);
        	     	ssra.pack_cmd=2;
        	     	return ssra;
        	    }catch(Exception e)
        	    {
        	    	throw new UnknownPackException();
        	    }
        	   
        	case 3:
        		System.out.println("-------Case 3 --------");
        		sm_deliver_result sdr = new sm_deliver_result();
        		try
        		{
        	        sdr.readInBytes(packbuf);
        	        sdr.pack_cmd =3;
        	        return sdr;
        	    }catch(Exception e2)
        	    {
        	    	throw new UnknownPackException();
        	    }
        	   // break;
            case 4:
            		System.out.println("-----case 4------");
            		sm_deliver_ack_result adar = new sm_deliver_ack_result();
            		try
            		{
            			adar.readInBytes(packbuf);
            			adar.pack_cmd=4;
            			return adar;
            		}catch(Exception e)
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