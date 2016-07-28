package mosendhandle;
import mohandle.*;
import java.util.ArrayList;
public class SJNZMoSendHandle implements Runnable{
	public void run(){

		int moNum = 0;
		SJNZMoSender moSender = new SJNZMoSender();
		int sleepTime = 0;
		while(true){
			MoCounter moCounter = new MoCounter();
			System.out.println("开始检查发送数据");
			moCounter.setCompany("sjnz");
			moCounter.checkMo();
			moNum = moCounter.getMoNum();
			ArrayList mosList = moCounter.getMosList();
			System.out.println("the num is:" + moNum);
			if(moNum >= 30){
				System.out.println("数据量到开始发送。。。");
				moSender.setMosList(mosList);
				moSender.sendMo2Company();
				String stat = moSender.getStatus();
				moCounter.updateSendStat(stat);
			}else{
				try{
					if(mosList.size() > 0){
							sleepTime++;
						if(sleepTime*500==3000){
							System.out.println("时间到到开始发送。。。");
							moSender.setMosList(mosList);
							moSender.sendMo2Company();
							String stat = moSender.getStatus();
							moCounter.updateSendStat(stat);
							sleepTime = 0;
						}

					}
					Thread.currentThread().sleep(2000);
				}catch(Exception e){
					e.printStackTrace();
				}

			}
			moCounter.mydb.dbclose();

		}
	}
	public static void main(String[] args){
		SJNZMoSendHandle moSendHandle = new SJNZMoSendHandle();
		new Thread(moSendHandle).start();
	}
}
