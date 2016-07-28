package org.x;

import java.util.Vector;

import comsd.commerceware.cmpp.cmppe_deliver_result;

public class ThreadPoolManager {
	private int maxThread;
	public Vector vector1;
	private cmppe_deliver_result cd;
	private String ISMGID = "";

	public void setMaxThread(int threadCount) {
		maxThread = threadCount;
	}

	public ThreadPoolManager(int threadCount, String ismgId) {
		// this.cd = cd;
		this.ISMGID = ismgId;
		setMaxThread(threadCount);
		System.out.println("Starting thread pool...");
		vector1 = new Vector();
		for (int i = 1; i <= maxThread; i++) {
			moOperate thread1 = new moOperate(i, this.ISMGID);
			vector1.addElement(thread1);
			thread1.start();
		}
	}

	public void process1(cmppe_deliver_result mocd) {
		int i;
		for (i = 0; i < vector1.size(); i++) {
			moOperate currentThread = (moOperate) vector1.elementAt(i);

			if (!currentThread.isRunning()) {
				currentThread.cd = mocd;
				currentThread.setRunning(true);
				return;
			}
		}
		if (i == vector1.size()) {
			System.out.println("pool is full, try in another time.");
			process1(mocd);
		}
	}
}
