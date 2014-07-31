package org.sevenstar.util;

import java.io.InputStream;

public class BytesHelper {
	private BytesHelper() {
	}

	public static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	public static short toShort(byte[] bytes) {
		return (short) (((-(short) Byte.MIN_VALUE + (short) bytes[0]) << 8)
				- (short) Byte.MIN_VALUE + (short) bytes[1]);
	}

	public static byte[] toBytes(int value) {
		byte[] result = new byte[4];
		for (int i = 3; i >= 0; i--) {
			result[i] = (byte) ((0xFFl & value) + Byte.MIN_VALUE);
			value >>>= 8;
		}
		return result;
	}

	public static byte[] toBytes(short value) {
		byte[] result = new byte[2];
		for (int i = 1; i >= 0; i--) {
			result[i] = (byte) ((0xFFl & value) + Byte.MIN_VALUE);
			value >>>= 8;
		}
		return result;
	}

	public static byte[] readAll(InputStream is) {
		try {
			byte[] bytes = new byte[1024];
			byte[] totals = null;
			int i = is.read(bytes);
			while (i != -1) {
				if (i < 1024 && i > 0) {
					byte[] newBytes = new byte[i];
					System.arraycopy(bytes, 0, newBytes, 0, i);
					bytes = newBytes;
				}
				if (totals == null) {
					totals = bytes;
				} else {
					byte[] newtotals = new byte[totals.length + 1024];
					System.arraycopy(totals, 0, newtotals, 0, totals.length);
					System.arraycopy(bytes, 0, newtotals, totals.length,
							bytes.length);
					totals = newtotals;
				}
				bytes = new byte[1024];
				i = is.read(bytes);
			}
			is.close();
			return totals;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		System.out
				.println(0 + "==" + BytesHelper.toInt(BytesHelper.toBytes(0)));
		System.out
				.println(1 + "==" + BytesHelper.toInt(BytesHelper.toBytes(1)));
		System.out.println(-1 + "=="
				+ BytesHelper.toInt(BytesHelper.toBytes(-1)));
		System.out.println(Integer.MIN_VALUE + "=="
				+ BytesHelper.toInt(BytesHelper.toBytes(Integer.MIN_VALUE)));
		System.out.println(Integer.MAX_VALUE + "=="
				+ BytesHelper.toInt(BytesHelper.toBytes(Integer.MAX_VALUE)));
		System.out
				.println(Integer.MIN_VALUE
						/ 2
						+ "=="
						+ BytesHelper.toInt(BytesHelper
								.toBytes(Integer.MIN_VALUE / 2)));
		System.out
				.println(Integer.MAX_VALUE
						/ 2
						+ "=="
						+ BytesHelper.toInt(BytesHelper
								.toBytes(Integer.MAX_VALUE / 2)));
	}

}
