package org.x;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Disney.com</p>
 * @author Gavin Wang
 * @version 0.5
 */
public class SMSFactory 
{
	/**
	*
	*
	*/
    public CMPPSend createSMS (String type,SMSMT mt)
    {
    	/*
        if(type.equals("06")) {
            return new CMPPSend_ln(mt); //��������
        } else if(type.equals("08")) { 
            return new CMPPSend_hl(mt);    //������
        } else if(type.equals("10")) {
            return new CMPPSend_bj(mt);    //�����ƶ�
        } else if(type.equals("01")) {
            return new CMPPSend_bj(mt);   //�����ƶ�
        } else if(type.equals("15")) {
            return new CMPPSend_sd(mt);  //ɽ���ƶ�
        } else if(type.equals("19")) {
        	return new CMPPSend_gd(mt);  //�㶫�ƶ�
        } else { 
            return new CMPPSend_bj(mt);	
        }
        */
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        System.out.println("content is:" + mt.sendContent);
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
    	return new CMPPSend_sd(mt);
    }
}