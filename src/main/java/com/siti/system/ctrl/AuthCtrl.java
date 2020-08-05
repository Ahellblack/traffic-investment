package com.siti.system.ctrl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siti.common.base.BaseCtrl;
import com.siti.common.base.FormValidFailureException;
import com.siti.system.biz.AuthBiz;
import com.siti.system.biz.RoleBiz;
import com.siti.system.db.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("auths")
public class AuthCtrl extends BaseCtrl<AuthBiz,Auth> {
	
	@Autowired
	private RoleBiz roleBiz;
	
	/**根据角色Id获取权限*/
	@GetMapping("roleId/{roleId}")
	public List<Auth> getByRole(@PathVariable int roleId){
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(roleId);
		return biz.getByRoleId(roleIds);
	}
	
	@GetMapping(params={"page","pageSize"})
	public PageInfo<Auth> findByPageAndParams(int page, int pageSize,Integer sort){	
		PageHelper.startPage(page, pageSize);  
	    List<Auth> list = biz.findBySort(sort);  	  
	    PageInfo<Auth> p=new PageInfo<Auth>(list);              
	    return p;  
	}
	
	@DeleteMapping
	public int delById(Auth auth,Errors error) throws FormValidFailureException{
		if(biz.findSid(auth.getId(),null)){
			error.rejectValue("id", "Auth.id.restrict", "该节点不是叶子节点");
			throw new FormValidFailureException(error);
		}
		return biz.delById(auth.getId(),auth.getPid(),auth.getSort());
	}
	
	/**添加权限信息
	 * @param pidSort 父节点的排序号
	 * @param insertSort 插入节点的排序号
	 * @param insertId 插入节点的id
	 * @param insertType 添加节点至插入节点之前（1）或之后（2）
	 * @throws FormValidFailureException 
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Auth saveAuth(Auth auth,Integer pidSort,Integer insertSort,Integer insertId,Integer insertType,Errors error) throws FormValidFailureException{
		if(pidSort!=null){
			if(biz.splitArr(pidSort)<1){
				error.rejectValue("pid", "Auth.pid.restrict", "不能为该权限添加子权限");
				throw new FormValidFailureException(error);
			}
		}
		return biz.saveAuth(auth, pidSort, insertSort, insertId, insertType,error);
	}
	
	/**修改权限信息
	 * @param oldPid 修改之前的父节点id
	 * @param pidSort 修改之后的父节点排序号
	 * @param insertSort 插入节点的排序号
	 * @param insertId 插入节点的id
	 * @param insertType 添加节点至插入节点之前（1）或之后（2）
	 * @throws FormValidFailureException 
	 */
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Auth updateAuth(Auth auth,Integer oldPid,Integer pidSort,Integer insertSort,Integer insertId,Integer insertType,Errors error) throws FormValidFailureException{
		if(biz.splitArr(auth.getSort())>=biz.splitArr(pidSort)){
			error.rejectValue("pid", "Auth.pid.restrict", "不能将此权限修改为该权限的子权限");
			throw new FormValidFailureException(error);
		}
		return biz.updateAuth(auth,oldPid, pidSort, insertSort, insertId, insertType,error);
	}
	
	@GetMapping("authsTree")
	public List<Auth> getAuthsTree(){
		return biz.getAuthsTree();
	}
	
	@GetMapping("sonAuths")
	public List<Auth> getAuthsByPid(Integer pid){
		return biz.getAuthsByPid(pid);
	}
	
	/**获取菜单
	 * @throws Exception 
	 * */
/*	@GetMapping("menu")
	public List<Auth> getMenu(){
//		throw new Exception("test exception");
		//#TODO 替换
		LoginUserInfo user = LoginCtrl.getLoginUserInfo();
		List<Role> roles = roleBiz.getByUserId(user.getId());
		List<Integer> roleIds = new ArrayList<Integer>();
		for(int i=0;i<roles.size();i++){
			roleIds.add(roles.get(i).getId());
		}
		return biz.getMenu(roleIds);
	}*/
}
