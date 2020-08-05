package com.siti.system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import com.siti.system.db.UserOrg;

public interface UserOrgMapper extends Mapper<UserOrg>{

	/**根据权限ID删除角色权限关系
	 * */
	@Delete("delete from sys_rlat_user_org where user_id =#{userId}")  
	public void deleteByUserId(int userId); 
	
	/**根据组织id删除用户组织关系
	 * */
	@Delete("delete from sys_rlat_user_org where org_id =#{orgId}")  
	public void deleteByOrgId(int orgId); 
	
	/**根据组织id查询是否存在用户组织绑定关系
	 * */
	@Select("select count(1) from (select * from sys_rlat_user_org where org_id=#{orgId} limit 1) a")
	public Boolean findExistById(Integer orgId);
}
