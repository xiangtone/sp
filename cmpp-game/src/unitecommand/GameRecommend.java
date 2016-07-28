package unitecommand;

import java.util.*;

import com.xiangtone.util.*;



public class GameRecommend{





	public int execMain(String cpn,String itemaction,String ismgid,String linkid,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector ismgSet,Vector linkidSet) 

	{

		StringBuffer buffer = new StringBuffer();

		String contactTel =ContactTEL.getContactTel(ismgid);

		buffer.append("");

		//buffer.append("业务推荐:请回复LT下载图片铃声.详情请登网址:www.indexweb.cn");

		buffer.append("客服："+contactTel);

		cpnSet.add(cpn);

		cpnGet.add(cpn);

	    msgSet.add(buffer.toString());

	    costSet.add("1000");
			linkidSet.add(linkid);
	    ismgSet.add(ismgid);

		return 1;	

	}

}

