package org.x;
//package platformalarm;
import java.net.*;
import java.io.*;
public class ServerConnectTest {
	public static boolean connectflag = false;
	public void connectServer(){
		try{
			Socket s = new Socket("202.109.249.20",8110);
			if(s!=null){
				connectflag = true;
				s.close();
			}
			else{
				connectflag = false;
			}
		}catch(Exception e){
			System.out.println(e.toString());
			connectflag = false;
		}
	}
}
