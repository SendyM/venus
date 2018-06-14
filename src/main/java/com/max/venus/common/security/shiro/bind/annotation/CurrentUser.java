package com.max.venus.common.security.shiro.bind.annotation;

import com.max.venus.common.config.Constants;

import java.lang.annotation.*;

/**
 * 绑定当前登录的用户 不同于@ModelAttribute
 * 
 * @author fbf
 */
@Target({ ElementType.PARAMETER })
// 用于参数上
@Retention(RetentionPolicy.RUNTIME)
// 范围 运行时有效
@Documented
public @interface CurrentUser {

	/**
	 * 当前用户在request中的名字
	 * 
	 * @return String
	 */
	String value() default Constants.CURRENT_USER;

}
