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

	/** 方法rdmFruit(int seed)在给定的范围内生成随机数
	*@param int seed 随机数的范围
	*@return int rdmFruit 返回的随机数
	*/
	public long rdmFruit(int seed){
		long rdmFruit = Math.round(Math.random()*100)%seed;		
		return rdmFruit;
		}
	}