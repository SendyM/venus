package com.max.venus.common.uflo;

import com.bstek.uflo.env.EnvironmentProvider;
import com.max.venus.common.entity.SysUser;
import com.max.venus.common.security.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;

/**
 * SessionFactory及TransactionManager都是通过Spring环境注入；
 * 
 * 
 * @author Sendy
 *
 */
@Component
public class UfloEnvironmentProvider implements EnvironmentProvider {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	@Resource(name="transactionManager")
    private PlatformTransactionManager platformTransactionManager;
 
    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    @Override
    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }
 
    public void setPlatformTransactionManager(
            PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }
    /**getCategoryId方法返回null表示不对流程进行分类处理。只有该值为null 流程设计器里才也可以为空  该值主要用于saas多租户或者独立部署流程引擎时使用
     */
    @Override
    public String getCategoryId() {
        return null;
    }
    /** getLoginUser方法用于返回当前登录用户的用户id 不是用户名！！！
     */
    @Override
    public String getLoginUser() {
    	//返回当前系统的登录用户
    	SysUser sysUser = ShiroUtils.getSysUser();
    	String userId=null;
    	if(sysUser!=null){
    		userId=sysUser.getUserId();
    	}
    	if(StringUtils.isBlank(userId)){
    		return "anonymous";
    	}else{
    		return userId;
    	}
    }

}
