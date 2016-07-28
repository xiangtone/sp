package tableoperate;
import java.util.*;
public class TableHandle implements Runnable{
	
	public void run(){
		int hour = 0;
		String dbName = "sms_platform";
		String tableName = "companysms_reportlog";
		boolean operateFlag = true;
		try{
			while(true){
				hour = checkHour();
				System.out.println("now the hour is:" + hour);
				if(hour == 17){
					if(operateFlag){
						Long startTime = System.currentTimeMillis();
						TableOperate tableOperate = new TableOperate();
						//tableOperate.setDbName(dbName);
						tableOperate.setTableName(tableName);
						tableOperate.connectDb();
						tableOperate.reNameTableName();
						tableOperate.createTable();
						tableOperate.dbClose();
						operateFlag = false;
						Long endTime = System.currentTimeMillis();
						System.out.println("use time is:" + (endTime - startTime)/1000);
					}else{
						System.out.println("has operated");
					}
				}
				else{
					operateFlag = true;
					System.out.println("it is not Table Handle Time");
				}
				Thread.currentThread().sleep(1000 * 6);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private int checkHour(){
		Calendar calendar = Calendar.getInstance();
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		return currentHour;
	}
	public static void  main(String[] args){
		TableHandle tableHandle = new TableHandle();
		new Thread(tableHandle).start();
		System.out.println("finished");
	}
}
