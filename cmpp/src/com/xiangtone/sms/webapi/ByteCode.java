package com.xiangtone.sms.webapi;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ByteCode {

  public  int len;
  public int size;

  public char chars[];
  
  // 构造函数 初始划
  protected ByteCode(int i)
  {
      chars = new char[i];
      size = i;
      len =0;
  }
  protected char[] getChars()
  {

      char a[] = new char[len];
      for( int i = 0; i< len; i++)
      a[i] = chars[i];

      return a;
  }

 


  protected int getLen()
  {
      return len;
  }

  protected int getSize()
  {
      return size;
  }

  

  protected ByteCode(char a[])
  {
      chars = a;
      size = a.length;
      len =a.length;
  }

  protected void increase(int i)
  {
      int j = size+i;
      char a[] = new char[j];
      for(int k = 0; k <size; k++)
          a[k] = chars[k];
      chars = a;
      size = j;
  }
  
   protected void Addchar(char b)
  {
      if(len + 1 >size)
      increase(1);
      chars[len] = b;
      len ++;
  }
  
 


    protected void addAsciiz(String s, int i)
        throws Exception
    {
        int j = s.length();
        if(j > i)
            throw new Exception("toolong.string.");
        if(len + i > size)
            increase(i);
        for(int k = 0; k < j; k++)
        {

            chars[len] = s.charAt(k);
            len ++;
        }
        len += (i-j);
   }

}