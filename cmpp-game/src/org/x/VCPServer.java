package org.x;
import java.io.*;
import java.net.*;

public class VCPServer  extends Thread
{
    protected int listenPort;
    public VCPServer(int aListenPort) 
    {
        listenPort = aListenPort;
    }
    public void acceptConnections() 
    {
        try {
        	System.out.println(listenPort);
            ServerSocket server = new ServerSocket(listenPort, 1800);
            Socket incomingConnection = null;
            while (true) 
            {
                incomingConnection = server.accept();
                System.out.println(incomingConnection);
                handleConnection(incomingConnection);
              
            }
        } catch (BindException e) {
            System.out.println("Unable to bind to port " + listenPort);
        } catch (IOException e) {
            System.out.println("Unable to instantiate a ServerSocket on port: " + listenPort);
        }
    }
    public void handleConnection(Socket connectionToHandle) {
        new Thread(new ConnectionHandler(connectionToHandle)).start();
    
    }
     public void run()
    {
        VCPServer server = new VCPServer(listenPort);
        server.acceptConnections();
        
    }
   
}
