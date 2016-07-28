package smsubmitmsgid;
public class SmsSubmitMsgidThread implements Runnable{
	public void run(){
		while(true){
			try{
			SmsSubmitMsgidHandle smsSubMsgIdHandle = new SmsSubmitMsgidHandle();
			smsSubMsgIdHandle.UpdateSubmitMsgId();	
			
				Thread.currentThread().sleep(1000 * 5);
			}catch(Exception e){
				
			}
		}	
	}
	public static void main(String[] args){
			SmsSubmitMsgidThread smsMsgIdThread = new SmsSubmitMsgidThread();
			new Thread(smsMsgIdThread).start();
	}
}