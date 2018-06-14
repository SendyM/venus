package com.max.venus.sys.service;

import java.util.Set;

import com.max.venus.common.entity.SysUser;
import com.max.venus.common.service.BaseService;



public interface SysUserService extends BaseService<SysUser> {
	// 根据用户名查找权限
	public Set<String> findPermissionsByUserName(String userName);
	// 根据用户名查找角色
	public Set<String> findRolesByUserName(String userName);
}
