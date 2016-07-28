/*
 * Created on 2006-9-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ThreadPool1;
import order.*;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.io.*;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SimpleThread extends Thread{ 
	 private boolean runningFlag; 
	 private String argument; 
	 Socket s;
	 int order_type;
 public boolean isRunning(){ 
 	 return runningFlag; 
 } 
 public synchronized void setRunning(boolean flag){ 
	 runningFlag = flag; 
	 if(flag) 
	 this.notify(); 
 } 
 
 public String getArgument(){ 
 	return this.argument; 
 } 
 public void setArgument(String string){ 
 	argument = string; 
 } 
 public void send_orderesult(){
 	try{
 		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
 		pw.flush();
 		pw.close();
 	}catch(IOException e){
 		System.out.println(e.toString());
 	}
 	
 }
 public SimpleThread(int threadNumber,Socket _s){ 
	 runningFlag = false; 
	 this.s = _s;
	 System.out.println("thread " + threadNumber + "started."); 
 } 
 
public synchronized void run(){ 
		 try{ 
			 while(true){ 
				 if(!runningFlag){ 
				 	this.wait(); 
				 } 
				 else{ 
				 //System.out.println("processing " + getArgument() + "... done."); t
				 	OrderOperate oo = new OrderOperate(getArgument());
				 	order_type = oo.Order_Str();
				 	System.out.println("the order type is:" + order_type);
				 //sleep(5000); 
				 	System.out.println("Thread is sleeping..."); 
				 	setRunning(false); 
				 } 
			 } 
		 }catch(InterruptedException e){ 
		 	System.out.println("Interrupt"); 
		 } 
	 }//end of run() 
 }//end of class SimpleThread 
