package httpinterface;
/**
 * @author tang
 * @since 07-03-01
 * 目的：用于发送信息给http服务器，同时取得返回的数据；
 */
import java.net.*;
import java.io.*;

public class MessageSend {
	//Message messag
	String httpmsg = "";
	StringBuffer returnmsg = new StringBuffer();
	String returncpn = "";
	String returncontent = "";
	String returnlinkid = "";
	int resendNum = 0;

	public void send(Message message){
		addurl(message.url);
		addcpn(message.cpn);
		addcontent(message.msg);
		addlinkid(message.linkid);
		sendhttpmsg();
	}
	public void addurl(String msgurl){
		httpmsg += msgurl;
	}
	public void addcpn(String cpn){
		httpmsg += "cpn=" + cpn;
	}
	public void addcontent(String content){
		httpmsg += "&content=" + content;
	}
	public void addlinkid(String linkid){
		httpmsg += "&linkid=" + linkid;
	}
	private void sendhttpmsg(){
		/**
		 * to send msg to http server
		 * if return msg is null
		 * resend msg twice to http server;
		 */
		String getmessage = "";

			getmessage = sendmessage();
			if(getmessage.length() > 0){
				/**
				 * insert into message to db
				 */
			}
			else{
				resendNum++;
				if(resendNum < 3){
					sendhttpmsg();
				}
				returncontent = "对不起系统繁忙，请您稍后再试，谢谢！！";
			}
	}
	private String sendmessage() {
		String httpreturn = "";
		try{

		//_Lab1:
			URL u = new URL(httpmsg);
			InputStream in = new BufferedInputStream(u.openStream());
			BufferedReader retmsg = new BufferedReader(new InputStreamReader(in));
			//int i = 0;

			if((httpreturn = retmsg.readLine()) != null){
				//returnmsg.append((char)i);
				/**
				 * insert message to db
				 */
				String contlinkid = setcpn(httpreturn);
				String linkid = setcontent(contlinkid);
								setlinkid(linkid);
			}
		}catch(Exception e){
			System.out.println("send exception:" + e.toString());
		}
		return httpreturn;
	}
	private String setcpn(String retmsg){
		int cpnposition = retmsg.indexOf("&");
		this.returncpn = retmsg.substring(0,cpnposition);
		return retmsg.substring(cpnposition+1);
	}
	private String setcontent(String contentlinkid){
		int conposition = contentlinkid.indexOf("&");
		this.returncontent = contentlinkid.substring(0,conposition);
		return contentlinkid.substring(conposition + 1);
	}
	private void setlinkid(String linkid){
		this.returnlinkid = linkid;
	}
	public String getCpn(){
		return returncpn;
	}
	public String getContent(){
		return returncontent;
	}
	public String getLinkid(){
		return returnlinkid;
	}
}
