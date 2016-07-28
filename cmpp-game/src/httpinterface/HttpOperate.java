package httpinterface;
/**
 *
 * @author tang
 * @version 1.0
 * @since 2007-03-01
 * http接口，
 * 目的：使短信开发可以更加的方便快捷（除用java开发外还可用任何网页开发语言进行开发
 * 如php、asp等等
 *
 */
public class HttpOperate {
	public Message message;//信息类用于组织发送给http服务器的信息
	public MessageSend msgsend;
	public HttpOperate(){
		message = new Message();
		msgsend = new MessageSend();
	}
	public void SetMsg(String msg){
		message.msg = msg;
	}
	public void SetCpn(String cpn){
		message.cpn = cpn;
	}
	public void SetLinkid(String linkid){
		message.linkid = linkid;
	}
	public void SetUrl(String gameid){
		LoadProperty lp = new LoadProperty();
		lp.loadparam();
		message.url = lp.getUrl(gameid);
	}
	public void SendMsg(){
		msgsend.send(message);
	}

}
