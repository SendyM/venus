package com.max.venus.common.web.filter;

import com.max.venus.common.config.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 本系统大量采用ajax请求
 * 由于ajax的特殊性在session超时的情况下
 * 请求得到的数据会出问题 
 * 此时应该友好的提示并且转跳到指定的错误处理页面
 * 但是ajax无法自动转调 需要前台js配合处理 
 * 同时需要在后台判断当session不可用时设置http code方便前台判断问题的原因来进行处理
 * 
 * @author Sendy
 * 
 */
public class AjaxSessionTimeoutFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 判断session里是否为空 如果没有session 不创建新的session
		if (req.getSession(false) == null) {
			// 如果是ajax请求响应头会有，x-requested-with；
			if (req.getHeader("x-requested-with") != null
					&& req.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest")) {
				//如果是ajax访问 设置响应的http status状态码为408
				// 表示session timeout
				res.setStatus(Constants.AJAX_SESSION_TIMEOUT);
			} else {
				chain.doFilter(req, res);
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig chain) throws ServletException {

	}
}