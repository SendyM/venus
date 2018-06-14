package com.max.venus.common.security.shiro.filter;

import com.max.venus.common.config.Constants;
import com.max.venus.common.dto.GeneralReturnData;
import com.max.venus.common.dto.R;
import com.max.venus.common.util.AjaxUtils;
import com.max.venus.common.util.CaptchaUtils;
import com.max.venus.common.util.JsonUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码验证过滤器 用于form表单 post方式的验证码验证 shiro filter扩展 如果使用注意：
 * 用于验证码验证的Shiro拦截器在用于身份认证的拦截器FormAuthenticationFilter之前运行！！！
 * 
 * @author Sendy
 */
public class CaptchaValidateFilter extends AccessControlFilter {

	/**
	 * 是否开启验证码支持
	 */
	private boolean captchaEnable = Constants.CAPTCHA_ENABLED;

	/**
	 * 前台提交的验证码参数名
	 */
	private String requestCaptchaParam = Constants.REQUEST_CAPTCHA;
	/**
	 * 验证码验证失败后存储到的属性名
	 */

	private String captchaFailureKey = Constants.LOGIN_FAILURE_KEY;

	public String getCaptchaFailureKey() {
		return captchaFailureKey;
	}

	public String getRequestCaptchaParam() {
		return requestCaptchaParam;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		// 1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
		request.setAttribute(Constants.CAPTCHA_ENABLED_KEY, captchaEnable);

		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		// 2、判断验证码是否禁用 或不是表单提交（允许访问）
		if (!captchaEnable
				|| !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
			return true;
		}
		// 3、此时是表单提交，验证验证码是否正确
		return CaptchaUtils.validateResponse(httpServletRequest,
				httpServletRequest.getParameter(requestCaptchaParam));
	}

	public boolean isCaptchaEbabled() {
		return captchaEnable;
	}

	/**
	 * 当访问拒绝时在request域中放入错误消息
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		if(AjaxUtils.isAjax((HttpServletRequest)request, (HttpServletResponse)response)){
			//直接输出json结果 不再转发
			GeneralReturnData<String> returnData= R.data();
			returnData.setStatus("0");
			returnData.setInfo("登陆失败,验证码错误");
			String returnJsonData=JsonUtils.writeValueAsString(returnData);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			//允许跨域
			((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().println(returnJsonData);
			
		}else{
			//带参数转发
	        Map<String,String> param=new HashMap<String,String>();
			param.put(Constants.LOGIN_FAILURE_KEY, Constants.CAPTCHA_FAILURE);
			WebUtils.issueRedirect(request, response, Constants.ERROR_PAGE_URL,param);
		}
		
        //停止链式处理 本步骤已经重定向
        return false;
	}

	public void setCaptchaEbabled(boolean captchaEbabled) {
		this.captchaEnable = captchaEbabled;
	}

	public void setCaptchaFailureKey(String captchaFailureKey) {
		this.captchaFailureKey = captchaFailureKey;
	}

	public void setRequestCaptchaParam(String requestCaptchaParam) {
		this.requestCaptchaParam = requestCaptchaParam;
	}
}
