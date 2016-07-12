package com.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.algorithm.Base64;

/**对称加密算法;明文按64位分组，密钥长64位，但事实上只有56位参与运算（第8,16,24,32,40,48,56,64为检验位.使得每个密钥都有奇数个1），
 * 分组后的明文和密钥按位替代或者交换的方式形成密文
 * @author zhongyi
 *
 */
public class DES {
	
	/**通过KeyGenerator获得DES密钥（SecretKey），转换成base64字符串返回
	 * @return
	 * @throws Exception
	 */
	public static String genKeyDES() throws Exception{
		KeyGenerator keyGen=KeyGenerator.getInstance("DES");
		keyGen.init(56);
		SecretKey key=keyGen.generateKey();
		String base64Str = Base64.byte2base64(key.getEncoded());
		return base64Str;
	}
	
	/**获取密钥的SecretKey对象
	 * @param base64Key
	 * @return
	 * @throws Exception
	 */
	public static SecretKey loadKeyDES(String base64Key)throws Exception{
		byte[] bytes=Base64.base642byte(base64Key);
		SecretKey key= new SecretKeySpec(bytes,"DES");
		return key;
	}
	
	public static byte[] encryptDES(byte[] source,SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] bytes=cipher.doFinal(source);
		return bytes;
	}
	
	public static byte[] decryptDES(byte[] source,SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] bytes=cipher.doFinal(source);
		return bytes;
	}
	
	public static void main(String[] args) throws Exception {
		String key=genKeyDES();
		System.out.println(key);
		//edA+lIXHaNU=

	}
}
