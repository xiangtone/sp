/*
 * @(#)rdmFruit.java 1.0 02/01/10
 *
 * Copyright (c) 2001 XM Index, Inc. All Rights Reserved.
 * 
 * Check the income string available
 *
*/
package com.xmindex.dep1.sms;

public class intTyGender{

	/** ����rdmFruit(int seed)�ڸ����ķ�Χ�����������
	*@param int seed ������ķ�Χ
	*@return int rdmFruit ���ص������
	*/
	public long rdmFruit(int seed){
		long rdmFruit = Math.round(Math.random()*100)%seed;		
		return rdmFruit;
		}
	}