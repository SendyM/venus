package com.max.venus.common.security.esapi;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.Encryptor;
import org.owasp.esapi.crypto.CipherText;
import org.owasp.esapi.crypto.PlainText;
import org.owasp.esapi.errors.EncryptionException;
import org.owasp.esapi.errors.IntegrityException;

import javax.crypto.SecretKey;
import java.io.IOException;

/**
 * ESAPI加解密API
 * @author Sendy
 * @since 2016年10月11日 上午10:39:23
 * @version V1.0
 * 
 */
public class EsapiEncryptor {
	  private static final Encryptor ENCRYPTOR = ESAPI.encryptor();

	  private static final Encoder ENCODER = ESAPI.encoder();

	  private static final int HASH_ITERATIONS = 1024;

	  public String encrypt(String text)throws EncryptException
	  {
	    PlainText plainText = new PlainText(text);
	    try {
	      CipherText cipherText = ENCRYPTOR.encrypt(plainText);
	      return ENCODER.encodeForBase64(cipherText.asPortableSerializedByteArray(), false); }
	    	catch (EncryptionException e) {
	    		throw new EncryptException(e);
	    }
	  }

	  public String encrypt(SecretKey key, String text) throws EncryptException
	  {
	    PlainText plainText = new PlainText(text);
	    CipherText cipherText = null;
	    try {
	      cipherText = ENCRYPTOR.encrypt(key, plainText);
	      return ENCODER.encodeForBase64(cipherText.asPortableSerializedByteArray(), false); }
	    	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public String decrypt(byte[] data) throws EncryptException
	  {
	    try
	    {
	      PlainText plainText = ENCRYPTOR.decrypt(CipherText.fromPortableSerializedBytes(data));
	      return plainText.toString(); } 
	    	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public String decrypt(String text)throws IOException, EncryptException
	  {
	    byte[] data = ENCODER.decodeFromBase64(text);
	    return decrypt(data);
	  }

	  public String decrypt(SecretKey key, byte[] data) throws EncryptException
	  {
	    try
	    {
	      PlainText plainText = ENCRYPTOR.decrypt(key, CipherText.fromPortableSerializedBytes(data));
	      return plainText.toString(); } 
	      	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public String decrypt(SecretKey key, String text) throws EncryptException, IOException
	  {
	    byte[] data = ENCODER.decodeFromBase64(text);
	    return decrypt(key, data);
	  }

	  public String hash(String text, String salt) throws EncryptException
	  {
	    return hash(text, salt, 1024);
	  }

	  public String hash(String text, String salt, int iterations)throws EncryptException
	  {
	    try
	    {
	      return ENCRYPTOR.hash(text, salt, iterations); }
	    	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public String sign(String text) throws EncryptException
	  {
	    try
	    {
	      return ENCRYPTOR.sign(text); }
	    	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public boolean verifySignature(String sign, String data)
	  {
	    return ENCRYPTOR.verifySignature(sign, data);
	  }

	  public String seal(String text, long expiration)throws EncryptException
	  {
	    try
	    {
	      return ENCRYPTOR.seal(text, expiration); }
	    	catch (IntegrityException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public String unseal(String text)throws EncryptException
	  {
	    try
	    {
	      return ENCRYPTOR.unseal(text); }
	    	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public long getCurrentTime()
	  {
	    return ENCRYPTOR.getTimeStamp();
	  }

	  public long getRelativeTime(long offset)
	  {
	    return ENCRYPTOR.getRelativeTimeStamp(offset);
	  }
}
