package newmosendhandle;
/*
 * ���͹���
 * �������������������з��ͳ���
 */
import java.util.*;
import java.io.*;
public class MoSenderFactory {
private String company = "";
	public MoSender createMoSender() throws IOException{
		MoSender moSender = null;
		try{
			if(company.equals("szpwd")){
				//moSender = new PwdMoSender(company);//���������¹�˾��ͨ��������
			}
			else if(company.equals("sjnz")){
				//moSender = new SjnzMoSender(company);//��������ͨ���ֻ����ã�
			}
			else if(company.equals("xmzs")){
				//moSender = new XmzsMoSender(company);//�������ϣ�ͨ��������
			}
			//else if(company.equals("szmt")){
			//	moSender = new SzMtMoSender(company);//����Ħͨ��ͨ��������
			//}
			else if(company.equals("shts")){
				//moSender = new ShtsMoSender(company);//�Ϻ�ͨ�ƣ�ͨ��������
			}
			else if(company.equals("xt")){
				//moSender = new XtMoSender(company);//����(��ͨ)
			}
			else if(company.equals("wry")){
				//moSender = new WryMoSender(company);//���ӯ
			}
			else if(company.equals("zyhd")){
				moSender = new ZyhdMoSender(company);//׿�⻥��
			}
			else if(company.equals("mtfx")){
				moSender = new XtMoSender(company);//���ӯ
			}
			else if(company.equals("mtfx1")){
				//moSender = new XtMoSender(company);//���ӯ
			}
//			else if(company.equals("sxhgame1") || company.equals("sxhgame2")){
//				moSender = new SxhGameMoSender(company);//��Ѷ����Ϸ
//			}
//			else if(company.equals("sxhtv") || company.equals("sxhbook") || company.equals("sxhvideo")){
//				moSender = new SxhVideoMoSender(company);//��ǡ����ӡ���Ƶ
//			}
			else if(company.equals("tltd")){
				moSender = new TltdMoSender(company);//���ڴ����������ͺꡢyiwen
			}
			/*
			else if(company.equals("hztx")){
				//moSender = new HztxMoSender(company);//������Ѷ
			}
			else if(company.equals("szmk")){
				//moSender = new HztxMoSender(company);//������Ѷ
			}
			*/
			//else{
				//moSender = new XtMoSender(company);//
			//}
			
			//else if(company.equals("wry")){
			//	moSender = new WryMoSender(company);//���ӯ
			//}
			/*
			//////////szmt change at 091102
			else if(company.equals("szmt")){
				moSender = new SzMtMoSender(company);//����Ħͨ��ͨ��������
			}

			
			else if(company.equals("shts")){
				moSender = new ShtsMoSender(company);//�Ϻ�ͨ�ƣ�ͨ��������
			}
			else if(company.equals("xt")){
				moSender = new XtMoSender(company);//����(��ͨ)
			}
			else{
				moSender = new XtMoSender(company);//
			}
			*/
		}catch(Exception e){
			e.printStackTrace();
		}
		return moSender;
	}
	public void setCompany(String company){
		this.company = company;
	}
	
}
