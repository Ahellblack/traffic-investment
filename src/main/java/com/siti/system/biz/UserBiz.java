package com.siti.system.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siti.common.base.BaseBiz;
import com.siti.common.base.vo.ValidVo;
import com.siti.system.db.Role;
import com.siti.system.db.User;
import com.siti.system.db.UserOrg;
import com.siti.system.db.UserRole;
import com.siti.system.mapper.RoleMapper;
import com.siti.system.mapper.UserMapper;
import com.siti.system.mapper.UserOrgMapper;
import com.siti.system.mapper.UserRoleMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserBiz extends BaseBiz<UserMapper,User> {
	
	@Autowired
	private RoleMapper roleDao;
	@Autowired
	private UserRoleMapper userRoleRelaDao;
	@Autowired
	private UserOrgMapper userOrgDao;	
	
	@SuppressWarnings("unchecked")
	public User findByUserName(String userName){
		User user = new User();
		user.setUserName(userName);
		List<User> list = dao.select(user);
		if(list.size() == 1)
			return list.get(0);
		else
			return null;
	}
	
	/**用户登陆检测
	 * @return 查询不到返回null。
	 * */
	@SuppressWarnings("unchecked")
	public User loginCheck(String userName, String password){
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		List<User> users = dao.select(user);
		if(users.size() != 1)
			return null;
		else
			return users.get(0);
	}
	
	/**保存用户，同时保存用户与角色关联信息
	 * */
	public void save(User user,Integer[] roleIds,Integer[] orgIds) {
		dao.insert(user);
		//设置User所属组织
	    for(int i=0; i<orgIds.length; i++){
			UserOrg userOrg = new UserOrg();
			userOrg.setUserId(user.getId());
			userOrg.setOrgId(orgIds[i]);
			userOrg.setUpdateTime(new Date());
			userOrgDao.insert(userOrg);
		}
		for(int i=0;i<roleIds.length;i++){
			UserRole userRole = new UserRole();
			userRole.setUserId(user.getId());
			userRole.setRoleId(roleIds[i]);
			userRole.setUpdateTime(new Date());
			userRoleRelaDao.insert(userRole);
		}
	}
	
	/**更新用户，但不更新用户密码。
	 * 更新用户的角色信息
	 * */
	public void update(User user,List<Integer> newRoleIds) {
		List<Integer> oldRoleIds = userRoleRelaDao.getRoleIdByUserId(user.getId());
		List<Integer> clone = new ArrayList<Integer>(oldRoleIds);
		//要删除的角色关系
		oldRoleIds.removeAll(newRoleIds);
		if(oldRoleIds.size() > 0)
			userRoleRelaDao.deleteByUserAndRole(oldRoleIds,user.getId());
		//要添加的角色关系
		newRoleIds.removeAll(clone);
		if(newRoleIds.size() > 0){
			for(int i=0;i<newRoleIds.size();i++){
				UserRole userRole = new UserRole();
				userRole.setUserId(user.getId());
				userRole.setRoleId(newRoleIds.get(i));
				userRoleRelaDao.insert(userRole);
			}
		}		
		dao.updateByPrimaryKeySelective(user);
	}
	
	/**密码验证
	 * @return 验证通过返回true,失败返回false
	 * */
	public ValidVo checkPwd(Integer userId, String oldPassword){
		User user = new User();
		user.setId(userId);
		List<User> users = dao.select(user);
		String password = users.get(0).getPassword();
		return new ValidVo(password.equals(oldPassword));
	}
	
	/**修改密码
	 * */
	public void modifyPwd(Integer userId, String password, Integer updateBy){
		User user = new User();
		user.setId(userId);
		user.setPassword(password);
		user.setUpdateBy(updateBy);
		dao.updateByPrimaryKeySelective(user);
	}
	
	/**先删除用户与角色关联信息，然后再删用户*/
	@Transactional
	public void deleteById(Integer id) {
		userRoleRelaDao.deleteByUserId(id);
		userOrgDao.deleteByUserId(id);
		super.deleteById(id);
	}

	/**根据搜索条件分页查询数据
	 * */
	public List<User> getByName(String userName, String realName){
		return dao.getByName(userName, realName);
	}
	
	/**获取组织下的用户*/
	public List<User> getUserByOrg(Integer orgId){
		return dao.getUserByOrg(orgId);
	}
	
	/**根据用户id获取相关的角色id*/
	public List<Integer> getRoleId(int userId){
		return userRoleRelaDao.getRoleIdByUserId(userId);
	}
	
	/**根据用户名获取用户信息*/
	public User findByName(String userName){
		User user = new User();
		user.setUserName(userName);
		return dao.selectOne(user);
	}
	
	/**给用户添加角色
	 * @return boolean 返回false，标识无法根据此roleCode找到对应角色。true表示添加成功。
	 * */
	public boolean addRoleRela(Integer userId, String roleCode){
		Role role = new Role();
		role.setCode(roleCode);
		Integer roleId = roleDao.selectOne(role).getId();
		if(roleId == null)
			return false;
		List<Integer> roleIds = userRoleRelaDao.getRoleIdByUserId(userId);
		//判断用户是否已经拥有该角色
		if(!roleIds.contains(roleId)){
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);
			userRoleRelaDao.insert(userRole);
		}			
		return true;
	}

	/**删除用户的某个角色
	 * @return boolean 返回false，标识无法根据此roleCode找到对应角色。true表示删除成功。
	 * */
	public boolean delRoleRela(Integer userId, String roleCode){
		userRoleRelaDao.deleteByUserAndCode(userId, roleCode);
		return true;
	}
	
	/**根据角色编码获取用户*/
	public List<User> getByRoleCode(String code){
		return dao.getByRoleCode(code);
	}
	
	public boolean nameIsExist(Integer id,String userName){
		return dao.isExistName(userName, id);			
	}
}
