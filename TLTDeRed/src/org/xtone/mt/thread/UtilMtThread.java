package org.xtone.mt.thread;
import org.xtone.mo.web.Thread_MoAction;
import org.xtone.mt.web.Thread_MtAction;
public class UtilMtThread extends Thread{
	public void run(){
		 while(true)
			{	
			 System.out.println("进入线程.......");
			 Thread_MtAction a=new Thread_MtAction();
			 a.queryThreadMt();
			 Thread_MoAction b=new Thread_MoAction();
			 b.queryThreadMo();
			 try {
				 System.out.println("睡眠......");
				Thread.sleep(5*60*1000);
				System.out.println("睡醒......");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


