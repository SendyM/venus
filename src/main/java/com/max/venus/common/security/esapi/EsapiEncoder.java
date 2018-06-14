package com.max.venus.common.security.esapi;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.PreparedString;
import org.owasp.esapi.codecs.*;
import org.owasp.esapi.errors.EncodingException;

import java.io.IOException;

/**
 * ESAPI 编解码API
 * @author Sendy
 * @since 2016年10月11日 上午10:38:55
 * @version V1.0
 * 
 */
public class EsapiEncoder{
  private static final Encoder ENCODER = ESAPI.encoder();
  private static final char HTML_PLACEHODER = '?';

  public String sqlEncode(String inputString, DatabaseCodec dbcodec)
  {
    return ENCODER.encodeForSQL(dbcodec.codec(), inputString);
  }

  public String sqlPreparedString(String sqlTemplate, String[] paras, DatabaseCodec dbcodec)
  {
    PreparedString sqlPreparedString = new PreparedString(sqlTemplate, dbcodec.codec());
    for (int i = 0; i < paras.length; i++) {
      sqlPreparedString.set(i + 1, paras[i]);
    }
    return sqlPreparedString.toString();
  }

  public String htmlEncode(String inputString)
  {
    return ENCODER.encodeForHTML(inputString);
  }

  public String htmlAttributeEncode(String inputString)
  {
    return ENCODER.encodeForHTMLAttribute(inputString);
  }

  public String cssEncode(String inputString)
  {
    return ENCODER.encodeForCSS(inputString);
  }

  public String javaScriptEncode(String inputString)
  {
    return ENCODER.encodeForJavaScript(inputString);
  }

  public String urlEncode(String inputString) throws Exception
  {
    try
    {
      return ENCODER.encodeForURL(inputString);
    }
    catch (EncodingException e) {
    	throw new Exception(e);
    }
  }

  public String urlDecode(String url) throws Exception
  {
    try
    {
      return ENCODER.decodeFromURL(url); }
    	catch (EncodingException e) {
    	  throw new Exception(e);
    }
  }

  public String xmlEncode(String inputString)
  {
    return ENCODER.encodeForXML(inputString);
  }

  public String xmlAttributeEncode(String inputString)
  {
    return ENCODER.encodeForXMLAttribute(inputString);
  }

  public String webPreparedString(String strTemplate, String[] paras, Codec[] codecs, char placeholder)
  {
    PreparedString clientSidePreparedString = new PreparedString(strTemplate, placeholder, TextCodec.HTML.codec());
    for (int i = 0; i < paras.length; i++) {
      clientSidePreparedString.set(i + 1, paras[i], codecs[i]);
    }
    return clientSidePreparedString.toString();
  }

  public String webPreparedString(String strTemplate, String[] paras, Codec[] codecs)
  {
    return webPreparedString(strTemplate, paras, codecs, '?');
  }

  public String webPreparedString(String strTemplate, String[] paras, TextCodec[] codecs, char placeholder)
  {
    PreparedString clientSidePreparedString = new PreparedString(strTemplate, placeholder, TextCodec.HTML.codec());
    for (int i = 0; i < paras.length; i++) {
      clientSidePreparedString.set(i + 1, paras[i], codecs[i].codec());
    }
    return clientSidePreparedString.toString();
  }

  public String webPreparedString(String strTemplate, String[] paras, TextCodec[] codecs)
  {
    return webPreparedString(strTemplate, paras, codecs, '?');
  }

  public String webPreparedString(String strTemplate, String param, TextCodec codec)
  {
    return webPreparedString(strTemplate, new String[] { param }, new TextCodec[] { codec }, '?');
  }

  public String encodeForBase64(byte[] data)
  {
    return ENCODER.encodeForBase64(data, false);
  }

  public byte[] decodeFromBase64(String text)
    throws IOException
  {
    return ENCODER.decodeFromBase64(text);
  }

  public static enum TextCodec
  {
    CSS(new CSSCodec()), 
    HTML(new HTMLEntityCodec()), 
    JS(new JavaScriptCodec()), 
    PERCENT(new PercentCodec()), 
    XML(new XMLEntityCodec()), 
    UNIX(new UnixCodec()), 
    WINDOWS(new WindowsCodec()), 
    VB(new VBScriptCodec());

    private Codec codec;

    private TextCodec(Codec codec) { this.codec = codec; }

    public Codec codec()
    {
      return this.codec;
    }
  }

  public static enum DatabaseCodec
  {
    ORACLE(new OracleCodec()), 
    MYSQL_ANSI(new MySQLCodec(MySQLCodec.Mode.ANSI)), 
    MYSQL_STANDARD(new MySQLCodec(MySQLCodec.Mode.STANDARD)), 
    DB2(new DB2Codec());

    private Codec codec;

    private DatabaseCodec(Codec codec) { this.codec = codec; }

    public Codec codec()
    {
      return this.codec;
    }
  }
}