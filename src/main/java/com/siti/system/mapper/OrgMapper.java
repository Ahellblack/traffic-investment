package com.siti.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import com.siti.system.db.Org;

import java.util.List;
import java.util.Set;

public interface OrgMapper extends Mapper<Org>{

	/**根据组织id查询是否是叶子节点
	 * */
	@Select("select count(1) from (select * from sys_org where pid = #{id} limit 1) a")
	public Boolean findLeaf(@Param("id") Integer id);
	
	/**根据用户id查询所属组织信息
	 * */
	@Select("select * from sys_org where id in(select org_id from sys_rlat_user_org where user_id=#{userId})")
	public Set<Org> getByUserId(@Param("userId") Integer userId);
	
	/**更新path
	 * */
	@Update("update sys_org set path=replace(path,#{updateId},#{updatePath})")
	public void updatePath(@Param("updateId") String updateId, @Param("updatePath") String updatePath);
	
	/**查询树
	 * */
	@Select("<script> select * from sys_org where 1=1 <if test='id!=null'> and (id=#{id} or FIND_IN_SET(#{id},path)) </if></script>")  
	public List<Org> findById(Integer id); 
	
	@Select("SELECT distinct name FROM sys_org where id in (SELECT org_id FROM sys_rlat_user_org where user_id=#{userId})")
	public List<String> getOrgNames(Integer userId);
	
	@Select("SELECT distinct code FROM sys_org where id in (SELECT org_id FROM sys_rlat_user_org where user_id=#{userId}) and type='department'")
	public List<String> getOrgCodesByUserId(Integer userId);
}
