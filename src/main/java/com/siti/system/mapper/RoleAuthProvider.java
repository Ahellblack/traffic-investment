package com.siti.system.mapper;

import java.util.Map;

public class RoleAuthProvider {

	@SuppressWarnings("rawtypes")
	public String getLowGradeRoleId(Map map) {  
		StringBuffer buffer = new StringBuffer("select t1.role_id from ")
			//统计每个角色所拥有的权限在roleId所拥有的权限范围内的权限个数
			.append("(SELECT r1.role_id,count(auth_id) c FROM sys_rlat_role_auth r1 where auth_id in (select auth_id from sys_rlat_role_auth where role_id =#{roleId} ) group by role_id) t1")
			.append(" INNER JOIN ")
			//统计每个角色所拥有的权限个数
			.append("(select r2.role_id,count(auth_id) c from sys_rlat_role_auth r2 group by role_id) t2")
			//返回两个权限统计数相同的记录
			.append(" on t1.role_id = t2.role_id and t1.c=t2.c");
	    return buffer.toString();  
	} 
}
