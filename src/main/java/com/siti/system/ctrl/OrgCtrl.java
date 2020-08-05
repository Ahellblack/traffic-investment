package com.siti.system.ctrl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import com.siti.common.base.BaseCtrl;
import com.siti.common.base.FormValidFailureException;
import com.siti.system.biz.OrgBiz;
import com.siti.system.db.Org;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("orgs")
public class OrgCtrl extends BaseCtrl<OrgBiz,Org>{
	
	//分页查询
	@GetMapping(params={"page","pageSize"})
	public PageInfo<Org> findByPageAndParams(int page, int pageSize,Integer id){
		PageHelper.startPage(page, pageSize);  
	    List<Org> list = biz.findById(id);
	    PageInfo<Org> p=new PageInfo<Org>(list);              
	    return p;  
	}
	
	//查询整棵树
	@GetMapping("getTree")
	public List<Org> getTree(){
		return biz.getAll();
	}

	/**保存组织
	 **/ 
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Org saveOrg(Org org,String ppath) {
		if(org.getPid() != null){
			if(ppath==null){
				org.setPath(org.getPid().toString());
			}else{
				org.setPath(ppath+","+org.getPid());
			}
		}
		org.setUpdateTime(new Date());
		biz.save(org);
		return org;
	}
	
	/**修改组织
	 * @throws FormValidFailureException 
	 **/ 
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Org updateOrg(Org org, Errors error, String ppath) throws FormValidFailureException {
		if(ppath != null && !ppath.equals("")){
			String replacePath = ","+org.getId()+",";		
			String updatePath = ","+ppath+",";	
			if(updatePath.contains(replacePath)){
				error.rejectValue("id", "Org.id.restrict", "不能将该组织修改为子组织的子组织");
				throw new FormValidFailureException(error);
			}
		}
		return biz.updateOrg(org, ppath);
	}
	
	//删除组织
	@DeleteMapping("id/{id}")
	public int delById(@PathVariable Integer id, Org org, Errors error) throws FormValidFailureException{
		if(biz.findLeaf(id)){
			error.rejectValue("id", "Org.id.restrict", "该节点不是叶子节点");
			throw new FormValidFailureException(error);
		}
		return biz.delById(id);
	}
}
