package org.x;
/**

*Copyright 2003 Xiamen Xiangtone Co. Ltd.

*All right reserved.

*/

import java.io.*;

import java.net.*;

import org.apache.log4j.Logger;

public class VCPServer extends Thread {
	private static Logger logger = Logger.getLogger(VCPServer.class);
	protected int listenPort;

	public VCPServer(int aListenPort) {
		listenPort = aListenPort;
	}

	public void acceptConnections() {

		try {

			ServerSocket server = new ServerSocket(listenPort, 1000);

			logger.debug("acceptConnections on port " + listenPort + " waiting ...");

			Socket incomingConnection = null;

			while (true) {

				incomingConnection = server.accept();

				handleConnection(incomingConnection);
			}

		}

		catch (BindException e) {
			logger.error("Unable to bind to port " + listenPort, e);
			e.printStackTrace();
		}

		catch (IOException e) {
			logger.error("Unable to instantiate a ServerSocket on port: " + listenPort, e);
			e.printStackTrace();
		}

	}

	public void handleConnection(Socket connectionToHandle) {

		new Thread(new VCPConnectionHandler(connectionToHandle)).start();

	}

	public void run() {

		VCPServer server = new VCPServer(listenPort);

		server.acceptConnections();

	}
}
