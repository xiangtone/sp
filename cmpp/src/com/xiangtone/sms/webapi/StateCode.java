package com.xiangtone.sms.webapi;

public class StateCode
{


	public static final String  sm_submit ="0001"; //������Ϣ VCP-->XTSMS
	public static final String  sm_submit_ack ="0002"; //������ϢӦ�� XTSMS-->VCP

	//filed attributes
	public static final String  VCP_ID = "01"; //�����VCP��ID�ţ��������ض�VSP���ȷ��
	public static final String  SERVER_CODE= "02"; //�ط���
	public static final String  MEDIA_TYPE = "03";
	public static final String  SERver_TYPE = "04"; //Ӧ�÷�������
	public static final String  DEST_CPN= "05"; //Ŀ���ֻ�����
	public static final String  FEE_CPN = "06"; //�����ֻ�����
	public static final String  FEE_TYPE = "07"; //�ʷ����
	public static final String  FEE_CODE = "08"; //����ֵ���Է�Ϊ��λ��
	public static final String  CONTENT= "09"; //��������
	public static final String  ACK_CODE = "10"; //Ӧ����Ϣ�з���
}