package org.xtone.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description: 发送报警类	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-8下午02:30:57
 */
public class SendMsg {
	/**
	 * 
	 * 以get方式 请求地址
	 * 
	 * @param url
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */

	public static String send(String url) throws Exception

	{
		System.out.println(url);

		URL send_url = new URL(url);

		URLConnection connection = send_url.openConnection();

		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");

		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		connection.setRequestProperty("Content-type", "text/xml");

		HttpURLConnection httpConn = (HttpURLConnection) connection;

		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());

		BufferedReader in = new BufferedReader(isr);

		String inputLine;

		String tt = "";

		while ((inputLine = in.readLine()) != null)

		{

			tt += inputLine;

		}

		in.close();

		return tt;

	}


}
