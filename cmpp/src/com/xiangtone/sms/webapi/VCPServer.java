package com.xiangtone.sms.webapi;
import java.io.*;
import java.net.*;

public class VCPServer 
{
    protected int listenPort;
    public VCPServer(int aListenPort) 
    {
        listenPort = aListenPort;
    }
    public void acceptConnections() 
    {
        try {
            ServerSocket server = new ServerSocket(listenPort, 5);
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
    public static void main(String[] args) 
    {
        VCPServer server = new VCPServer(3000);
        server.acceptConnections();
    }
}
