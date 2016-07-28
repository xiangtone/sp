package org.x;

import java.io.BufferedInputStream;
import java.io.InputStream;
//package platformalarm;
import java.net.URL;

public class ServerAlarm {
	public void Alarm(int type) {
		String http = "";
		String content = "ƽ̨���쳣�������뷢��Ϣ�ٴ�ȷ���Ƿ�����";

		String phone[] = { "13859905479", "13950058509" };
		if (type == 0) {
			content = "ƽ̨���쳣�������뷢��Ϣ�ٴ�ȷ���Ƿ�����";
		} else if (type == 1) {
			content = "ƽ̨���쳣�����ڶ��β��ɹ�";
		} else {
			content = "ƽ̨��������δ�ɹ������Ϸ������鿴��";
		}
		try {
			for (int i = 0; i < phone.length; i++) {
				System.out.println("start to send msg to phone" + phone[i]);
				http = "http://202.109.249.20:2000/?msg=" + java.net.URLEncoder.encode(content, "gb2312") + "&url=&phone="
						+ phone[i] + "&spnumber=88871";
				System.out.println(http);
				URL u = new URL(http);
				InputStream in = new BufferedInputStream(u.openStream());
			}

		} catch (Exception e) {
			System.out.print(e.toString());
		}
	}

	public void AlarmOut() {
		String http = "";
		String content = "ƽ̨alarm";
		String phone[] = { "13859905479", "13950058509" };
		try {
			for (int i = 0; i < phone.length; i++) {
				System.out.println("start to send msg to phone" + phone[i]);
				http = "http://202.109.249.20:2000/?msg=" + java.net.URLEncoder.encode(content, "gbk") + "&url=&phone="
						+ phone[i] + "&spnumber=88871";
				System.out.println(http);
				URL u = new URL(http);
				InputStream in = new BufferedInputStream(u.openStream());
			}

		} catch (Exception e) {
			System.out.print(e.toString());
		}
	}
}
