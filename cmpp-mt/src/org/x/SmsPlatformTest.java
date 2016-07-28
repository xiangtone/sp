package org.x;
//package platformalarm;

public class SmsPlatformTest implements Runnable{
	ServerConnectTest sct;
	ServerRestart sr;
	ServerAlarm sa;
	public void run(){
		sct = new ServerConnectTest();//ƽ̨���Ӳ���
		sr = new ServerRestart();//ƽ̨��������
		sa = new ServerAlarm();//ƽ̨Ԥ������
		int platformint = 0;
		int msg_type = 0;
		while(true){
			sct.connectServer();
			if(!sct.connectflag){
				System.out.println("platform exception.ready to restart");
				sa.Alarm(msg_type);
				sr.Restart();
				try{
					Thread.currentThread().sleep(1*60000);
				}catch(Exception e){
					System.out.println(e.toString());
				}
				msg_type++;
				platformint = 1;
				
			}
			else{
				try{
					if(platformint == 1){
						sa.AlarmOut();
						platformint = 0;
					}
					
					System.out.println("platform all right");
					Thread.currentThread().sleep(10*60000);
				}catch(Exception e){
					System.out.println(e.toString());
				}
				
			}
		}
	}
	public static void main(String[] args){
		SmsPlatformTest spt = new SmsPlatformTest();
		new Thread(spt).start();
	}
}
