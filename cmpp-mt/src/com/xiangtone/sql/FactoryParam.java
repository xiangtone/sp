/** 
* Created on 2003-5-13 
* 
* To change the template for this generated file go to 
* Window>Preferences>Java>Code Generation>Code and Comments 
*/
package com.xiangtone.sql;
/** 
* @author youyongming 
* 
*/
//���ӳع�������
public class FactoryParam 
{ 
	 
	private int MaxConnectionCount = 4; //���������
	private int MinConnectionCount = 2; //��С������ 
	//���ղ��� 
	private int ManageType = 0; 
	public FactoryParam() 
	{ 
	}  
	/**  
	* �������ӳع��������Ķ���  
	* @param max ���������  
	* @param min ��С������  
	* @param type �������  
	*/ 
	public FactoryParam(int max, int min, int type) 
	{  
		this.ManageType = type;  
		this.MaxConnectionCount = max;  
		this.MinConnectionCount = min; 
	} 
	/**  
	* ��������������  
	* @param value  
	*/ 
	public void setMaxConn(int value) 
	{  
		this.MaxConnectionCount = value; 
	} 
	/**  
	* ��ȡ���������  
	* @return  */ 
	public int getMaxConn() 
	{  
		return this.MaxConnectionCount; 
	} 
	/**  
	* ������С������  
	* @param value  
	
	*/ 
	public void setMinConn(int value) 
	{  
		this.MinConnectionCount = value; 
	} 
	/**  
	* ��ȡ��С������  
	* @return  
	*/ 
	public int getMinConn() 
	{  
		return this.MinConnectionCount; 
	} public int getType() 
	{ 
		return this.ManageType; 
	}
}