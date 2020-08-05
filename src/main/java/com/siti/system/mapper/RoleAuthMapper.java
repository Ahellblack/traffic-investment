package com.siti.system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;
import com.siti.system.db.RoleAuth;

import java.util.List;

public interface RoleAuthMapper extends Mapper<RoleAuth>{

	/**根据角色ID删除角色权限关系
	 * */
	@Delete("delete from sys_rlat_role_auth where role_id =#{roleId}")  
	public void deleteByRoleId(int roleId); 
	
	/**根据权限ID删除角色权限关系
	 * */
	@Delete("delete from sys_rlat_role_auth where auth_id =#{authId}")  
	public void deleteByAuthId(int authId); 
	
	/**根据角色ID和权限ID删除角色权限关系
	 * */
	@Delete("<script> delete from sys_rlat_role_auth where role_id =#{roleId} and auth_id in <foreach collection ='list' item='authId' index= 'index' open='(' separator=',' close=')'> #{authId} </foreach> </script>")  
	public void deleteByRoleAndAuth(@Param("roleId") int roleId, @Param("authIds") List<Integer> authIds);
	
	/**根据角色ID获取关联权限ID
	 * */
	@Select("select auth_id from sys_rlat_role_auth where role_id =#{roleId}")
	public List<Integer> getAuthIdByRoleId(int roleId);
	
	/**根据当前角色ID获取该ID对应的角色信息和低等级角色
	 * @param roleId
	 * @return List roleIds
	 * */
	@SelectProvider(type = RoleAuthProvider.class, method = "getLowGradeRoleId")  
	public List<Integer> getLowGradeRoleId(@Param("roleId") int roleId);
}
