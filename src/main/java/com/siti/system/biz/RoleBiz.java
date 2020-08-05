package com.siti.system.biz;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siti.common.base.BaseBiz;
import com.siti.system.db.Role;
import com.siti.system.db.RoleAuth;
import com.siti.system.mapper.RoleAuthMapper;
import com.siti.system.mapper.RoleMapper;
import com.siti.system.mapper.UserRoleMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class RoleBiz extends BaseBiz<RoleMapper,Role>{

	@Autowired
	private RoleAuthMapper roleAuthDao;
	@Autowired
	private UserRoleMapper userRoleDao;
	
	public List<Role> findByNameLike(String name){
		return dao.findByNameLike(name);
	}
	
	/**保存角色,并同时保存角色与权限关系
	 * @param authIds 可为null
	 * */
	public Role save(Role role, Integer[] authoIds){
		dao.insert(role);
		Integer roleId = role.getId();
		if(authoIds != null){
			for(int i=0;i<authoIds.length;i++){
				RoleAuth roleAuth = new RoleAuth();
				roleAuth.setRoleId(roleId);
				roleAuth.setAuthId(authoIds[i]);
				roleAuthDao.insert(roleAuth);
			}
		}
		return role;
	}

	/**更新角色
	 * */
	public void update(Role role, Integer[] authoIds){
		List<Integer> authIdList = new ArrayList<Integer>(authoIds.length); 
		Collections.addAll(authIdList, authoIds);
		int roleId = role.getId();
		List<Integer> oldAuthIds = roleAuthDao.getAuthIdByRoleId(roleId);
		List<Integer> clone = new ArrayList<Integer>(oldAuthIds);
		oldAuthIds.removeAll(authIdList);
		if(oldAuthIds.size() > 0)
			roleAuthDao.deleteByRoleAndAuth(roleId, oldAuthIds);
		
		authIdList.removeAll(clone);
		if(authIdList.size() > 0){
			for(int i=0;i<authIdList.size();i++){
				RoleAuth roleAuth = new RoleAuth();
				roleAuth.setRoleId(roleId);
				roleAuth.setAuthId(authIdList.get(i));
				roleAuthDao.insert(roleAuth);
			}
		}
		this.dao.updateByPrimaryKey(role);
	}
	
	public void deleteById(Integer id) {
		roleAuthDao.deleteByRoleId(id);
		userRoleDao.deleteByRoleId(id);
		super.deleteById(id);
	}
	
	/**根据当前角色ID获取该ID对应的角色信息和低等级角色
	 * @param roleId 角色ID
	 * @return 返回角色列表，其中包括roleId对应的角色，和等级（权限）低于该角色的所有角色。
	 * */
	public List<Role> getLowGradeRole(int roleId){
		List<Integer> roleIds = roleAuthDao.getLowGradeRoleId(roleId);
		return dao.findByIds(roleIds);
	}
	
	public List<Role> getRolesByAuthId(Integer authId){
		return dao.getRolesByAuthId(authId);
	}
	
	/**获取所有角色id和name
	 * */
	public List<Role> getAll(){
		return dao.selectAll();
	}
	
	public List<Role> getByUserId(@Param("userId")Integer userId){
		return dao.getByUserId(userId);
	}
	
}