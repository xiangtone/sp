package org.x;

import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;

public class VCPServer extends Thread {
	protected int listenPort;
	private static Logger logger = Logger.getLogger(VCPServer.class);

	public VCPServer(int aListenPort) {
		listenPort = aListenPort;
	}

	public void acceptConnections() {
		try {
			logger.debug("listenPort:" + listenPort);
			ServerSocket server = new ServerSocket(listenPort, 1800);
			Socket incomingConnection = null;
			while (true) {
				incomingConnection = server.accept();
				logger.debug("incomingConnection:" + incomingConnection);
				handleConnection(incomingConnection);

			}
		} catch (BindException e) {
			logger.error("Unable to bind to port :" + listenPort, e);
		} catch (IOException e) {
			logger.error("Unable to instantiate a ServerSocket on port: " + listenPort, e);
		}
	}

	public void handleConnection(Socket connectionToHandle) {
		new Thread(new ConnectionHandler(connectionToHandle)).start();
	}

	public void run() {
		VCPServer server = new VCPServer(listenPort);
		server.acceptConnections();
	}

}
