package com.zondy.util;

import java.nio.charset.Charset;

public class ByteUtil {
	private static String hexStr =  "0123456789ABCDEF";
    private static String[] binaryArray =
            {"0000","0001","0010","0011",
                    "0100","0101","0110","0111",
                    "1000","1001","1010","1011",
                    "1100","1101","1110","1111"};
	public static byte[] getBytes(short data) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) (data & 0xff);
		bytes[1] = (byte) ((data & 0xff00) >> 8);
		return bytes;
	}
 
	public static byte[] getBytes(char data) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) (data);
		bytes[1] = (byte) (data >> 8);
		return bytes;
	}
	
 
	public static byte[] getBytes(int data) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (data & 0xff);
		bytes[1] = (byte) ((data & 0xff00) >> 8);
		bytes[2] = (byte) ((data & 0xff0000) >> 16);
		bytes[3] = (byte) ((data & 0xff000000) >> 24);
		return bytes;
	}
 
	public static byte[] getBytes(long data) {
		byte[] bytes = new byte[8];
		bytes[0] = (byte) (data & 0xff);
		bytes[1] = (byte) ((data >> 8) & 0xff);
		bytes[2] = (byte) ((data >> 16) & 0xff);
		bytes[3] = (byte) ((data >> 24) & 0xff);
		bytes[4] = (byte) ((data >> 32) & 0xff);
		bytes[5] = (byte) ((data >> 40) & 0xff);
		bytes[6] = (byte) ((data >> 48) & 0xff);
		bytes[7] = (byte) ((data >> 56) & 0xff);
		return bytes;
	}
 
	public static byte[] getBytes(float data) {
		int intBits = Float.floatToIntBits(data);
		return getBytes(intBits);
	}
 
	public static byte[] getBytes(double data) {
		long intBits = Double.doubleToLongBits(data);
		return getBytes(intBits);
	}
 
	public static byte[] getBytes(String data, String charsetName) {
		Charset charset = Charset.forName(charsetName);
		return data.getBytes(charset);
	}
 
	public static byte[] getBytes(String data) {
		return getBytes(data, "GBK");
	}
 
	public static short getShort(byte[] bytes) {
		return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
	}
 
	public static char getChar(byte[] bytes) {
		return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
	}
 
	public static int getInt(byte[] bytes) {
		return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16))
				| (0xff000000 & (bytes[3] << 24));
	}
 
	public static long getLong(byte[] bytes) {
		return (0xffL & (long) bytes[0]) | (0xff00L & ((long) bytes[1] << 8)) | (0xff0000L & ((long) bytes[2] << 16))
				| (0xff000000L & ((long) bytes[3] << 24)) | (0xff00000000L & ((long) bytes[4] << 32))
				| (0xff0000000000L & ((long) bytes[5] << 40)) | (0xff000000000000L & ((long) bytes[6] << 48))
				| (0xff00000000000000L & ((long) bytes[7] << 56));
	}
 
	public static float getFloat(byte[] bytes) {
		return Float.intBitsToFloat(getInt(bytes));
	}
 
	public static double getDouble(byte[] bytes) {
		long l = getLong(bytes);
		//System.out.println(l);
		return Double.longBitsToDouble(l);
	}
 
	public static String getString(byte[] bytes, String charsetName) {
		return new String(bytes, Charset.forName(charsetName));
	}
 
	public static String getString(byte[] bytes) {
		return getString(bytes, "GBK");
	}
	
	/**
     * 将字节数组转换成十六进制字符串
     * @param bytes 待转换的字节数组
     * @return 转换之后的十六进制字符串
     */
    public static String bytesToHexStr(byte[] bytes) {
 
        String result = "";
        String hex = "";
        int F0 = 0xF0;
        int ZeroF = 0x0F;
        for (int i = 0; i < bytes.length; i++) {
            //字节高4位
            hex = String.valueOf("0123456789ABCDEF".charAt((bytes[i] & F0) >> 4));
            //字节低4位
            hex += String.valueOf("0123456789ABCDEF".charAt(bytes[i] & ZeroF));
            result += hex;
        }
        return result;
    }
 
    /**
     * 将16进制转换成二进制字节数组,注意16进制不要输入0x，只需输入ff，不要输入成0xff
     * @param hexString 带转换的十六进制字符串
     * @return 转换之后的字节数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
 
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
 
 
 
    /**
     * 二进制数组转换为二进制字符串   2-2
     */
    public static String bytesToBinStr(byte[] bArray) {
        String outStr = "";
        int pos = 0;
        for (byte b : bArray) {
            //高四位
            pos = (b & 0xF0) >> 4;
            outStr += binaryArray[pos];
            //低四位
            pos = b & 0x0F;
            outStr += binaryArray[pos];
        }
        return outStr;
    }
 
    /**
     * 将十六进制字符串转换为二进制字符串
     */
    public static String hexStr2BinStr(String hexString){
        return bytesToBinStr(hexStringToBytes(hexString));
    }
 
	public static void main(String[] args) {
		short s = 122;
		int i = 122;
		long l = 1222222;
 
		char c = 'a';
 
		float f = 122.22f;
		double d = 122.22;
 
		String string = "我是好孩子";
		System.out.println(s);
		System.out.println(i);
		System.out.println(l);
		System.out.println(c);
		System.out.println(f);
		System.out.println(d);
		System.out.println(string);
 
		System.out.println("**************");
 
		System.out.println(getShort(getBytes(s)));
		System.out.println(getInt(getBytes(i)));
		System.out.println(getLong(getBytes(l)));
		System.out.println(getChar(getBytes(c)));
		System.out.println(getFloat(getBytes(f)));
		System.out.println(getDouble(getBytes(d)));
		System.out.println(getString(getBytes(string)));
	}
}
