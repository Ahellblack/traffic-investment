package com.siti.system.mapper;

import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;
import com.siti.system.db.User;

import java.util.List;

public interface UserMapper extends Mapper<User>{

	/**查询用户信息
	 * */
	@Select("<script> select * from sys_user where 1=1 <if test='userName!=null'> and user_name=#{userName} </if><if test='realName!=null'> and real_name=#{realName} </if></script>")
	@Results(id = "progItemMap",value = {
		@Result(property = "id",column = "id"),
		@Result(property = "userName",column = "user_name"),
		@Result(property = "realName",column = "real_name"),
		@Result(property = "updateBy",column = "update_by"),
		@Result(property = "updateTime",column = "update_time"),
		@Result(property = "roles",javaType = List.class,column = "id",many = @Many(select="com.siti.system.mapper.RoleMapper.getByUserId")),
		@Result(property = "orgs",javaType = List.class,column = "id",many = @Many(select="com.siti.system.mapper.OrgMapper.getByUserId"))
	})
	public List<User> getByName(@Param("userName") String userName, @Param("realName") String realName);
	
	@Select("select * from sys_user where id in (select user_id from sys_rlat_user_role where role_id = (select id from sys_role where code =#{code}))")  
	public List<User> getByRoleCode(String code);
	
	/**根据用户名查询是否存在用户信息
	 * */
	@Select("<script> select count(id) from sys_user where user_name=#{userName} <if test='id!=null'> and id!=#{id} </if></script>")
	public Boolean isExistName(@Param("userName") String userName, @Param("id") Integer id);
	
	/**获取组织下的用户*/
	@Select("select * from sys_user where id in (select user_id from sys_user_org_rela where org_id =#{orgId})")
	public List<User> getUserByOrg(Integer orgId);
	
    /*根据用户名查询用户信息*/
    @Select("select * from sys_user where user_name=#{userName}")
    @Results(id = "progItem", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.siti.system.mapper.RoleMapper.getByUserId")),
            @Result(property = "orgs", javaType = List.class, column = "id", many = @Many(select = "com.siti.system.mapper.OrgMapper.getByUserId"))
    })
    User findUserByUserName(@Param("userName") String userName);

	/*根据用户名查询用户信息*/
	@Select("<script>" +
			"SELECT real_name FROM `sys_user` su left join `sys_rlat_user_role` srur on su.id = srur.user_id " +
			"left join sys_role sr on srur.role_id  = sr.id where 1=1 " +
			"<if test = \' roleCode !=null \'> and sr.code = #{roleCode} </if>" +
			"<if test = \' userName !=null \'> and su.real_name = #{userName}</if> " +
			"</script>")
	List<String> findUserame(@Param("userName") String userName, @Param("roleCode") String roleCode);
	
	/**根据组织和用户类型获取用户信息*/
	@Select("select * from sys_user where id in (select user_id from sys_rlat_user_org where org_id in"
			+ "(SELECT id FROM sys_org where name = #{orgName})) and type = #{type}")
	public List<User> getUserByOrgNameAndType(@Param("orgName") String orgName, @Param("type") String type);

	/**根据组织获取用户信息*/
	@Select("<script>select * from sys_user where id in \n" +
			"(select user_id from sys_rlat_user_org where org_id in \n" +
			"\t(select org_id from sys_org where 1" +
			"<if test=\"orgName !=null and orgName!=''\"> and name like '%${orgName}%'</if>" +
			"))</script>")
	List<User> getUserNameByOrgName(@Param("orgName") String orgName);
}
