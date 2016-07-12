package com.transform;

public class ByteAndHex {
	
	public static String byte2hex(byte[] bytes){
		StringBuffer hex=new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			byte b= bytes[i];
			boolean negative = false;
			if(b<0)  negative=true;
			int inte=Math.abs(b);
			if(negative) inte= inte | 0x80;
			//负数转正数（最高位的负号变成数值计算），再转16进制
			String temp=Integer.toHexString(inte&0xFF);
			if(temp.length()==1){
				hex.append(0);
			}
			
			hex.append(temp.toLowerCase());
		}
		return hex.toString();
	}
	
	
	private static byte[] hex2bytes(String hex){
		byte[] bytes=new byte[hex.length()/2];
		for (int i = 0; i < hex.length(); i=i+2) {
			String subStr=hex.substring(i,i+2);
			boolean negative =false;
			int inte=Integer.parseInt(subStr, 16);
			if(inte>127) negative = true;
			if(inte==128){
				inte=-128;
			}else if(negative){
				inte=0-(inte&0x7F);
			}
			byte b=(byte)inte;
			bytes[i/2]=b;
		}
		
		return bytes;
	}
	
	public static void main(String[] args) {
		byte a=-65;
		byte b=2;
		byte c= (byte)(a*b);
		System.out.println(c);
		System.out.println(Long.MAX_VALUE); //2的64
	}
}
