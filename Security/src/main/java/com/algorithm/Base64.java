package com.algorithm;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {
	public static String byte2base64(byte[] bytes){
		BASE64Encoder encoder= new BASE64Encoder();
		return encoder.encode(bytes);
	}
	
	public static byte[] base642byte(String base64) throws IOException{
		BASE64Decoder decoder= new BASE64Decoder();
		return decoder.decodeBuffer(base64);
	}
}
