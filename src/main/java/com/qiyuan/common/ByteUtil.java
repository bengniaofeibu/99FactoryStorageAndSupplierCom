package com.qiyuan.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class ByteUtil {

	/**
	 * 把两个字节数组合成一个
	 * 
	 * @param begin
	 * @param second
	 * @return
	 */
	public static byte[] byteAndByte(byte[] begin, byte[] second) {

		if (begin == null || begin.length == 0) {

			if (second != null && second.length != 0) {

				return second;

			} else {

				return null;
			}

		} else if (second == null || second.length == 0) {

			return begin;
		}

		byte[] newTotal = new byte[begin.length + second.length];

		for (int i = 0; i < begin.length; i++) {

			newTotal[i] = begin[i];
		}

		for (int i = begin.length; i < second.length + begin.length; i++) {

			newTotal[i] = second[i - begin.length];
		}

		return newTotal;
	}

	/**
	 * 把字节数组转换成十六进制
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {

		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				hv = "0" + hv;
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();

	}

	/**
	 * 把字节数组转换成string类型
	 * 
	 * @param src
	 * @param begin
	 * @param length
	 * @return
	 */
	public static String bytesToString(byte[] src, int begin, int length) {

		String str1 = null;
		StringBuilder sb = new StringBuilder("");
		if (begin == 0 && length == 0) {
			for (byte element : src) {
				sb.append(String.valueOf(element));
			}
		} else {
			for (int i = begin; i < begin + length; i++) {
				byte element = src[i];
				sb.append(String.valueOf(element));
			}
		}

		str1 = sb.toString();
		return str1;

	}

	/**
	 * 根据BitMap获取对应的域
	 * 
	 * @param bitMap
	 * @return
	 */
	public static List<String> getBitMapLocation(byte[] bitMap) {
		String binary = byteToBinary(bitMap);
		List<String> bitMapLoc = new ArrayList<String>();
		for (int i = 0; i < binary.length(); i++) {
			String str = binary.charAt(i) + "";
			if ("1".equals(str)) {
				bitMapLoc.add((i + 1) + "");
			}
		}
		return bitMapLoc;
	}

	/**
	 * byte[] 转换成 2进制字符串数据用于定位bitMap
	 * 
	 * @param data
	 * @return
	 */
	public static String byteToBinary(byte[] data) {
		String dataString = ByteUtil.bytesToHexString(data);
		String binaryValue = "";
		for (int i = 0; i < dataString.length(); i++) {
			String strValue = dataString.charAt(i) + "";
			int intValue = Integer.parseInt(strValue, 16);
			String binaryTempValue = Integer.toBinaryString(intValue);
			while (binaryTempValue.length() != 4) {
				binaryTempValue = "0" + binaryTempValue;
			}
			binaryValue += binaryTempValue;
		}
		return binaryValue;
	}

	/**
	 * @函数功能: 10进制串转为BCD码
	 * @输入参数: 10进制串
	 * @输入参数: 不足位数是否在末尾添0，true--在末尾添加 false--在左边添加
	 * @输出结果: BCD码
	 * @param asc
	 * @return
	 */
	public static byte[] strToBCD(String asc, boolean atEnd) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			if (atEnd) {
				asc = asc + "0";
			} else {
				asc = "0" + asc;
			}

			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	public static String keepAfterPoint2(String data) {
		if (data.indexOf(".") != -1) {
			int indexOf = data.indexOf(".");
			while (data.length() - 1 - indexOf < 2) {
				return data + "0";
			}
			while (data.length() - 1 - indexOf > 2) {
				return data.substring(0, indexOf + 3);
			}
			return data;
		} else {
			return data + "00";
		}
	}

	/**
	 * 阿斯卡马
	 * 
	 * @param input
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getASCIIBytes(String input) throws UnsupportedEncodingException {
//		byte[] a = input.getBytes();
//		String temp = new String(a,"GBK");
		byte[] b = input.getBytes("GBK");
//		char[] c = input.toCharArray();
//	    byte[] b = new byte[c.length];
//		for (int i = 0; i < c.length; i++)
//		b[i] = (byte) (c[i] & 0x007F);
		return b;
	}

	/**
	 * 整数转化为16进制无符号数
	 * 
	 * @param len
	 * @return
	 */
	public static byte[] getLengthBytes(int len) {
		String len_value = Integer.toHexString(len);
		byte[] lenBytes = strToBCD(len_value, false);
		return lenBytes;
	}

	/**
	 * 根据传递的byte数组(BCD码)返回整型字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToIntStr(byte[] bytes) {
		String bytesToHexString = ByteUtil.bytesToHexString(bytes);
		String intStr = Integer.valueOf(bytesToHexString, 16).toString();
		return intStr;
	}

	/**
	 * ASCII码转化为字符串
	 * 
	 * @param bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String asciiToStr(byte[] bytes) throws UnsupportedEncodingException {
		String str = new String(bytes,"GBK");
		return str;
	}

	/**
	 * ASCII码转化为字符串
	 * 
	 * @param bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String asciiToStr(byte[] bytes,int start,int length) throws UnsupportedEncodingException {
		byte[]temp = new byte[length];
		for(int i = 0;i<length;i++){
			temp[i] = bytes[i+start];
		}
		String str = new String(temp,"GBK");
		return str;
	}
	

	/**
	 * 转化为Unicode
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] toUnicode(String str) throws UnsupportedEncodingException{
		byte[] b1 = str.getBytes("UNICODE");
// byte[] b2 = new byte[b1.length-2];
// for(int i=2;i<b1.length;i++){
// b2[i-2] = b1[i];
// }
		return b1;
	}
	
	/**
	 * Unicode转化为String
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String fromUnicode(byte[] b) throws UnsupportedEncodingException{
		String str = new String(b,"UNICODE");
		return str;
	}
	
	
	/**
	 * 数字String转化为16进制无符号数
	 * 
	 * @param len
	 * @return
	 */
	public static byte[] getLengthBytes(String len) {
		byte[] lenBytes = new byte[len.length()];
		for(int i=0;i<len.length();i++){
			String len_value = Integer.toHexString(Integer.parseInt(len.substring(i, i+1)));
			System.arraycopy(strToBCD(len_value, false), 0, lenBytes,
					i, strToBCD(len_value, false).length) ;
		}
		
		
		return lenBytes;
	}
	

	
	
	/**
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param len
	 *            除信息内容外的长度
	 * @return
	 */
// public static String msgToStr(byte[] b,int len){
// String str = "";
// try {
// //HEX
// System.arraycopy(getLengthBytes(str.substring(0,2)),
// 0, msg, 0, 2);
// //ASCII
// System.arraycopy(getASCIIBytes(str.substring(2, len)),
// 0, msg, 2, len-2);
// //UNICODE
// System.arraycopy(str.substring(len).getBytes("UNICODE"),
// 2, msg, len, str.substring(len).getBytes("UNICODE").length-2);
//			
// } catch (NumberFormatException e) {
// e.printStackTrace();
// } catch (UnsupportedEncodingException e) {
// e.printStackTrace();
// } catch (Exception e) {
// e.printStackTrace();
// }
// return msg;
// }
	
	public static byte[] setBlank(byte[] b ,int len){
		while( b.length < len ){
			byte[] temp = { 0x00 };
			b = byteAndByte(b,temp);
		}
		return b;
	}
	//将字符数组转成byte数组
	public static byte[] hexStr2Bytes(String src)  
    {  
        int m=0,n=0;  
        int l=src.length()/2;  
        System.out.println(l);  
        byte[] ret = new byte[l];  
        for (int i = 0; i < l; i++)  
        {  
            m=i*2+1;  
            n=m+1;  
            ret[i] = Byte.decode( src.substring(i*2, m) + src.substring(m,n));  
        }  
        return ret;  
    }  	
	
}
