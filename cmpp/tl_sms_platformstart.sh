#�ýű�ΪLinux������java����ű�
#Authour CWQ 2014/5/21
#ʹ��ǰҪ���ú�JDK��������
#���õ�rc.localĩβ�����������Զ�����


#��ʼ��psid������ȫ�֣�
psid=0
#���java�����Ƿ�����   
checkpid() {
   javaps=`jps -l | grep -w $1`

   
   if [ -n "$javaps" ]; then
      psid=`echo $javaps | awk '{print $1}'`
   else
      psid=0
   fi
}
#����java��������Ѿ���������ֱ��kill��������
#������������������Main��������ͬ�ĳ��򣬿��ܱ���ɱ   
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
#����java���򣬲���1��Ŀ·��������2ȫ����Main����
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

#����resin
#/home/smsdevp/resin-3.1.9/bin/httpd.sh stop
#/home/smsdevp/resin-3.1.9/bin/httpd.sh start
#����tomcat
#/home/smsdevp/tomcat-6.0.30/bin/shutdown.sh
#/home/smsdevp/tomcat-6.0.30/bin/startup.sh
#/home/smsdevp/apache-tomcat-6.0.35/bin/shutdown.sh
#/home/smsdevp/apache-tomcat-6.0.35/bin/startup.sh
