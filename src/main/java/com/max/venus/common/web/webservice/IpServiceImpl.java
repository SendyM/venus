package com.max.venus.common.web.webservice;

import org.apache.commons.lang.StringUtils;

import com.max.venus.common.util.IPUtils;

public class IpServiceImpl implements IpService {

	@Override
	public String getIpInfo(String ip) {
		if(StringUtils.isBlank(ip)){
			return "";
		}
		String info=IPUtils.getIpInfo(ip);
		if(StringUtils.isNotBlank(info)){
			return info;
		}
		return "";
		
	}

}
