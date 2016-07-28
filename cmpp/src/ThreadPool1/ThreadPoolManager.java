/*
 * Created on 2006-9-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ThreadPool1;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.util.*; 
import java.net.*;

public class ThreadPoolManager 
{ 

private int maxThreads; 
private int livethreadnum;
public Vector vector1; 
int j;
public void setMaxThread(int threadCount) 
{ 
maxThreads = threadCount; 
} 

public ThreadPoolManager(int threadCount) 
{ 
setMaxThread(threadCount); 
System.out.println("Starting thread pool..."); 
vector1= new Vector(); 
/*
for(int i = 1; i <= 10; i++) 
{ 
SimpleThread thread1 = new SimpleThread(i); 
vector1.addElement(thread1); 
thread1.start(); 
} 
*/
} 

public void process1(String argument,Socket s) 
{ 
int i; 
int threadnum = vector1.size();
if(threadnum == 0){
	System.out.println("To create the first thread");
		SimpleThread firstthread = new SimpleThread(livethreadnum++,s); 
		vector1.addElement(firstthread); 
		firstthread.start();
		firstthread.setArgument(argument); 
		firstthread.setRunning(true);
		firstthread.send_orderesult();
		return; 
}
else if(threadnum < maxThreads){//在线程满且线程数小于最大线程数时，再建新的线程
		//int j;
			for(i = 0; i < vector1.size(); i++){ 
				SimpleThread currentThread = (SimpleThread)vector1.elementAt(i); 
				if(!currentThread.isRunning()) 
				{ 
					System.out.println("Thread "+ (i+1) +" is processing:" + argument); 
					currentThread.setArgument(argument); 
					currentThread.setRunning(true); 
					currentThread.send_orderesult();
					return; 
				}
			}
	if(i == vector1.size()) 
	{ 
		System.out.println("Create a new thread");
		SimpleThread newthread = new SimpleThread(livethreadnum++,s); 
		vector1.addElement(newthread); 
		newthread.start(); 
		newthread.setArgument(argument); 
		newthread.setRunning(true); 
	//System.out.println("pool is full, try in another time.");
	//process1(argument); 
	} 
}
else{ //当全部线程都创建时，有新输入逐个检查是否有空的线程，有：分配，无：等待
	
	int freenum = 0;
	
 	while(true){
 			System.out.println("::::::::::::::"+ j);
 			if(j == maxThreads-1){
 					j = 0;
 					System.out.println("All thread is working,wait a moment please!!");
 				}
 			SimpleThread freeThread = (SimpleThread)vector1.elementAt(j++);
 			if(!freeThread.isRunning()) 
				{ 
					System.out.println("Thread "+ (j+1) +" is processing:" + argument); 
					freeThread.setArgument(argument); 
					freeThread.setRunning(true); 
					freeThread.send_orderesult();
					return; 
				}
			else{
					try{
						Thread.currentThread().sleep(1000);
					}catch(Exception e){
							System.out.println(e.toString());
						}
					
				}	
 	}
}
}//end process1
}//end of class ThreadPoolManager 
