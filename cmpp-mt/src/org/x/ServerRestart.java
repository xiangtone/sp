package org.x;
//package platformalarm;
import java.util.*;
import java.net.*;
import java.io.*;
public class ServerRestart {
	public void Restart(){
		try{
			Runtime r = Runtime.getRuntime();
			Process p = r.exec("java sms_public");
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
		
}
