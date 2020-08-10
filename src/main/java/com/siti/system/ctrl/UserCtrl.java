package com.siti.system.ctrl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.siti.common.base.BaseCtrl;
import com.siti.common.base.FormValidFailureException;
import com.siti.common.base.vo.ValidVo;
import com.siti.system.biz.UserBiz;
import com.siti.system.db.User;
import com.siti.system.db.UserOrg;
import com.siti.system.mapper.UserOrgMapper;
import com.siti.system.valid.UserValid;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserCtrl extends BaseCtrl<UserBiz,User> {
	
	@Autowired
	private UserValid userValid;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.setValidator(userValid);
	}
	
	@Autowired
	private UserOrgMapper userOrgDao;
	
	/**保存用户
	 * 后台设置默认密码
	 * @throws FormValidFailureException 
	 **/ 
	@PostMapping
	public User saveUser(@Validated User user, Errors result, Integer[] roleIds, Integer[] orgIds) throws FormValidFailureException {
		if(result.hasErrors()){  
			throw new FormValidFailureException(result);
        } 
		user.setUpdateBy(1);
		user.setUpdateTime(new Date());
		user.setPassword("");
		if(user.getUserName().equals("")){
			Date date = new Date();
			long times = date.getTime();
			String timeStr  =  String.valueOf(times);
			user.setUserName(timeStr);
		}
		biz.save(user, roleIds, orgIds);
		return user;
	}

	/**更新用户，但不更新用户密码
	 * @throws FormValidFailureException 
	 **/ 
	@PutMapping
	public User updateUser(@Validated User user, Integer[] roleIds, Errors result, Integer[] orgIds) throws FormValidFailureException {
		if(result.hasErrors()){  
			throw new FormValidFailureException(result);
        } 
		user.setUpdateBy(1);
		user.setUpdateTime(new Date());
		userOrgDao.deleteByUserId(user.getId());
		//设置User所属组织
		for(int i=0; i<orgIds.length; i++){
			UserOrg userOrg = new UserOrg();
			userOrg.setUserId(user.getId());
			userOrg.setOrgId(orgIds[i]);
			userOrg.setUpdateTime(new Date());
			userOrgDao.insert(userOrg);
		}
		List<Integer> role = Arrays.asList(roleIds);
        List<Integer> roles = new ArrayList<Integer>(role);
		biz.update(user, roles);
		return user;
	}
	
	/**密码验证
	 **/
	@GetMapping("checkPwd")
	public ValidVo checkPwd(Integer userId, String oldPassword){
		//oldPassword = this.getEncryptPassword(oldPassword);
		return biz.checkPwd(userId, oldPassword);
	}
	
	/**修改密码
	 * @return success：修改成功
	 **/
	@PutMapping("pwd")
	public String modifyPwd(Integer id, String password){
		int updateBy = 1;
		biz.modifyPwd(id, password, updateBy);
		return "success";
	} 
	
	/**根据搜索条件分页查询数据。
	 * @param page 偏移量，即记录索引位置
	 * @param pageSize 每页记录数
	 **/ 
	@GetMapping(params={"page","pageSize"})
	public PageInfo<User> findByPageAndParams(int page, int pageSize, String userName, String realName, Integer orgId) {
		PageHelper.startPage(page, pageSize);  
	    List<User> list = biz.getByName(userName, realName);
	    PageInfo<User> p=new PageInfo<User>(list);              
	    return p; 
	}
	
	/**获取组织下的用户*/
	@GetMapping("orgId/{orgId}")
	public List<User> getUserByOrg(Integer orgId){
		return biz.getUserByOrg(orgId);
	}
	
	/**获取加密密码
	private String getEncryptPassword(String password){
		return new SimpleHash("SHA-1",password).toString();
	}*/
	
	/**验证用户名是否重复*/
	@GetMapping("nameIsExist")
	public ValidVo nameIsExist(Integer id,String userName){
		return new ValidVo(biz.nameIsExist(id, userName));
	}
	
	@DeleteMapping("id/{id}")
	public int deleteById(@PathVariable Integer id) {
		biz.deleteById(id);
		return 1;
	}

	@DeleteMapping("passwordForget")
	public int passwordForget(@PathVariable Integer id) {
		biz.deleteById(id);
		return 1;
	}

	
}
