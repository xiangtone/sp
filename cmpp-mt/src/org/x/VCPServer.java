package org.x;
/**

*Copyright 2003 Xiamen Xiangtone Co. Ltd.

*All right reserved.

*/

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class VCPServer extends Thread

{

	private final static Logger LOG = Logger.getLogger(VCPServer.class);

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

			ServerSocket server = new ServerSocket(listenPort, 1000);

			LOG.info("acceptConnections on port " + listenPort + " waiting ...");

			Socket incomingConnection = null;

			while (true)

			{

				incomingConnection = server.accept();

				// System.out.println(incomingConnection);

				handleConnection(incomingConnection);
			}

		}

		catch (BindException e)

		{
			e.printStackTrace();
			// writeLog(e.toString());
			System.out.println("Unable to bind to port " + listenPort);

		}

		catch (IOException e)

		{
			e.printStackTrace();
			// writeLog(e.toString());
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
	/*
	 * private void writeLog(String logStr){//�����쳣��¼
	 * 
	 * Logger myLogger = Logger.getLogger("MsgSendLogger"); Logger mySonLogger =
	 * Logger.getLogger("myLogger.mySonLogger");
	 * PropertyConfigurator.configure("log4j.properties"); //logStr = this.compUrl
	 * + "\n" + logStr; myLogger.info(logStr); }
	 */
}
