package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
import java.io.*;
import java.net.*;

/**
*
*
*/
public class VCPServer extends Thread
{
	/**
	*
	*
	*/

    protected int listenPort;
    
    public VCPServer(int aListenPort) 
    {
        listenPort = aListenPort;
     }
    /**
    *
    *
    */
    public void acceptConnections() 
    {
        try 
        {
            ServerSocket server = new ServerSocket(listenPort, 100);
             System.out.println("接收vcp信息的服务线程.... on port "+listenPort+" waiting ...");
         
            Socket incomingConnection = null;
            while (true) 
            {
                incomingConnection = server.accept();
                //System.out.println(incomingConnection);
                handleConnection(incomingConnection);
                try{
                	 //Thread.currentThread().sleep(100);
                }catch(Exception e){
                	
                }
               
              
            }
        } 
        catch (BindException e)
        {
            System.out.println("Unable to bind to port " + listenPort);
        } 
        catch (IOException e)
        {
            System.out.println("Unable to instantiate a ServerSocket on port: " + listenPort);
        }
    }
    /**
    *
    *
    */
    public void handleConnection(Socket connectionToHandle)
    {
        new Thread(new VCPConnectionHandler(connectionToHandle)).start();
    
    }
    /**
    *
    *
    */
    public void run()
    {
        VCPServer server = new VCPServer(listenPort);
        server.acceptConnections();
        
    }
   
}
