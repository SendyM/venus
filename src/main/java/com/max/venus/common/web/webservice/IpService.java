package com.max.venus.common.web.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Ip服务
 * 
 */
@WebService(name = "IpService", targetNamespace = "http://www.myweiixn.club/", portName = "IpServicePort", serviceName = "IpService")
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
public interface IpService {

	/**
	 * @param ip String
	 * @return String
	 */
	@WebMethod
	String getIpInfo(@WebParam(name = "ip") String ip);

}
