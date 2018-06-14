package com.max.venus.common.security.esapi;

/**
 * ESAPI工具类的工厂
 * @author Sendy
 * @since 2016年10月11日 上午10:39:48
 * @version V1.0
 * 
 */
public class Esapi {
	private static final EsapiEncryptor ENCRYPT_UTILS = new EsapiEncryptor();

	  private static final EsapiEncoder ENCODER_UTILS = new EsapiEncoder();

	  public static EsapiEncryptor encryptor()
	  {
	    return ENCRYPT_UTILS;
	  }

	  public static EsapiEncoder encoder()
	  {
	    return ENCODER_UTILS;
	  }
}
