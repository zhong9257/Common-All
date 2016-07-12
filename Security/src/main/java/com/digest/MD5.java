package com.digest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.transform.ByteAndHex;

/**信息摘要，摘要长度128位。
 * @author zhongyi
 *
 */
public class MD5 {
	
	/**生成指定字符串的MD5摘要信息，返回MD5bytes
	 * @param str
	 * @return bytes
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] md5bytes(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md5=MessageDigest.getInstance("MD5");
		byte[] bytes=md5.digest(str.getBytes("utf-8"));
		return bytes;
	}
	
	
	/**生成指定字符串的MD5摘要信息，返回16进制表示形式的字符串（32个字符）
	 * @param str
	 * @return 16进制表示形式的字符串（32个字符）
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String md5HexString(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md5=MessageDigest.getInstance("MD5");
		byte[] bytes=md5.digest(str.getBytes("utf-8"));
		String base64HexStr=ByteAndHex.byte2hex(bytes);
		return base64HexStr;
	}
}
