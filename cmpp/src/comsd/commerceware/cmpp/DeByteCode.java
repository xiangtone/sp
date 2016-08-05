package comsd.commerceware.cmpp;

/**
 * Title: Description: Copyright: Copyright (c) 2001 Company:
 * 
 * @author
 * @version 1.0
 */

public class DeByteCode {

	private int len;
	private int size;
	private int offset;

	private byte bytes[];

	protected int getLen() {
		return len;
	}

	protected int getSize() {
		return size;
	}

	protected DeByteCode(int i) {
		bytes = new byte[i];
		size = i;
		len = 0;
		offset = 0;
	}

	protected DeByteCode(byte a[]) {
		bytes = a;
		size = a.length;
		len = a.length;
		offset = 0;
	}

	public byte[] getBytes(int i) throws Exception {
		if (offset + i > size) {
			throw new Exception("underrun.int16.");
		} else {
			byte b[] = new byte[i];
			for (int j = 0; j < i; j++) {
				b[j] = bytes[offset + j];
			}

			offset += i;
			return b;
		}
	}

	public short int16() throws Exception {
		if (offset + 1 > size) {
			throw new Exception("underrun.int16.");
		} else {
			short word0 = (short) ((bytes[offset] & 0xff) << 8 | bytes[offset + 1] & 0xff);
			offset += 2;
			return word0;
		}
	}

	public byte int8() throws Exception {
		if (offset > size) {
			throw new Exception("underrun.int8.");
		} else {
			byte byte0 = bytes[offset];
			offset++;
			return byte0;
		}
	}

	public int int32() throws Exception {
		if (offset + 3 > size) {
			throw new Exception("underrun.int32.");
		} else {
			int i = (bytes[offset] & 0xff) << 24 | (bytes[offset + 1] & 0xff) << 16 | (bytes[offset + 2] & 0xff) << 8
					| bytes[offset + 3] & 0xff;
			offset += 4;
			return i;
		}
	}

	public String asciiz(int i) throws Exception {
		byte abyte0[] = new byte[i + 1];
		int j;
		System.out.println("offset value is:" + offset);
		for (j = 0; j < i + 1 && offset + j < size && bytes[offset + j] != 0; j++)
			abyte0[j] = bytes[offset + j];

		if (offset + j == size + 1) {
			throw new Exception("underrun.string.");
			// System.out.println("the length is:" + (offset + j));
		}

		if (j == i + 1)
			throw new Exception("toolong.string.");
		offset += i;
		if (j == 0) {
			return null;
		} else {
			abyte0[j] = 0;
			String str = new String(abyte0, 0, 0, j);
			// String str = new String(
			if (str == null)
				str = "null!";
			return str;
		}
	}

	/*
	 * public static String asHex (byte hash[]) { StringBuffer buf = new
	 * StringBuffer(hash.length * 2); int i;
	 * 
	 * for (i = 0; i < hash.length; i++) { if (((int) hash[i] & 0xff) < 0x10)
	 * buf.append("0"); buf.append(Long.toString((int) hash[i] & 0xff, 16)); }
	 * 
	 * return buf.toString(); }
	 */

	public void printBytes() {
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
		System.out.println("\n-------------------------------------------------------------------");
	}
}