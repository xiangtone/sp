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
public class TestServer extends Thread
{
	/**
	*
	*
	*/

    protected int listenPort;
    
    public TestServer(int aListenPort) 
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
            ServerSocket server = new ServerSocket(listenPort, 5);
             System.out.println("����������Ϣ�ķ����߳�.... on port "+listenPort+" waiting ...");
         
            Socket incomingConnection = null;
            while (true) 
            {
                incomingConnection = server.accept();
                //System.out.println(incomingConnection);
                //handleConnection(incomingConnection);
                try{
                	 Thread.currentThread().sleep(10000);
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
            System.out.println("Unable to instantiate a TestServerSocket on port: " + listenPort);
        }
    }
    public void run()
    {
        TestServer testserver = new TestServer(listenPort);
        testserver.acceptConnections();
        
    }
   
}
