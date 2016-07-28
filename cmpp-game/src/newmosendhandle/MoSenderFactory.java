package newmosendhandle;
/*
 * 发送工厂
 * 产生各个合作伙伴的上行发送程序
 */
import java.util.*;
import java.io.*;
public class MoSenderFactory {
private String company = "";
	public MoSender createMoSender() throws IOException{
		MoSender moSender = null;
		try{
			if(company.equals("szpwd")){
				//moSender = new PwdMoSender(company);//深圳普威德公司（通道合作）
			}
			else if(company.equals("sjnz")){
				//moSender = new SjnzMoSender(company);//深圳梦龙通（手机内置）
			}
			else if(company.equals("xmzs")){
				//moSender = new XmzsMoSender(company);//厦门掌上（通道合作）
			}
			//else if(company.equals("szmt")){
			//	moSender = new SzMtMoSender(company);//深圳摩通（通道合作）
			//}
			else if(company.equals("shts")){
				//moSender = new ShtsMoSender(company);//上海通势（通道合作）
			}
			else if(company.equals("xt")){
				//moSender = new XtMoSender(company);//创世(翔通)
			}
			else if(company.equals("wry")){
				//moSender = new WryMoSender(company);//沃睿盈
			}
			else if(company.equals("zyhd")){
				moSender = new ZyhdMoSender(company);//卓意互动
			}
			else if(company.equals("mtfx")){
				moSender = new XtMoSender(company);//沃睿盈
			}
			else if(company.equals("mtfx1")){
				//moSender = new XtMoSender(company);//沃睿盈
			}
//			else if(company.equals("sxhgame1") || company.equals("sxhgame2")){
//				moSender = new SxhGameMoSender(company);//深讯和游戏
//			}
//			else if(company.equals("sxhtv") || company.equals("sxhbook") || company.equals("sxhvideo")){
//				moSender = new SxhVideoMoSender(company);//书城、电视、视频
//			}
			else if(company.equals("tltd")){
				moSender = new TltdMoSender(company);//深圳创世互动、和宏、yiwen
			}
			/*
			else if(company.equals("hztx")){
				//moSender = new HztxMoSender(company);//华中天讯
			}
			else if(company.equals("szmk")){
				//moSender = new HztxMoSender(company);//华中天讯
			}
			*/
			//else{
				//moSender = new XtMoSender(company);//
			//}
			
			//else if(company.equals("wry")){
			//	moSender = new WryMoSender(company);//沃睿盈
			//}
			/*
			//////////szmt change at 091102
			else if(company.equals("szmt")){
				moSender = new SzMtMoSender(company);//深圳摩通（通道合作）
			}

			
			else if(company.equals("shts")){
				moSender = new ShtsMoSender(company);//上海通势（通道合作）
			}
			else if(company.equals("xt")){
				moSender = new XtMoSender(company);//创世(翔通)
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
