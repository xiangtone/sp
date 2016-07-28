package com.xiangtone.util;
public class IntByteConvertor {

public static byte[] int2Byte(int intValue){
		byte[] b=new byte[8];
		for(int i=0;i<4;i++){
		b[i]=(byte)(intValue>>8*(3-i) & 0xFF);
//System.out.print(Integer.toBinaryString(b[i])+" ");
//System.out.print((b[i]& 0xFF)+" ");
		}
		return b;
}
public static int byte2Int(byte[] b){
		int intValue=0;
		for(int i=0;i<b.length;i++){
			intValue +=(b[i] & 0xFF)<<(8*(3-i));
		//System.out.print(Integer.toBinaryString(intValue)+" ");
		}
			return intValue;
}
		public static void main(String[] args) {
			System.out.println(byte2Int(int2Byte(1000)));
		}
public static void putLong(byte[] bb, long x, int index) {
			bb[index + 0] = (byte) (x >> 56);
			bb[index + 1] = (byte) (x >> 48);
			bb[index + 2] = (byte) (x >> 40);
			bb[index + 3] = (byte) (x >> 32);
			bb[index + 4] = (byte) (x >> 24);
			bb[index + 5] = (byte) (x >> 16);
			bb[index + 6] = (byte) (x >> 8);
			bb[index + 7] = (byte) (x >> 0);
}

public static long getLong(byte[] bb, int index) {
			return ((((long) bb[index + 0] & 0xff) << 56)
			| (((long) bb[index + 1] & 0xff) << 48)
			| (((long) bb[index + 2] & 0xff) << 40)
			| (((long) bb[index + 3] & 0xff) << 32)
			| (((long) bb[index + 4] & 0xff) << 24)
			| (((long) bb[index + 5] & 0xff) << 16)
			| (((long) bb[index + 6] & 0xff) << 8) | (((long) bb[index + 7] & 0xff) << 0));
			}
}
