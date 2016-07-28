/* 
* Created on 2003-5-13 
* 
* To change the template for this generated file go to 
* Window>Preferences>Java>Code Generation>Code and Comments 
*/
package com.xiangtone.sql;
/** 
* @author youyongming 
* 
*/
//连接池调度线程
public class FactoryMangeThread implements Runnable 
{ 
	ConnectionFactory cf = null; 
	long delay = 1000; 
	public FactoryMangeThread(ConnectionFactory obj) 
	{  
		cf = obj; 
	} 
	/* (non-Javadoc)  
	* @see java.lang.Runnable#run()  
	*/ public void run() 
	{  
		while(true)
		{   
			try
			{    
				Thread.sleep(delay);   
			}   
			catch(InterruptedException e)
			{
			}   
			System.out.println("eeeee");  
			//判断是否已经关闭了工厂，那就退出监听   
			if (cf.isCreate())    
				cf.schedule();  
			else
			{
				System.out.println("要退出了...");
				System.exit(1); 
			}
		} 
	}
}