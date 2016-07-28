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
		 *            邮件标题
		 * @param mailFrom
		 *            发送邮件的邮箱
		 * @param mailTo
		 *            接收邮件的邮箱
		 * @param strText
		 *            邮件内容
		 */
		public static void send(String fromTitle, String mailFrom, String mailTo, final String strText) {
			try {
				ConfigManager configManager=ConfigManager.getInstance();
				// 发件人使用发邮件的电子信箱服务器
				String host = configManager.getConfigData("MAILHOST");

				// 创建 properties ，里面包含了发送邮件服务器的地址。
				Properties props = new Properties();
				// 发送邮件服务器的地址
				props.put("mail.smtp.host", host);
				// 通过验证 默认为false
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.localhost", "localhost");
				// 校验发信人权限
				MyAuthenticator myauth = new MyAuthenticator(configManager.getConfigData("SENDMAIL"), configManager.getConfigData("MAILPASS"));
				// 创建 session
				Session session = Session.getDefaultInstance(props, myauth);
				// session.setDebug(true);//打开调试
				// 创建 邮件的message，message对象包含了邮件众多有的部件，都是封装成了set方法去设置的
				MimeMessage message = new MimeMessage(session);
				// 设置发信人
				message.setFrom(new InternetAddress(mailFrom));
				message.setDataHandler(new DataHandler(new ByteArrayDataSource(strText.toString(), "text/html")));
				// 收信人
				message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(mailTo));
				// 邮件标题
				message.setSubject(fromTitle);
				// 邮件内容
				// message.setText(strText);
				// 保存以上工作
				message.saveChanges();

				// 发送邮件
				Transport.send(message);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("邮件发送失败");
			}
		}

		public static void main(String[] args) {
			ConfigManager configManager=ConfigManager.getInstance();
			StringBuffer url = new StringBuffer(configManager.getConfigData("ACTIVATION_URL") + "confrimAccount.action");
			String sendMail=configManager.getConfigData("SENDMAIL");
			try {
				send("test", sendMail, "429379083@qq.com,Shirp@bjxiangtone.com", "test2");
			} catch (Exception e) {
				System.out.println("发送邮件时发生异常");
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
