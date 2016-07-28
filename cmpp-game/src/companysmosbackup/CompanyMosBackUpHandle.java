package companysmosbackup;
import java.util.*;
public class CompanyMosBackUpHandle implements Runnable{
	public void run(){
		
		while(true){
			MosHandle mosHandle = new MosHandle();
			try{
				
				mosHandle.backUpMos();
				mosHandle.mydb.close();
				Thread.currentThread().sleep(1000 * 30);
			}catch(Exception e){
				try{
						mosHandle.mydb.close();
				}catch(Exception e1){
					
				}
			}
			finally{
				try{
						mosHandle.mydb.close();
				}catch(Exception e1){
					
				}
			}
		}
	}
	public static void main(String[] args){
		CompanyMosBackUpHandle cmbh = new CompanyMosBackUpHandle();
		new Thread(cmbh).start();
	}
}
