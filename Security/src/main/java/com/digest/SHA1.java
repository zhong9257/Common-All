package com.digest;

import java.security.MessageDigest;

import com.algorithm.Base64;

public class SHA1 {
	public static byte[] sha1(String content) throws Exception{
		MessageDigest digest=MessageDigest.getInstance("SHA1");
		byte[] bytes=digest.digest(content.getBytes("UTF-8"));
		return bytes;
	}
	
	public static String sha1Base64Str(String content) throws Exception{
		MessageDigest digest=MessageDigest.getInstance("SHA1");
		byte[] bytes=digest.digest(content.getBytes("UTF-8"));
		String base64Str=Base64.byte2base64(bytes);
		return base64Str;
	}
}
