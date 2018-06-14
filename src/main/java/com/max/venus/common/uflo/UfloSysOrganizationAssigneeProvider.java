package com.max.venus.common.uflo;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.assign.AssigneeProvider;
import com.bstek.uflo.process.assign.Entity;
import com.bstek.uflo.process.assign.PageQuery;
import com.max.venus.common.entity.SysOrganization;
import com.max.venus.common.entity.SysUser;
import com.max.venus.sys.service.SysOrganizationService;
import com.max.venus.sys.service.SysUserService;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 与系统的组织机构相关联
 * @author Sendy
 * @since 2017年8月23日
 */
@Component
public class UfloSysOrganizationAssigneeProvider implements AssigneeProvider {
	/**
	 * 默认启用
	 */
	private boolean disabledSysOrganizationProvider=false;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysOrganizationService sysOrganizationService;

	/**
	 * 树形结构
	 * @return boolean
	 */
	@Override
	public boolean isTree() {
		return true;
	}

	@Override
	public String getName() {
		return "组织机构";
	}

	@Override
	public void queryEntities(PageQuery<Entity> pageQuery, String parentId) {
		if(parentId!=null && parentId.equals("null")){
			parentId=null;
		}
		if(parentId==null){
			parentId="0";
		}
		List<Entity> list=new ArrayList<Entity>();		
		Example example=new Example(SysOrganization.class);
		example.createCriteria().andEqualTo("parentId", parentId);
		example.createCriteria().andEqualTo("isValid", "1");
		//找出所有下一级
    	List<SysOrganization> sysOrganizations= sysOrganizationService.selectByExample(example);
    	if(sysOrganizations!=null&&sysOrganizations.size()!=0){
    		
    		for(SysOrganization sysOrganization:sysOrganizations){
    			Entity entity=new Entity(sysOrganization.getOrgId().toString(),sysOrganization.getOrgName());
    			//如果是叶子结点是可以选择的 非叶子结点不能选择
    			Example example1=new Example(SysOrganization.class);
    			example1.createCriteria().andEqualTo("parentId", sysOrganization.getOrgId());
    			example1.createCriteria().andEqualTo("isValid", "1");
    			Integer selectCountByExample = sysOrganizationService.selectCountByExample(example1);
    			if(selectCountByExample==0){
    				//是叶子节点
    				entity.setChosen(true);
    			}else{
    				//非叶子节点只能展开
    				entity.setChosen(false);
    			}
    			list.add(entity);
    		}
    		pageQuery.setResult(list);
    		pageQuery.setTree(true);
    	}else{
    		return;
    	}

		
	}

	/**
	 * 根据	entityId招对应的用户ids
	 */
	@Override
	public Collection<String> getUsers(String entityId, Context context, ProcessInstance processInstance) {
		Example example =new Example(SysUser.class);
		example.createCriteria().andEqualTo("isValid", "1");
		example.createCriteria().andEqualTo("sysOrganizationId", entityId);
		List<SysUser> findList=sysUserService.selectByExample(example);
		Collection<String> userids=new ArrayList<String>();
		for(SysUser user:findList){
			userids.add(user.getUserId());
		}
		return userids;
	}

	@Override
	public boolean disable() {
		return disabledSysOrganizationProvider;
	}

	public boolean isDisabledSysOrganizationProvider() {
		return disabledSysOrganizationProvider;
	}

	public void setDisabledSysOrganizationProvider(boolean disabledSysOrganizationProvider) {
		this.disabledSysOrganizationProvider = disabledSysOrganizationProvider;
	}

	
}
