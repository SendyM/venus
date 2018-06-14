package com.max.venus.common.annotation;

import com.max.venus.common.config.Constants;

import java.lang.annotation.*;

/**
 * 资源管理注解 通过辅助工具扫描到本注解
 * 提取信息到数据库表中以便维护信息的一致性和方便资源权限管理
 * @author Sendy
 * @since 2016年10月12日 下午4:34:37
 * @version V1.0
 * 
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysRes {
	
	
	/**
	 * 资源的id
	 * 
	 * @return String id
	 */
	public String id() default "";

	/**
	 * 资源的名称
	 * 
	 * @return String value
	 */
	public String name() default "";
	
	/**
	 * 资源的有效性 默认1 有效 0无效
	 * 
	 * @return String value
	 */
	public String isValid() default "1";


	/**
	 * 资源的父id
	 * 
	 * @return boolean value
	 */
	public String parentId() default "";
	

	/**
	 * 资源的父ids
	 * 
	 * @return boolean value
	 */
	public String parentIds() default ""; 


	/**
	 * 资源的类型
	 * 默认按钮
	 * @return String value
	 */
	public String type() default Constants.BUTTON;

}
