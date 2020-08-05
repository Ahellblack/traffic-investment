package com.siti.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.common.Mapper;
import com.siti.system.db.Auth;

import java.util.List;

public interface AuthMapper extends Mapper<Auth>{

	@Select("<script> select * from sys_auth where 1=1 <if test='sort!=null'> and sort=#{sort} </if> order by sort asc </script>")  
	public List<Auth> findBySort(@Param("sort") Integer sort);
	 
	/**根据角色ID获取菜单信息
	 * */
	@Select("<script> select * from sys_auth where id in (select distinct auth_id from sys_rlat_role_auth where role_id in <foreach collection ='list' item='roleId' index= 'index' open='(' separator=',' close=')'> #{roleId} </foreach>) and type = 1 order by sort </script>")  
	public List<Auth> getMenuByRoleId(List<Integer> roleIds); 
	
	/**根据角色ID获取授权信息
	 * */
	@Select("<script> select * from sys_auth where id in (select distinct auth_id from sys_rlat_role_auth where role_id in <foreach collection ='list' item='roleId' index= 'index' open='(' separator=',' close=')'> #{roleId} </foreach>) and type != 1 </script>")  
	public List<Auth> getPermissionByRoleId(List<Integer> roleIds); 
	
	/**根据角色ID获取权限信息
	 * */
	@Select("<script> select * from sys_auth where id in (select distinct auth_id from sys_rlat_role_auth where role_id in <foreach collection ='list' item='roleId' index= 'index' open='(' separator=',' close=')'> #{roleId} </foreach>) </script>")  
	public List<Auth> getByRoleId(List<Integer> roleIds); 
	
	/**根据用户ID获取权限信息
	 * */
	@Select("select * from sys_auth where id in (select distinct auth_id from sys_rlat_role_auth where role_id in(select distinct role_id from sys_rlat_user_role where user_id = #{userId}))")  
	public List<Auth> getByUserId(Integer userId); 
	
	/**获取菜单
	 * */
	@Select("<script> select * from sys_auth where id in (select distinct auth_id from sys_rlat_role_auth where role_id in <foreach collection ='list' item='roleId' index= 'index' open='(' separator=',' close=')'> #{roleId} </foreach>) and type = 1 order by sort asc </script>")  
	public List<Auth> getMenu(List<Integer> roleIds); 
	
	/**判断某节点是否存在子节点
	 * */
	@Select("<script> select count(1) from (select * from sys_auth where 1=1 <when test='id==null'> <when test='pid==null'> and pid is null </when> <otherwise> and pid =#{pid} </otherwise> </when><otherwise> <when test='pid==null'> and pid is null and id != #{id} </when> <otherwise> and pid =#{pid} and id != #{id} </otherwise></otherwise> limit 1) a </script>")
	public Boolean isExistSon(@Param("pid") Integer pid, @Param("id") Integer id);
	
	
	
	/**查询权限树
	 * */
	@Select("select * from sys_auth order by sort asc")
	public List<Auth> getAuthsTree();
	
	/**查询子权限
	 * */
	@Select("select * from sys_auth where pid = #{pid} order by sort asc")
	public List<Auth> getAuthsByPid(Integer pid);
		
	/*----------------------以下均为更新排序号所需接口------------------------------------*/
	
	//所有受影响的节点排序号加或减
	@UpdateProvider(type = AuthProvider.class, method = "updatePlusOrMinus")
	public void updatePlusOrMinus(@Param("sortDigit") Integer sortDigit, @Param("plusSort") Integer plusSort, @Param("sortLike") String sortLike, @Param("type") String type);
	
	//查询需要更新的同级节点序号
	@SelectProvider(type = AuthProvider.class, method = "updateSortList")
	public List<Short> updateSortList(@Param("pid") Integer pid, @Param("sort") Integer sort, @Param("type") String type, @Param("insertType") Integer insertType, @Param("insertSort") Integer insertSort);
	
	//更新改变节点以及其子节点的序号
	@UpdateProvider(type = AuthProvider.class, method = "updateSonSort")
	public void updateSonSort(@Param("sortDigit") Integer sortDigit, @Param("oldSort") String oldSort, @Param("sort") String sort, @Param("updateSort") Integer updateSort, @Param("oldPid") Integer oldPid);

	//分两步修改子节点的排序号----第一步 将子节点的排序号修改，给修改时更新的节点的子节点挪位置（非最终修改）
	@UpdateProvider(type = AuthProvider.class, method = "updateSonSortOne")
	public void updateSonSortOne(@Param("sortDigit") Integer sortDigit, @Param("oldSort") String oldSort, @Param("updateSort") Integer updateSort, @Param("oldPid") Integer oldPid);
	
	//分两步修改子节点的排序号----第二步 最终修改子节点的排序号
	@UpdateProvider(type = AuthProvider.class, method = "updateSonSortTwo")
	public void updateSonSortTwo(@Param("sort") String sort);

}
