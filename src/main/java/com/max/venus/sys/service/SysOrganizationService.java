package com.max.venus.sys.service;

import com.max.venus.common.entity.SysOrganization;
import com.max.venus.common.service.BaseService;



public interface SysOrganizationService extends BaseService<SysOrganization> {

	/**
	 * 删除自己和子组织机构
	 * @param orgId
	 */
	void deleteSelfAndSubSysOrganization(SysOrganization sysOrganization);
	
}
