package org.x;
/*
 * Created on 2006-11-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import comsd.commerceware.cmpp.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.xiangtone.util.ConfigManager;
import com.xiangtone.util.MailUtil;

public class CMPPSingleConnect {
	private  static CMPPSingleConnect cmppcon = null;
    private  CMPP p = new CMPP();
  	public   static conn_desc con = new conn_desc();
  	private  CmppLogin cl = new CmppLogin();
  	public static int count=0;
  	private ConfigManager configManager=ConfigManager.getInstance();
  	private int maxConnect=Integer.parseInt(configManager.getConfigData("max_connect"));
  	
  	private CMPPSingleConnect(){
  		connectIsmg();
  	}
  	public static synchronized CMPPSingleConnect getInstance(){
  		if(cmppcon == null){
  			cmppcon = new CMPPSingleConnect();		
  		}
  		return cmppcon;
  	}
  	
  	private void connectIsmg(){
  		try{
  			System.out.println("login gateway:" + SMSIsmgInfo.gd_ismg_ip);
			p.cmpp_connect_to_ismg(SMSIsmgInfo.gd_ismg_ip, SMSIsmgInfo.gd_ismg_port, con);
  			cl.set_icpid(SMSIsmgInfo.gd_icpID);
  			cl.set_auth(SMSIsmgInfo.gd_icpShareKey);
  			cl.set_version((byte)0x30);
  			cl.set_timestamp(1111101020);
  			p.cmpp_login(con,cl);
  			count=0;
  		}catch(Exception e){
			if (configManager.getConfigData("mail_io").equals("true")) {
				count++;
				if (count >= maxConnect) {
					count = 0;
					try {
						MailUtil.send("GATEWAY ERROR:form " + InetAddress.getLocalHost().getHostAddress(),
								configManager.getConfigData("mail_form"), configManager.getConfigData("mail_to"),
								"Trying to connect to dateway more than " + maxConnect);
						// MailUtil.send("短信网关连接异常",
						// configManager().getConfigData("mail_form"),
						// configManager().getConfigData("mail_to"),
						// "短信网关尝试重连次数超过"+maxConnect+"次！");
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
  			System.out.println("err:login ismg failed! --CMPP_receive.java");
    		System.out.println(e.toString());
  		}
  	}
  	synchronized public static void destroy()
	{
		System.out.println("destory connect instance.......");
		cmppcon = null;
	}
  	public static void main(String[] args) {
		System.out.println(ConfigManager.getInstance().getConfigData("mail_io"));
	}
}
