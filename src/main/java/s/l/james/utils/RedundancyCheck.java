package s.l.james.utils;

/**
 * 数据冗余校验类 该类主要包含CRC16校验及CS校验
 * 
 * @ClassName: RedundancyCheck
 * @Description: TODO
 * @author: James.S.L
 * @date: 2016年7月25日 下午1:06:20
 */
public class RedundancyCheck {

	/**
	 * @Title: CS校验
	 * @Description: TODO
	 * @param 所需校验的字节数据
	 * @return: 一个字节的CS校验码
	 */
	public static byte checkCS(byte[] data) {

		int CS = 0x0;
		for (byte bt : data) {
			CS = CS ^ bt;
			for (int j = 1; j <= 8; j++) {
				if ((CS & 0x80) == 0x80) {
					CS = (CS << 1) ^ 0xE5;
				} else {
					CS = CS << 1;
				}
			}
		}
		return (byte) CS;

	}

	/**
	 * @Title: CRC16校验
	 * @Description: TODO
	 * @param 所需校验的字节数组
	 * @return: 返回长度为2的CRC16字节数组，高位在后，低位在前
	 */
	public static byte[] CheckCRC16(byte[] data) {

		byte[] CRC16 = new byte[2];
		int CRC = 0x0000ffff;
		int POLYNOMIAL = 0x0000a001;
		int i, j;

		if (data.length == 0) {
			return null;
		}
		for (i = 0; i < data.length; i++) {
			CRC ^= ((int) data[i] & 0x000000ff);
			for (j = 0; j < 8; j++) {
				if ((CRC & 0x00000001) != 0) {
					CRC >>= 1;
					CRC ^= POLYNOMIAL;
				} else {
					CRC >>= 1;
				}
			}
		}

		CRC16[0] = (byte) (CRC & 0x00ff);
		CRC16[1] = (byte) (CRC >> 8);

		return CRC16;

	}

}
