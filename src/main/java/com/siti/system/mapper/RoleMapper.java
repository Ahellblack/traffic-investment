package com.siti.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import com.siti.system.db.Role;

import java.util.List;

public interface RoleMapper extends Mapper<Role>{

	@Select("select * from sys_role where name like concat(concat('%',#{name}),'%')")  
	public List<Role> findByNameLike(String name); 
	
	/**根据权限id获取角色信息
	 * */
	@Select("select * from sys_role where id in (select distinct role_id from sys_rlat_role_auth where auth_id =#{authId})")  
	public List<Role> getRolesByAuthId(Integer authId); 
	
	/**根据角色名查询是否存在角色信息
	 * */
	@Select("<script> select count(1) from sys_role where name =#{name} <if test='id!=null'> and id!=#{id} </if></script>")
	public Boolean findExistByName(@Param("name") String name, @Param("id") Integer id);
	
	/**根据角色编码查询是否存在角色信息
	 * */
	@Select("<script> select count(1) from sys_role where code =#{code} <if test='id!=null'> and id!=#{id} </if></script>")
	public Boolean findExistByCode(@Param("code") String code, @Param("id") Integer id);
	
	/**根据用户id查询角色信息
	 * */
	@Select("select * from sys_role where id in(select role_id from sys_rlat_user_role where user_id=#{userId})")
	public List<Role> getByUserId(@Param("userId") Integer userId);
	
	/**根据角色id查询角色信息
	 * */
	@Select("<script> select * from sys_role where id in <foreach collection ='list' item='roleId' index= 'index' open='(' separator=',' close=')'> #{roleId} </foreach></script>")
	public List<Role> findByIds(List<Integer> roleIds);
}
