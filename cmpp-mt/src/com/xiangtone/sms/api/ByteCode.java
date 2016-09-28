/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.sms.api;

/**
 * Title: Description: Copyright: Copyright (c) 2001 Company:
 * 
 * @author
 * @version 1.0
 */

public class ByteCode {

	public int len;
	public int size;

	public byte bytes[];

	protected ByteCode(int i) {
		bytes = new byte[i];
		size = i;
		len = 0;
	}

	protected byte[] getBytes() {

		byte a[] = new byte[len];
		for (int i = 0; i < len; i++)
			a[i] = bytes[i];

		return a;
	}

	protected void AddInt16(short b) {
		if (len + 2 > size)
			increase(2);
		bytes[len] = (byte) (b >>> 8 & 0xff);
		len++;
		bytes[len] = (byte) (b & 0xff);
		len++;
	}

	protected void AddShort(short b) {
		if (len + 2 > size)
			increase(2);
		bytes[len] = (byte) (b >>> 8 & 0xff);
		len++;
		bytes[len] = (byte) (b & 0xff);
		len++;
	}

	protected void AddInt32(int i) {
		if (len + 4 > size)
			increase(4);
		bytes[len] = (byte) (i >>> 24 & 0xff);
		len++;
		bytes[len] = (byte) (i >>> 16 & 0xff);
		len++;
		bytes[len] = (byte) (i >>> 8 & 0xff);
		len++;
		bytes[len] = (byte) (i & 0xff);
		len++;
	}

	protected void AddInt(int i) {
		if (len + 4 > size)
			increase(4);
		bytes[len] = (byte) (i >>> 24 & 0xff);
		len++;
		bytes[len] = (byte) (i >>> 16 & 0xff);
		len++;
		bytes[len] = (byte) (i >>> 8 & 0xff);
		len++;
		bytes[len] = (byte) (i & 0xff);
		len++;
	}

	protected int getLen() {
		return len;
	}

	protected int getSize() {
		return size;
	}

	protected ByteCode(byte a[]) {
		bytes = a;
		size = a.length;
		len = a.length;
	}

	protected void increase(int i) {
		int j = size + i;
		byte a[] = new byte[j];
		for (int k = 0; k < size; k++)
			a[k] = bytes[k];
		bytes = a;
		size = j;
	}

	protected void AddByte(byte b) {
		if (len + 1 > size)
			increase(1);
		bytes[len] = b;
		len++;
	}

	protected void AddInt8(byte b) {
		if (len + 1 > size)
			increase(1);
		bytes[len] = b;
		len++;
	}

	protected void AddInt8(short b) {
		AddInt8((byte) b);
	}

	protected void AddBytes(byte b[]) throws Exception {
		if (b == null)
			throw new java.lang.Exception(" Byte[] is null");

		if (len == 0) {
			bytes = new byte[b.length];
			for (int i = 0; i < b.length; i++)
				bytes[i] = b[i];
			len = b.length;
			size = len;
		} else {
			int i;
			int j;

			byte c[] = new byte[len + b.length];

			for (i = 0; i < len; i++)
				c[i] = bytes[i];
			for (j = 0; j < b.length; j++)
				c[i + j] = b[j];
			bytes = c;
			len = len + b.length;
			size = len;
		}
	}

	protected void addAsciiz(String s, int i) throws Exception {
		int j = s.getBytes().length;
		if (j > i)
			throw new Exception("toolong.string.");
		if (len + i > size)
			increase(i);

		// byte asc = s.getBytes();
		for (int k = 0; k < j; k++) {

			bytes[len] = (byte) (s.charAt(k) & 0xff);
			len++;
		}
		len += (i - j);
	}

	protected void printBytes() {
		int i;
		int j = 0;
		for (i = 0; i < len; i++) {
			if (i % 10 == 0) {
				System.out.println('\n');
				j++;
				System.out.print("Line Number " + j + "  :");
			}
			System.out.print(' ');
			System.out.print(' ');
			System.out.print('[');
			System.out.print(Long.toString((int) bytes[i] & 0xff, 16));
			System.out.print('|');
			System.out.print((char) bytes[i]);
			System.out.print(']');

		}
		System.out.print("\n-------------------------------------------------------------------");
	}
}