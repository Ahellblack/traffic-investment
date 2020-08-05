package com.siti.system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import com.siti.system.db.UserRole;

import java.util.List;

public interface UserRoleMapper extends Mapper<UserRole>{

	/**根据用户id获取相关的角色id*/
	@Select("select role_id from sys_rlat_user_role where user_id =#{userId}")  
	public List<Integer> getRoleIdByUserId(int userId); 
	
	/**判断用户是否拥有某角色
	 * @param code 角色编码
	 * @return true:拥有 false:没有
	 * */
	@Select("select count(1) from sys_rlat_user_role where user_id =#{userId} and role_id = (select id from sys_role where code =#{code})")
	public boolean isHasRole(int userId, String code);
	
	/**根据用户id删除用户角色关系
	 * */
	@Delete("delete from sys_rlat_user_role where user_id =#{userId}")  
	public void deleteByUserId(int userId); 
	
	/**根据角色ID删除用户角色关系
	 * */
	@Delete("delete from sys_rlat_user_role where role_id =#{roleId}")  
	public void deleteByRoleId(int roleId); 
	
	@Delete("<script> delete from sys_rlat_user_role where user_id =#{userId} and role_id in <foreach collection ='roleIds' item='roleId' index= 'index' open='(' separator=',' close=')'> #{roleId} </foreach> </script>")  
	public void deleteByUserAndRole(@Param("roleIds") List<Integer> roleIds, @Param("userId") int userId);
	
	@Delete("delete from sys_rlat_user_role where user_id =#{userId} and role_id in (select id from sys_role where code =#{roleCode})")  
	public void deleteByUserAndCode(int userId, String roleCode); 
}
