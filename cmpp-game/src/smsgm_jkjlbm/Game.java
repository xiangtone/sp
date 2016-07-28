package smsgm_jkjlbm;
import java.util.Vector;
import DBHandle.*;
import java.util.*;
import MonthOrderHandle.UsersHandle;
import MonthOrderHandle.MessageHandle;

public class Game{
	String ismgid = "01";
	String serverId = "1003";
	String feeCode = "-SHJKJLB";
	String spId = "901763";
	public int execMain(String cpn,String itemaction,String ismgid,String linkid,Integer cpnType,String msgId,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector ismgSet,Vector linkidSet,Vector cpntypeSet,Vector msgIdSet){
		String userCpn = cpn;
		String userInput = itemaction;
		int SendCycleDay = 3;
		if(userInput.equals("SYSAUTO")){
			Calendar c = Calendar.getInstance();
			int sendTime = c.get(Calendar.HOUR_OF_DAY);
			////////////////////////////////////
			UsersHandle userHandle = new UsersHandle();
			userHandle.setFeeCode(this.feeCode);
			userHandle.setSpId(this.spId);
			String retMsg = "";
			MessageHandle messageHandle = new MessageHandle();
					messageHandle.setDbIp("xiangtone_dbip");
					messageHandle.setDbName("smsmonthdb");
					messageHandle.setDbUser("xiangtone_dbuser");
					messageHandle.setDbPwd("xiangtone_dbpwd");
					messageHandle.setDbPort("xiangtone_dbport");
					messageHandle.setSpId(this.spId);
					messageHandle.setServerId(feeCode);
					messageHandle.setSendCycleDay(SendCycleDay);//用于设置循环发送天数
					messageHandle.createDbConnect();
			///////////////////
			//用于取得当天订购未发送信息的用户
			ArrayList firstOrderUsers = userHandle.getFirstOrderUsers();
			if(firstOrderUsers.size() > 0){
					for(int i = 0;i < firstOrderUsers.size();i++){
					messageHandle.setCpn((String)firstOrderUsers.get(i));
					retMsg = messageHandle.msgHandle();
					if(!retMsg.equals("")){
						msgAdd((String)firstOrderUsers.get(i),retMsg,serverId,linkid,msgId,cpnType,cpnSet,cpnGet,msgSet,costSet,ismgSet,linkidSet,cpntypeSet,msgIdSet);
					}
				}
			}
			////////////////////////////////////
			if(sendTime >= 9 && sendTime < 17){
				ArrayList users = userHandle.getOrderUsers();
				for(int i = 0;i < users.size();i++){
					messageHandle.setCpn((String)users.get(i));
					retMsg = messageHandle.msgHandle();
					if(!retMsg.equals("")){
						msgAdd((String)users.get(i),retMsg,serverId,linkid,msgId,cpnType,cpnSet,cpnGet,msgSet,costSet,ismgSet,linkidSet,cpntypeSet,msgIdSet);
					}
				}	
			}
				try{
					userHandle.mydb.close();
					messageHandle.mydb.close();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try{
						userHandle.mydb.close();
						messageHandle.mydb.close();
					}catch(Exception e){
						e.printStackTrace();	
					}	
				}
		}
		else{
				String retMsg = "欢迎您使用创世互动游戏伴侣业务， 稍后我们将为您下发响应的心动地带信息.";
				msgAdd(userCpn,retMsg,serverId,linkid,msgId,cpnType,cpnSet,cpnGet,msgSet,costSet,ismgSet,linkidSet,cpntypeSet,msgIdSet);
		}

		/*
		User user = new User();
		UserHandle userHandle = new UserHandle();

		user.setCpn(cpn);
		user.setUserInput(userInput);
		//user.setStep(userInput);
		userHandle.setUser(user);
		if(userInput.equals("XDDD")){
			user.setStep(userInput);
			if(userHandle.checkUser()){
				user.setRetMsg(Message.welcome);
			}
			else{
				user.setRetMsg(Message.again);
			}
		}
		else if(userInput.equals("D")){//在此可以进行拓展，暂时只取最新的3条信息
			//user.setStep(userInput);
			user.setRetMsg(userHandle.getNews());
		}
		else if(userInput.startsWith("W")){
			//user.setStep(userInput);
			userHandle.WriteEnjoy();//写自己所兴趣爱好的
		}
		else if(userInput.equals("H")){
			user.setRetMsg(Message.Help);
		}
		else{
			user.setRetMsg(Message.error);
		}
		msgAdd(user.getCpn(),user.getRetMsg(),serverId,linkid,msgId,cpnType,cpnSet,cpnGet,msgSet,costSet,ismgSet,linkidSet,cpntypeSet,msgIdSet);
		*/
		//String retMsg = "欢迎您使用创世互动心动地带业务，资费8元/月,,客服电话:95105508.";
		//msgAdd(userCpn,retMsg,serverId,linkid,msgId,cpnType,cpnSet,cpnGet,msgSet,costSet,ismgSet,linkidSet,cpntypeSet,msgIdSet);

		return 0;
	}
	public void msgAdd(String cpn,String msg,String serverid,String linkId,String msgId,int cpnType,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector ismgSet,Vector linkidSet,Vector cpntypeSet,Vector msgIdSet){
		cpnSet.add(cpn);
		cpnGet.add(cpn);
	    msgSet.add(msg);
	    costSet.add(serverid);
		linkidSet.add(linkId);
	    ismgSet.add(ismgid);
	    msgIdSet.add(msgId);
	    cpntypeSet.add(cpnType);
	}
}
