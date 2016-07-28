/*
 * Created on 2006-9-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ThreadPool1;
import java.io.*;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestThreadPool {
	public static void main(String[] args){ 
		try{ 
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
			String s; 
			//String[] s1 = new String[10];
			/*
			for(int i = 0;i< s1.length;i++){
				s1[i] = "hello" + i;
			}
			*/
			int max = Integer.parseInt(args[0]);
			ThreadPoolManager manager = new ThreadPoolManager(max); 
		
			while((s = br.readLine()) != null){ 
				//manager.process1(s); 
			}
			/*
			for(int i = 0;i< s1.length;i++){
				manager.process1(s1[i]);
			}
			*/
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
}
