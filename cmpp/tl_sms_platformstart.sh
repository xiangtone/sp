#该脚本为Linux下启动java程序脚本
#Authour CWQ 2014/5/21
#使用前要配置好JDK环境变量
#配置到rc.local末尾可以做开机自动启动


#初始化psid变量（全局）
psid=0
#检查java程序是否启动   
checkpid() {
   javaps=`jps -l | grep -w $1`

   
   if [ -n "$javaps" ]; then
      psid=`echo $javaps | awk '{print $1}'`
   else
      psid=0
   fi
}
#启动java程序，如果已经启动，会直接kill掉再重启
#不能运行两个包名和Main函数都相同的程序，可能被误杀   
startjava(){
   echo "starting $1 $2"
   checkpid $2
   
   if [ $psid -ne 0 ]; then
      echo "warn: $1 $2 already started! (pid=$psid)"
      echo "kill $psid ..."
      kill $psid
      sleep 6s 
   fi
   echo "start $1 $2 ..."
   cd $1
   java $2 &
  
   checkpid $2
   if [ $psid -ne 0 ]; then
      echo "(pid=$psid) [OK]"
   else
      echo "[Failed]"
   fi
  }
#启动java程序，参数1项目路径，参数2全包名Main函数
startjava /home/smsdevp/qwsms/CMPPMT1  tlsmsmt_public
startjava /home/smsdevp/qwsms1/CMPPMT1 tlsmsmt_public1
startjava /home/smsdevp/qwsms2/CMPPMT1 tlsmsmt_public2
startjava /home/smsdevp/qwsms3/CMPPMT1 tlsmsmt_public3
startjava /home/smsdevp/qwsms4/CMPPMT1 tlsmsmt_public4
startjava /home/smsdevp/qwsms5/CMPPMT1 tlsmsmt_public5

startjava /home/smsdevp/qwsms/Cmppgame tlxiangtone_game
startjava /home/smsdevp/qwsms1/Cmppgame tlxiangtone_game1
startjava /home/smsdevp/qwsms2/Cmppgame tlxiangtone_game2
startjava /home/smsdevp/qwsms3/Cmppgame tlxiangtone_game3
startjava /home/smsdevp/qwsms4/Cmppgame tlxiangtone_game4
startjava /home/smsdevp/qwsms5/Cmppgame tlxiangtone_game5

startjava /home/smsdevp/qwsms/CMPP tlsms_public
startjava /home/smsdevp/qwsms1/CMPP tlsms_public1
startjava /home/smsdevp/qwsms2/CMPP tlsms_public2
startjava /home/smsdevp/qwsms3/CMPP tlsms_public3
startjava /home/smsdevp/qwsms4/CMPP tlsms_public4
startjava /home/smsdevp/qwsms5/CMPP tlsms_public5

#startjava /home/smsdevp/smsToolsDayLimit/MoreCompanysProvcountLimit  com.xiangtong.dao.MoreCompanysProvcountLimitThread

#启动resin
#/home/smsdevp/resin-3.1.9/bin/httpd.sh stop
#/home/smsdevp/resin-3.1.9/bin/httpd.sh start
#启动tomcat
#/home/smsdevp/tomcat-6.0.30/bin/shutdown.sh
#/home/smsdevp/tomcat-6.0.30/bin/startup.sh
#/home/smsdevp/apache-tomcat-6.0.35/bin/shutdown.sh
#/home/smsdevp/apache-tomcat-6.0.35/bin/startup.sh
