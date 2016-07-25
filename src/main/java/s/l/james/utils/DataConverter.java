package s.l.james.utils;

import java.io.UnsupportedEncodingException;

/**
 * 数据转换器类
 * 
 * @ClassName: DataConverter
 * @Description: TODO
 * @author: James.S.L
 * @date: 2016年7月25日 下午1:16:06
 */
public class DataConverter {

	/**
	 * 将字节类型的参数转换为十六进制的字符串 如：0xAB ===> "AB"
	 * 该方法在某些情况下会抛出异常，可使用printBytsToHexString()方法进行转换
	 * 
	 * @Title: bytesToHexString
	 * @Description: TODO
	 * @param 单个字节参数
	 * @return: 字符串
	 */
	public static String byteToHexString(byte b) {

		StringBuffer sb = new StringBuffer();
		String temp = null;
		temp = Integer.toHexString(0xFF & b);
		if (temp.length() < 2) {
			sb.append(0);
			sb.append(temp.toUpperCase());
		}

		return String.valueOf(sb);
	}

	/**
	 * 将字节类型的参数转换为十六进制的字符串
	 * 
	 * @Title: printBytsToHexString
	 * @Description: TODO
	 * @param 字节数组
	 * @return: 字符串
	 */
	public static String printBytsToHexString(byte[] b) {
		String hexStr = null;
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}

			hexStr = hex.toUpperCase();
		}
		return hexStr;

	}

	/**
	 * 十六进制字符串转2进制字符串
	 * 
	 * @Title: hexToBinary
	 * @Description: TODO
	 * @param 十六进制字符逆转
	 * @return: 二进制字符串
	 */
	public static String hexToBinary(String hex) {
		if (hex == null || hex.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hex.length(); i++) {
			tmp = "0000"
					+ Integer.toBinaryString(Integer.parseInt(
							hex.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	/**
	 * 二进制字符串转十六进制字符串
	 * 
	 * @Title: binaryToHex
	 * @Description: TODO
	 * @param 二进制字符串
	 * @return: 十六进制字符串
	 */
	public static String binaryToHex(String binary) {

		if (binary == null || binary.equals("") || binary.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < binary.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(binary.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	/**
	 * 十六进制字符串转字节数组
	 * 
	 * @Title: hexToByte
	 * @Description: TODO
	 * @param 十六进制字符串
	 * @return: 字节数组
	 */
	public static byte[] hexToByte(String hex) {
		String digital = "0123456789ABCDEF";
		char[] hex2char = hex.toCharArray();
		byte[] bytes = new byte[hex.length() / 2];
		int temp;
		for (int i = 0; i < bytes.length; i++) {
			temp = digital.indexOf(hex2char[2 * i]) * 16;
			temp += digital.indexOf(hex2char[2 * i + 1]);
			bytes[i] = (byte) (temp & 0xff);
		}
		return bytes;
	}

	/**
	 * 中文转unicode码，结果不带\\u
	 * 
	 * @Title: chinaToUnicode
	 * @Description: TODO
	 * @param str
	 * @return: String
	 */
	public static String chinaToUnicode(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int chr1 = (char) str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				result += Integer.toHexString(chr1);
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}

	/**
	 * unicode码转中文，参数中带\\u
	 * 
	 * @Title: unicodeToChina
	 * @Description: TODO
	 * @param utfString
	 * @return: String
	 */
	public static String unicodeToChina(String utfString) {
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = utfString.indexOf("\\u", pos)) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(
						utfString.substring(i + 2, i + 6), 16));
			}
		}

		return sb.toString();
	}

	/**
	 * 十进制转二进制
	 * 
	 * @Title: int2byte
	 * @Description: TODO
	 * @param res
	 * @return: byte[]
	 */
	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];

		targets[0] = (byte) (res & 0xff);// 最低位
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
		return targets;
	}

	/**
	 * 获取ASCII码
	 * 
	 * @Title: getAscii
	 * @Description: TODO
	 * @param str
	 * @return
	 * @return: String
	 */
	public static String getAscii(String str) {
		String s = "";
		byte[] b = hz2gbk(str);
		for (int i = 0, max = b.length; i < max; i++) {
			s = s + Integer.toHexString(b[i] & 0xff);
			if (i < max - 1) {
				s = s + ",";
			}
		}
		return s;
	}

	public static byte[] hz2gbk(String str) {
		try {
			return str.getBytes("GBK");
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
	}

	public static String convertStringToHex(String str) {

		char[] chars = str.toCharArray();

		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}

		return hex.toString();
	}

	
	public static String convertHexToString(String hex) {

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < hex.length() - 1; i += 2) {

			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);

			temp.append(decimal);
		}

		return sb.toString();
	}

}
