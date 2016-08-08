package org.xtone.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

public class URLConnectionUtil {
	private final static  String SERVLET_GET = "GET";
	private final static  String SERVLET_POST = "POST";
	static Logger log = Logger.getLogger(URLConnectionUtil.class); 
	
	public static String prepareParam(Map<String, Object> paramMap){
		StringBuffer sb = new StringBuffer();
		if(paramMap.isEmpty()){
			return "";
		}else{
			for (String key : paramMap.keySet()) {
				String value = paramMap.get(key)==null?"":paramMap.get(key).toString();
				if(sb.length()<1){
					sb.append(key).append("=").append(value);
				}else{
					sb.append("&").append(key).append("=").append(value);
				}
			}
		}
		return sb.toString();
	}
	public static String doGet(String urlStr,Map<String, Object> paramMap) throws Exception{
		String result = "";
		String paramStr = prepareParam(paramMap);
		if(paramStr==null||paramStr.length()<1){
			new RuntimeException("param is null");
		}else{
			urlStr+="?"+paramStr;
		}
		System.out.println(urlStr);
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(SERVLET_GET);
		conn.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
		conn.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while((line = reader.readLine())!=null){
			result = line;
		}
		log.info("doPost"+urlStr);
		log.info("result:"+result);
		reader.close();
		return result;
	}
	public static String doPost(String urlStr,Map<String, Object> paramMap) throws Exception{
		String result = "";
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(SERVLET_POST);
		String paramStr = prepareParam(paramMap);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		OutputStream os = conn.getOutputStream();
		os.write(paramStr.toString().getBytes("UTF-8"));
		os.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while((line = reader.readLine())!=null){
			result = line;
		}
		log.info("doPost"+urlStr);
		log.info(paramMap);
		
		reader.close();
		return result;
	}
	
	public static int getPostCode(String urlStr) throws Exception{
		int code;
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		code = conn.getResponseCode();
		return code;
	}
	
}
