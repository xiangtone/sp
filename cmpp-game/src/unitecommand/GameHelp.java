package unitecommand;
import java.util.*;
import com.xiangtone.util.*;

public class GameHelp{


	public int execMain(String cpn,String itemaction,String ismgid,String linkid,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector ismgSet,Vector linkidSet) 
	{
		String msg = "";
		String tel = ContactTEL.getContactTel(ismgid);
		String item = itemaction.toUpperCase();
		String _linkid = linkid;
	    if(item.equals("1"))
		{
			msg = "比武招亲,资费5元/月,发BW开通，发0000取消业务，更多帮助拨打热线"+tel;
			
		}
		else if(item.equals("2"))
		{
			msg = "今古情缘,资费6元/月,发QY开通，发0000取消业务，更多帮助拨打热线"+tel;
			
		}
		else if(item.equals("3"))
		{
			msg = "战争三国,资费5元/月,发SG开通，发0000取消业务，更多帮助拨打热线"+tel;
			
		}
		else if(item.equals("4"))
		{
			msg = "心灵鸡汤,资费5元/月,发TPP开通，发0000取消业务，更多帮助拨打热线"+tel;

		}
		else if(item.equals("5"))
		{
			msg = "街霸拳皇,资费0.2元/条,发JB开通，发0000取消业务，更多帮助拨打热线"+tel;

		}
		else if(item.equals("6"))
		{
			msg = "颜色占卜,资费0.3元/条,发ZB开通，发0000取消业务，更多帮助拨打热线"+tel;

		}
		else if(item.equals("7"))
		{
			msg = "我猜我猜,资费5元/月,发WC开通，发0000取消业务，更多帮助拨打热线"+tel;

		}
		else if(item.equals("8"))
		{
			msg = "翔天酒吧,资费8元/月,发BAR开通，发0000取消业务，更多帮助拨打热线"+tel;

			
		}
		else if(item.equals("9"))
		{
			msg = "篮球斗牛,资费0.2元/条,发NBA开通，发0000取消业务，更多帮助拨打热线"+tel;
			
		}
		else if(item.equals("M"))
		{
			msg="翔通乐园(直接回复序号了解详情)：\n6 颜色占卜\n7我猜我猜\n8 翔天酒吧\n9篮球斗牛";
		}
		else 
		{
			msg="翔通乐园(直接回复序号了解详情)：\n1 比武招亲\n2 今古情缘\n3 战争三国\n4 心灵鸡汤\n5 街霸拳皇\nM 更多";
		
		}
		cpnSet.add(cpn);
		cpnGet.add(cpn);
	    msgSet.add(msg);
	    costSet.add("1000");
	    ismgSet.add(ismgid);
	    linkidSet.add(_linkid);
		return 0;	
	}
}
