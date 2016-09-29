/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

/**
 * ��Ϣ����
 *
 */
public class StateCode {

	public static final int SM_SUBMIT = 1; // ������Ϣ VCP-->XTSMS
	public static final int SM_SUBMIT_ACK = 2; // ������ϢӦ�� XTSMS-->VCP
	public static final int SM_DELIVER = 3; // ������Ϣ XTSMS-->VCP
	public static final int SM_DELIVER_ACK = 4; // ������ϢӦ�� XTSMS-->VCP
	public static final int SM_REPORT = 5; // ������Ϣ
	public static final int SM_REPORT_ACK = 6; // ������ϢӦ��

	// submit attributes
	public static final byte VCP_ID = 1; // �����VCP��ID�ţ��������ض�VSP���ȷ��
	public static final byte SERVER_CODE = 2; // �ط���
	public static final byte PROV_ID = 3; // ʡ��
	public static final byte SERVER_ID = 4; // Ӧ�÷�������
	public static final byte DEST_CPN = 5; // Ŀ���ֻ�����
	public static final byte FEE_CPN = 6; // �����ֻ�����
	public static final byte FEE_TYPE = 7; // �ʷ����
	public static final byte FEE_CODE = 8; // ����ֵ���Է�Ϊ��λ��
	public static final byte MEDIA_TYPE = 9; // ý������
	public static final byte CONTENT = 10; // ��������
	public static final byte REGISTERED_DELIVERY = 11; // ״̬����
	// public static final byte VCP_PWD = 12; //��������
	public static final byte FEE_CPNTYPE = 13;// �����ֻ��ĺ������� α������� add @ 061201
	public static final byte LINK_ID = 12;// link id add at 061201
	public static final byte MSGID = 14;// ADD AT 08-11-27

	// deliver attributes
	public static final byte MOBILE_CODE = 1;
	public static final byte GAME_CODE = 2;
	public static final byte ACTION_CODE = 3;
	public static final byte SP_CODE = 4;
	public static final byte ISMG_CODE = 5;

	public static final byte ACK_CODE = 100; // Ӧ����Ϣ�з���
}