package org.x;
//package platformalarm;
import java.net.*;
import java.io.*;
public class ServerAlarm {
	public void Alarm(int type){
		String http = "";
		String content = "平台遇异常重启，请发信息再次确认是否启动";
		
		String phone[] = {"13859905479","13950058509"};
		if(type == 0){
			content = "平台遇异常重启，请发信息再次确认是否启动";
		}
		else if(type == 1){
			content = "平台遇异常重启第二次不成功";
		}
		else{
			content = "平台三次启动未成功，请上服务器查看。";
		}
		try{
			for(int i = 0;i < phone.length;i++){
				System.out.println("start to send msg to phone" + phone[i]);
				http = "http://202.109.249.20:2000/?msg="+java.net.URLEncoder.encode(content,"gb2312")+"&url=&phone="+phone[i]+"&spnumber=88871";
				System.out.println(http);
				URL u = new URL(http);
				InputStream in = new BufferedInputStream(u.openStream());
			}
			
		}catch(Exception e){
			System.out.print(e.toString());
		}
	}
	public void AlarmOut(){
		String http = "";
		String content = "平台重启完毕且正常";
		String phone[] = {"13859905479","13950058509"};
		try{
			for(int i = 0;i < phone.length;i++){
				System.out.println("start to send msg to phone" + phone[i]);
				http = "http://202.109.249.20:2000/?msg="+java.net.URLEncoder.encode(content,"gbk")+"&url=&phone="+phone[i]+"&spnumber=88871";
				System.out.println(http);
				URL u = new URL(http);
				InputStream in = new BufferedInputStream(u.openStream());
			}
			
		}catch(Exception e){
			System.out.print(e.toString());
		}
	}
}
