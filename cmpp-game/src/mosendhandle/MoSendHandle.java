package mosendhandle;
import mohandle.*;
import java.util.ArrayList;
public class MoSendHandle implements Runnable{
	public void run(){

		int moNum = 0;
		MoSender moSender = new MoSender();
		int sleepTime = 0;
		while(true){
			MoCounter moCounter = new MoCounter();
			System.out.println("��ʼ��鷢������");
			moCounter.setCompany("szpwd");
			moCounter.checkMo();
			moNum = moCounter.getMoNum();
			ArrayList mosList = moCounter.getMosList();
			System.out.println("the num is:" + moNum);
			if(moNum >= 30){
				System.out.println("����������ʼ���͡�����");
				moSender.setMosList(mosList);
				moSender.sentMo2Company();
				String stat = moSender.getStatus();
				moCounter.updateSendStat(stat);
			}else{
				try{

					if(mosList.size() > 0){
							sleepTime++;
						if(sleepTime*500==3000){
							System.out.println("ʱ�䵽����ʼ���͡�����");
							moSender.setMosList(mosList);
							moSender.sentMo2Company();
							String stat = moSender.getStatus();
							moCounter.updateSendStat(stat);
							sleepTime = 0;
						}

					}
					Thread.currentThread().sleep(200);
				}catch(Exception e){
					e.printStackTrace();
				}

			}
			moCounter.mydb.dbclose();

		}
	}
	public static void main(String[] args){
		MoSendHandle moSendHandle = new MoSendHandle();
		new Thread(moSendHandle).start();
	}
}
