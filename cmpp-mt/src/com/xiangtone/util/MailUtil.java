package com.xiangtone.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

public class MailUtil {

	
		/**
		 * @param fromTitle
		 *            �ʼ�����
		 * @param mailFrom
		 *            �����ʼ�������
		 * @param mailTo
		 *            �����ʼ�������
		 * @param strText
		 *            �ʼ�����
		 */
		public static void send(String fromTitle, String mailFrom, String mailTo, final String strText) {
			try {
				ConfigManager configManager=ConfigManager.getInstance();
				// ������ʹ�÷��ʼ��ĵ������������
				String host = configManager.getConfigData("MAILHOST");

				// ���� properties ����������˷����ʼ��������ĵ�ַ��
				Properties props = new Properties();
				// �����ʼ��������ĵ�ַ
				props.put("mail.smtp.host", host);
				// ͨ����֤ Ĭ��Ϊfalse
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.localhost", "localhost");
				// У�鷢����Ȩ��
				MyAuthenticator myauth = new MyAuthenticator(configManager.getConfigData("SENDMAIL"), configManager.getConfigData("MAILPASS"));
				// ���� session
				Session session = Session.getDefaultInstance(props, myauth);
				// session.setDebug(true);//�򿪵���
				// ���� �ʼ���message��message����������ʼ��ڶ��еĲ��������Ƿ�װ����set����ȥ���õ�
				MimeMessage message = new MimeMessage(session);
				// ���÷�����
				message.setFrom(new InternetAddress(mailFrom));
				message.setDataHandler(new DataHandler(new ByteArrayDataSource(strText.toString(), "text/html")));
				// ������
				message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(mailTo));
				// �ʼ�����
				message.setSubject(fromTitle);
				// �ʼ�����
				// message.setText(strText);
				// �������Ϲ���
				message.saveChanges();

				// �����ʼ�
				Transport.send(message);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("�ʼ�����ʧ��");
			}
		}

		public static void main(String[] args) {
			ConfigManager configManager=ConfigManager.getInstance();
			StringBuffer url = new StringBuffer(configManager.getConfigData("ACTIVATION_URL") + "confrimAccount.action");
			String sendMail=configManager.getConfigData("SENDMAIL");
			try {
				send("test", sendMail, "429379083@qq.com,Shirp@bjxiangtone.com", "test2");
			} catch (Exception e) {
				System.out.println("�����ʼ�ʱ�����쳣");
				e.printStackTrace();
			}
		}
	}


class MyAuthenticator extends Authenticator {
	String userName = "";
	String password = "";

	public MyAuthenticator() {

	}

	public MyAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
