package com.max.venus.common.security.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 加解密模块信息摘要计算
 * 使用MD5算法
 * @author Sendy
 *
 */
public class Digest {
	private static final Logger log = LoggerFactory.getLogger(Digest.class);
	/**
	 * 	默认编码UTF-8

	 */
	private static final String ENCODE = "UTF-8";
	
	/**
	 * md5摘要计算
	 * @param aValue String
	 * @param encoding String
	 * @return String
	 */
	public static String signMD5(String aValue, String encoding) {
		try {
			byte[] input = aValue.getBytes(encoding);
			MessageDigest md = MessageDigest.getInstance("MD5");
			return BaseConvertUtils.toHex(md.digest(input));
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			return null;
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 签名aValue使用String默认的UTF-8
	 * @param aValue String
	 * @return String
	 */
	public static String hmacSign(String aValue) {
		try {
			byte[] input = aValue.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			return BaseConvertUtils.toHex(md.digest(input));
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 签名aValue 包括akey  使用加解密模块默认的ENCODE默认UTF-8
	 * @param aValue String
	 * @param aKey String
	 * @return String
	 */
	public static String hmacSign(String aValue, String aKey) {
		return hmacSign(aValue, aKey, ENCODE);
	}

	/**计算摘要
	 * @param aValue 要计算摘要的原始信息
	 * @param aKey  String
	 * @param encoding  aValue和aKey的String对象的编码方式 java默认UTF-8
	 * @return String
	 */
	public static String hmacSign(String aValue, String aKey, String encoding) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encoding);
			value = aValue.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}
		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			return null;
		}
		//Updates the digest using the specified array of bytes.
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return BaseConvertUtils.toHex(dg);
	}

	/**
	 * sha算法计算摘要
	 * @param aValue String
	 * @param aKey String
	 * @param encoding String
	 * @return String
	 */
	public static String hmacSHASign(String aValue, String aKey, String encoding) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encoding);
			value = aValue.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}
		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 20);
		dg = md.digest();
		return BaseConvertUtils.toHex(dg);
	}

	/**
	 * 使用默认编码UTF-8计算摘要
	 * 使用sha算法
	 * @param aValue 需要计算摘要的原始信息
	 * @return String
	 */
	public static String digest(String aValue) {
		return digest(aValue, ENCODE);

	}

	/**使用指定编码计算摘要
	 * 使用sha算法
	 * @param aValue 需要计算摘要的原始信息
	 * @param encoding String
	 * @return String
	 */
	public static String digest(String aValue, String encoding) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			return null;
		}
		return BaseConvertUtils.toHex(md.digest(value));
	}


	/**
	 * 使用指定算法 指点编码计算摘要
	 * @param aValue String
	 * @param alg 指定算法名称 例如MessageDigest.getInstance("SHA"),必须jdk支持
	 * @param encoding String
	 * @return
	 */
	public static String digest(String aValue, String alg, String encoding) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(alg);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			return null;
		}
		return BaseConvertUtils.toHex(md.digest(value));
	}

	/**更新签名
	 * @param aValue String
	 * @return String
	 */
	public static String udpSign(String aValue) {
		try {
			byte[] input = aValue.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA1");
			return new String(Base64.encode(md.digest(input)), ENCODE);
		} catch (Exception e) {
			return null;
		}
	}

}
