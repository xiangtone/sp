/** 
* @author Airmirror 
* ʵ�����ݿ����ӵĲ����� 
*/
package com.xiangtone.sql;
import java.io.Serializable;

public class ConnectionParam implements Serializable 
{ 
	private String driver;    //���ݿ��������� 
	private String url;   //�������ӵ�URL 
	private String user;    //���ݿ��û��� 
	private String password;   //���ݿ�����  
	/**  
	 * Ψһ�Ĺ��캯������Ҫָ�����ӵ��ĸ���Ҫ����  
	 *@param driver ��������  
	 * @param url  ���ݿ�����url  
	 * @param user  �û���  
	 * @param password ����  
	 */ 
	 public ConnectionParam(String driver,String url,String user,String password) 
	 {  
	 	this.driver = driver;  
	 	this.url = url;  
	 	this.user = user;  
	 	this.password = password; 
	 }
	 /**
	 *ȡ�ò���ֵ(get)
	 *
	 */
 	 public String getDriver() {  return driver; }
 	 public String getPassword() {  return password; }
 	 public String getUrl() {  return url; }
 	 public String getUser() {  return user; }
 	
 	/**
 	*���ò���(set)
 	*
 	*/
 	public void setDriver(String driver) {  this.driver = driver; }
 	public void setPassword(String password) {  this.password = password; }
 	public void setUrl(String url) {  this.url = url; }
 	public void setUser(String user) {  this.user = user; }
 	/**
 	* @see java.lang.Object#clone()  
 	*/ 
 	public Object clone()
 	{  
 		ConnectionParam param = new ConnectionParam(driver,url,user,password);  
 		return param; 
 	}
	 /**  
	 * @see java.lang.Object#equals(java.lang.Object)  
	 */ 
	 public boolean equals(Object obj) 
	 {  
	 	if(obj instanceof ConnectionParam)
	 	{   
	 		ConnectionParam param = (ConnectionParam)obj;   
	 		return ((driver.compareToIgnoreCase(param.getDriver()) == 0)&&(url.compareToIgnoreCase(param.getUrl()) == 0)&&(user.compareToIgnoreCase(param.getUser()) == 0)&&(password.compareToIgnoreCase(param.getPassword()) == 0));  
	 	}  
	 	return false; 
	 }
}