package com.max.venus.common.security.shiro;

import com.max.venus.common.security.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 自定义 apache shiro session监听器
 * 
 * @author Sendy
 * 
 */
public class ShiroSessionListener extends SessionListenerAdapter {

	@Autowired
	private ShiroSessionRepository shiroSessionRepository;

	private static final Logger logger = LoggerFactory.getLogger(ShiroSessionListener.class);

	/**
	 * 当过期时
	 * 
	 * @param session
	 *            the session that has expired.
	 */
	@Override
	public void onExpiration(Session session) {
		if (logger.isDebugEnabled()) {
			Date close = session.getLastAccessTime();
			Date start = session.getStartTimestamp();
			long between = close.getTime() - start.getTime();
			// 单位秒
			long betweens = between / 1000;
			logger.debug("session销毁--------sessionID：" + session.getId() + "----session持续时间：" + betweens + "s");
		}

		// session过期进行清理
		shiroSessionRepository.deleteSession(session.getId());
	}

	/**
	 * 开始一个session时
	 * 
	 * @param session
	 *            the session that has started.
	 */
	@Override
	public void onStart(Session session) {
		if (logger.isInfoEnabled()) {
			logger.info("session开始--------sessionID：" + session.getId() + "----sessionHost：" + session.getHost()
					+ "----session超时时间" + session.getTimeout());
		}
	}

	/**
	 * 
	 * 
	 * @param session
	 *            the session that has stopped.
	 */
	@Override
	public void onStop(Session session) {
		if (logger.isDebugEnabled()) {
			Date close = session.getLastAccessTime();
			Date start = session.getStartTimestamp();
			long between = close.getTime() - start.getTime();
			// 单位秒
			long betweens = between / 1000;
			logger.debug("session结束--------sessionID：" + session.getId() + "----session持续时间：" + betweens + "s");
		}
	}

	public ShiroSessionRepository getShiroSessionRepository() {
		return shiroSessionRepository;
	}

	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}
}
