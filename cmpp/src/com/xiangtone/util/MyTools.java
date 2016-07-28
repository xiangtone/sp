/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.util;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
* class commention
*
*/
public class MyTools
{
	/**
	*
	*
	*/
	public static String Bytes2HexString(byte[] buffer)
	{
		 String s="";
		 for(int j=0; j<buffer.length; j++)
         {
             byte b=buffer[j];
             int bb=b;
             if(bb<0)
              bb=256+b;
              String a = Integer.toHexString(bb);
              if(a.length()==1)
                 a="0"+a;
             s += a; 
        }
        return s;
	}
	/**
	*
	*
	*/
	public static String UCS2GB2312(byte[] sm)
	{
		int tt=0,tt1=0,tt2=0;  
       	String tmp_tt="";  
       	int slen=sm.length/2;  
       	for(int ii=0;ii<slen;ii++)   
       	{  
           	tt1=(int)sm[ii*2];  
           	tt1=(tt1&0x000000ff)<<8;  
           	tt2=(int)sm[ii*2+1];  
           	tt2=tt2&0x000000ff;  
           	tt=tt1+tt2;  
           	tmp_tt=tmp_tt+(char)tt;  
       	}
       	return tmp_tt.trim();  
	}
	/////////////////////////////
	/*
	*gsm-7½âÂë
	*/
	public static String GSM72GB2312(byte[] sm){
		String src = new String(sm);
		String result = null; 
		        int[] b; 
		        String temp=null; 
		        byte srcAscii; 
		        byte left=0; 
		        
		  if (src != null && src.length() %2==0){ 
		   result=""; 
		           b=new int[src.length() /2]; 
		           temp=src+"0"; 
		           for(int i=0,j=0,k=0;i<temp.length() -2;i+=2,j++){ 
		             b[j]=Integer.parseInt(temp.substring(i,i+2),16); 
		             
		             k=j % 7; 
		             srcAscii=(byte)(((b[j]<<k) & 0x7F)|left); 
		             result+=(char)srcAscii; 
		             left=(byte)(b[j]>>>(7-k)); 
		             if(k==6){ 
		              result+=(char)left; 
		              left=0; 
		             } 
		             if(j==src.length() /2) 
		              result+=(char)left; 
		           } 
		  }
		  return result; 
	}
}