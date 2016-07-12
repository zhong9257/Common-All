package com.encrypt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.algorithm.Base64;

/**非对称加密算法又称为公开密钥算法，它需要两个密钥，一个称为公开密钥（即公钥），另外一个称为密钥。
 * 公钥和密钥配对使用，公钥加密就需要私钥解密，私钥加密需要公钥解密。因为加密和解密使用2个不同的密钥，
 * 所以称为非对称加密算法。目前最广泛使用的是rsa。
 * @author zhongyi
 *
 */
public class RSA {
	
	
	public static KeyPair getKeyPair() throws NoSuchAlgorithmException{
		KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(512);
		KeyPair keyPair=keyPairGenerator.generateKeyPair();
		return keyPair;
	}
	
	public static String getPublickey(KeyPair keyPair){		
		PublicKey publicKey=keyPair.getPublic();
		byte[] bytes = publicKey.getEncoded();
		return Base64.byte2base64(bytes);	
	}
	
	public static String getPrivatekey(KeyPair keyPair){		
		PrivateKey  privatekey=keyPair.getPrivate();
		byte[] bytes = privatekey.getEncoded();
		return Base64.byte2base64(bytes);	
	}
	
	public static PublicKey string2PublicKey(String base64PublicKeyStr) throws Exception{
		byte[] bytes=Base64.base642byte(base64PublicKeyStr);
		X509EncodedKeySpec keySpec=new X509EncodedKeySpec(bytes);
		KeyFactory factory=KeyFactory.getInstance("RSA");
		PublicKey publicKey=factory.generatePublic(keySpec);
		return publicKey;
	}
	
	public static PrivateKey string2PrivateKey(String base64PrivateKeyStr) throws Exception{
		byte[] bytes=Base64.base642byte(base64PrivateKeyStr);
		X509EncodedKeySpec keySpec=new X509EncodedKeySpec(bytes);
		KeyFactory factory=KeyFactory.getInstance("RSA");
		PrivateKey publicKey=factory.generatePrivate(keySpec);
		return publicKey;
	}
	
	public static byte[] publicEncrypt(byte[] source,PublicKey publicKey) throws Exception{
		Cipher cipher=Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytes=cipher.doFinal(source);
		return bytes;
	}
	
	public static byte[] privateDecrypt(byte[] source,PrivateKey privateKey) throws Exception{
		Cipher cipher=Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bytes=cipher.doFinal(source);
		return bytes;
	}
}
