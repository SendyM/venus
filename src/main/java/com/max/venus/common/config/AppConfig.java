package com.max.venus.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 全局配置类 读取classpath:config.properties配置文件
 * 
 * @author Sendy
 */
@Component
@PropertySources({// PropertySources注解是spring4.1才开始支持
		@PropertySource("classpath:config.properties")//,
		// 可以配置多个，如果是相同的key，则最后一个起作用
		//@PropertySource(value = "classpath:db.properties", ignoreResourceNotFound = true)
		// ignoreResourceNotFound是spring4.2才开始支持，默认为false，如果不存在会抛出异常，设置为true，忽略不存在的情况
})
public class AppConfig {
	/**
	 * 日志工具
	 */
	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

	/**
	 * spring推荐使用Environment的方式来操作
	 */
	@Resource
	private Environment env;

	/**
	 * getValue读取系统指定properties的key的值
	 * 
	 * @param key
	 * @return value
	 */
	public String getValue(String key) {
		return env.getProperty(key);
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

}
