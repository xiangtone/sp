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
//���ӳص����߳�
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
			//�ж��Ƿ��Ѿ��ر��˹������Ǿ��˳�����   
			if (cf.isCreate())    
				cf.schedule();  
			else
			{
				System.out.println("Ҫ�˳���...");
				System.exit(1); 
			}
		} 
	}
}