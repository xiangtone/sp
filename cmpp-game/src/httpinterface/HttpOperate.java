package httpinterface;
/**
 *
 * @author tang
 * @version 1.0
 * @since 2007-03-01
 * http�ӿڣ�
 * Ŀ�ģ�ʹ���ſ������Ը��ӵķ����ݣ�����java�����⻹�����κ���ҳ�������Խ��п���
 * ��php��asp�ȵ�
 *
 */
public class HttpOperate {
	public Message message;//��Ϣ��������֯���͸�http����������Ϣ
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
