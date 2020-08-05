package com.siti.system.biz;

import com.siti.common.base.BaseBiz;
import com.siti.system.db.Org;
import com.siti.system.mapper.OrgMapper;
import com.siti.system.mapper.UserOrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgBiz extends BaseBiz<OrgMapper,Org> {
	
	@Autowired
	UserOrgMapper userOrgDao;
	
	//分页查询
	public List<Org> findById(Integer id){
		return dao.findById(id);
	}
	
	/**按名称查找组织，全匹配查询
	 * @return 未查询到返回null
	 * */
	public Org getByName(String orgName){
		Org org = new Org();
		org.setName(orgName);
		List<Org> list = dao.select(org);
		if(list.size() == 1)
			return list.get(0);
		return null;
	}
	/*
	public List<String> getOrgNames(){
		LoginUserInfo user = LoginCtrl.getLoginUserInfo();
		List<String> OrgNames= dao.getOrgNames(user.getId());
		return OrgNames;
	}
	*/
	/**获取父节点用户所属组织*/
	public List<Org> getByPrincipalId(int pid){
		Org org = new Org();
		org.setPid(pid);
		return dao.select(org);
	}
	
	public List<Org> getAll(){
		return dao.selectAll();
	}
	
	/**根据组织id查询是否是叶子节点
	 * */
	public Boolean findLeaf(Integer id){
		return dao.findLeaf(id);
	}
	
	//删除组织
	public int delById(Integer id){
		try{
			userOrgDao.deleteByOrgId(id);
			dao.deleteByPrimaryKey(id);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public Org updateOrg(Org org,String ppath){
		String updatePath = "";
		Org oldOrg = dao.selectByPrimaryKey(org.getId());
		if(ppath != null && !ppath.equals("")){
			updatePath = ppath+","+org.getPid()+","+org.getId();
		}else{
			updatePath = org.getPid()+","+org.getId();
		}
		dao.updatePath(oldOrg.getPid()+","+org.getId(),updatePath);		
		if(org.getPid() != null){
			if(ppath==null){
				org.setPath(org.getPid().toString());
			}else{
				org.setPath(ppath+","+org.getPid());
			}
		}		
		dao.updateByPrimaryKey(org);
		return org;		
	}
	
	public List<String> getOrgCodesByUserId(Integer userId){
		return dao.getOrgCodesByUserId(userId);
	}
}