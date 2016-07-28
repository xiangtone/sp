package mtlogbackup;
import java.util.*;
import java.sql.*;
public class MtLogHandle implements Runnable{
	public void run(){
		MtLogBackUpHandle mtLogBackUp = new MtLogBackUpHandle();
		Calendar c = Calendar.getInstance();
		int backUpTime = 0;
		boolean checkFlag = true;
		while(true){
			
			try{
					System.out.println("start to backup");
					mtLogBackUp.mtBackUpOperate();
					Thread.currentThread().sleep(1000*60*5);
				/*
				backUpTime = c.get(Calendar.HOUR_OF_DAY);
				if((backUpTime >=8) && (backUpTime <=10)){
					if(checkFlag){
						System.out.println("start to backup");
						mtLogBackUp.mtBackUpOperate();
					}
					checkFlag = false;
				}
				else{
					checkFlag = true;
					Thread.currentThread().sleep(1000*60*5);
				}
				*/
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		MtLogHandle mlh = new MtLogHandle();
		new Thread(mlh).start();
	}
}
