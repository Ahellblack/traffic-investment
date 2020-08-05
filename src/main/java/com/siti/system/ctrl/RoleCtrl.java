package com.siti.system.ctrl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.siti.common.base.BaseCtrl;
import com.siti.common.base.FormValidFailureException;
import com.siti.system.biz.RoleBiz;
import com.siti.system.db.Role;
import com.siti.system.mapper.UserRoleMapper;
import com.siti.system.valid.RoleValid;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("roles")
public class RoleCtrl extends BaseCtrl<RoleBiz,Role>{
	
	@Autowired
	private RoleValid roleValid;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.setValidator(roleValid);
	}
	
	@Autowired
	private UserRoleMapper userRoleBiz;
	
	//分页查询
	@GetMapping(params={"page","pageSize"})
	public PageInfo<Role> findByPageAndParams(int page, int pageSize,String name){	
		PageHelper.startPage(page, pageSize);  
	    List<Role> list = biz.findByNameLike(name);
	    PageInfo<Role> p=new PageInfo<Role>(list);              
	    return p;  
	}
	
	/**添加角色
	 * @param authIds 角色可不设置权限，所以该值可为null
	 * @throws FormValidFailureException 
	 * */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Role saveRole(@Validated Role role, Errors result, Integer[] authIds) throws FormValidFailureException{
		if(result.hasErrors()){  
			throw new FormValidFailureException(result);
        }  
		role.setUpdateBy(1);
		return biz.save(role, authIds);
	}
	
	/**修改角色
	 * @param authIds 角色可不设置权限，所以该值可为null
	 * @throws FormValidFailureException 
	 * */
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Role updateRole(@Validated Role role, Errors result, Integer[] authIds) throws FormValidFailureException{
		if(result.hasErrors()){  
			throw new FormValidFailureException(result);
        }  
		role.setUpdateBy(1);
		biz.update(role, authIds);
		return role;
	}
	
	/**根据当前用户角色获取低等级角色(返回的角色信息里包括其用户角色自身)*/
	@GetMapping("lowGradeRole")
	public Set<Role> getLowGradeRole(){
		List<Integer> roleIds = userRoleBiz.getRoleIdByUserId(1);
		Set<Role> roles = new HashSet<Role>();
		//获取所有权限低于当前用户的角色
		for(int i=0; i<roleIds.size(); i++){
			List<Role> roleList = biz.getLowGradeRole(roleIds.get(i));
			roles.addAll(roleList);
		}
		return roles;
	}
	
	@GetMapping("rolesByAuthId")
	public List<Role> getRolesByAuthId(Integer authId){
		return biz.getRolesByAuthId(authId);
	}
	
	/**获取所有角色id和name
	 * */
	@GetMapping("idAndName")
	public List<Role> findIdAndName(){
		return biz.getAll();
	}
	
}