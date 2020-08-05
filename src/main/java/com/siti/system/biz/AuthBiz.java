package com.siti.system.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import com.siti.common.base.BaseBiz;
import com.siti.common.base.FormValidFailureException;
import com.siti.system.db.Auth;
import com.siti.system.mapper.AuthMapper;
import com.siti.system.mapper.RoleAuthMapper;

import java.util.List;

@Service
@Transactional
public class AuthBiz extends BaseBiz<AuthMapper,Auth>{

	@Autowired
	private RoleAuthMapper roleAuthDao;
	
	public List<Auth> findBySort(Integer sort){
//		Integer minus = splitArr(sort)*2;
//		String equalSort = sort.toString().substring(0,sort.toString().length()-minus);
		return dao.findBySort(sort);
	}
	
	//判断某节点是否存在子节点
	public Boolean findSid(Integer pid,Integer id){
		return dao.isExistSon(pid, id);
	}
	
	/**删除前先检查是否有子节点，有则不能删除，同时删除角色权限关联信息*/
	public int delById(Integer id,Integer pid,Integer sort){
		try{
			//删除角色权限关联表信息
			roleAuthDao.deleteByAuthId(id);
			dao.deleteByPrimaryKey(id);
			updateSortD(pid, sort, (int)Math.pow(100,splitArr(sort)),splitArr(sort)*2);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**根据角色ID获取菜单信息*/
	public List<Auth> getMenuByRoleId(List<Integer> roleIds){
		return dao.getMenuByRoleId(roleIds);
	}
	
	/**根据角色ID获取授权信息。权限验证用
	 * */
	public List<Auth> getPermissionByRoleId(List<Integer> roleIds){
		return dao.getPermissionByRoleId(roleIds);
	}
	
	/**根据角色ID获取权限信息
	 * */
	public List<Auth> getByRoleId(List<Integer> roleId){
		return dao.getByRoleId(roleId);
	}
	
	/**根据用户ID获取权限信息
	 * */
	public List<Auth> getByUserId(Integer userId){
		return dao.getByUserId(userId);
	}
	
	/**获取菜单
	 * */
	public List<Auth> getMenu(List<Integer> roleIds){
		return dao.getMenu(roleIds);
	}
	
	/**添加权限信息
	 * @param pidSort 父节点的排序号
	 * @param insertSort 插入节点的排序号
	 * @param insertId 插入节点的id
	 * @param insertType 添加节点至插入节点之前（1）或之后（2）
	 * @throws FormValidFailureException 
	 */
	public Auth saveAuth(Auth auth,Integer pidSort,Integer insertSort,Integer insertId,Integer insertType,Errors error) throws FormValidFailureException{
		if(pidSort == null){//是否选择了父节点
			if(insertSort == null){//是否选择了插入节点
				if(dao.isExistSon(auth.getPid(),auth.getId())){//是否存在可选插入节点，若存在则必选
					error.rejectValue("id", "Auth.id.restrict", "请选择插入节点");
					throw new FormValidFailureException(error);
				}
				auth.setSort(10000);
			}else{
				if(insertType==1){//保存节点到插入节点之前
					updateSort(auth.getPid(), insertSort,(int)Math.pow(100,splitArr(insertSort)),insertType,splitArr(insertSort)*2);//更新相关节点排序号
					auth.setSort(insertSort);
				}else{////保存节点到插入节点之后
					updateSort(auth.getPid(), insertSort,(int)Math.pow(100,splitArr(insertSort)),insertType,splitArr(insertSort)*2);//更新相关节点排序号
					auth.setSort(insertSort+(int)Math.pow(100,splitArr(insertSort)));
				}
			}
		}else{
			if(insertSort == null){
				if(dao.isExistSon(auth.getPid(),auth.getId())){
					error.rejectValue("id", "Auth.id.restrict", "请选择插入节点");
					throw new FormValidFailureException(error);
				}
				auth.setSort(pidSort+(int)Math.pow(100,splitArr(pidSort)-1));
			}else{
				if(insertType==1){
					updateSort(auth.getPid(), insertSort,(int)Math.pow(100,splitArr(insertSort)),insertType,splitArr(insertSort)*2);//更新相关节点排序号
					auth.setSort(insertSort);
				}else{
					updateSort(auth.getPid(), insertSort,(int)Math.pow(100,splitArr(insertSort)),insertType,splitArr(insertSort)*2);//更新相关节点排序号
					auth.setSort(insertSort+(int)Math.pow(100,splitArr(insertSort)));
				}
			}
		}
		dao.insert(auth);
		return auth;
	}
	
	/**修改权限信息
	 * @param oldPid 修改之前的父节点id
	 * @param pidSort 修改之后的父节点排序号
	 * @param insertSort 插入节点的排序号
	 * @param insertId 插入节点的id
	 * @param insertType 添加节点至插入节点之前（1）或之后（2）
	 * @throws FormValidFailureException 
	 */
	public Auth updateAuth(Auth auth,Integer oldPid,Integer pidSort,Integer insertSort,Integer insertId,Integer insertType,Errors error) throws FormValidFailureException{
		Integer oldSort = auth.getSort();	
		if(auth.getPid()!=oldPid){ //更换了父节点
			if(auth.getPid()!=null){ //新父节点不为null
				if(insertType!=null){ //插入节点不为null
					if(insertType==1){
						auth.setSort(insertSort);
					}else if(insertType==2){
						auth.setSort(insertSort+(int)Math.pow(100,splitArr(insertSort)));
					}
				}else{ //插入节点为null
					auth.setSort(pidSort+(int)Math.pow(100,(splitArr(pidSort)-1)));	
				}				
			}else{ //新父节点为null
				if(insertType==1){
					auth.setSort(insertSort);
				}else if(insertType==2){
					auth.setSort(insertSort+(int)Math.pow(100,splitArr(insertSort)));
				}
			}
			updateSort(auth.getPid(), insertSort, (int)Math.pow(100,splitArr(insertSort)), insertType,splitArr(insertSort)*2);
			String sort = auth.getSort().toString();
			dao.updateSonSort(splitArr(oldSort)*2,oldSort.toString().substring(0,oldSort.toString().length()- splitArr(oldSort)*2), sort.substring(0,sort.length()- splitArr(auth.getSort())*2), splitArr(auth.getSort())*2,oldPid);			
			updateSortD(oldPid, oldSort, (int)Math.pow(100,splitArr(oldSort)),splitArr(oldSort)*2);			
		}else{ //未更换父节点
			if(auth.getPid()!=null){ //新父节点不为null
				if(insertType!=null){ //插入节点不为null
					if(insertType==1){
						if(auth.getSort()>insertSort)
							auth.setSort(insertSort);
						else
							auth.setSort(insertSort-(int)Math.pow(100,splitArr(insertSort)));
					}else if(insertType==2){
						if(auth.getSort()>insertSort)
							auth.setSort(insertSort+(int)Math.pow(100,splitArr(insertSort)));
						else
							auth.setSort(insertSort);	
					}
				}				
			}else{ //新父节点为null
				if(insertType==1){
					if(auth.getSort()>insertSort)
						auth.setSort(insertSort);
					else
						auth.setSort(insertSort-(int)Math.pow(100,splitArr(insertSort)));
				}else if(insertType==2){
					if(auth.getSort()>insertSort)
						auth.setSort(insertSort+(int)Math.pow(100,splitArr(insertSort)));
					else
						auth.setSort(insertSort);	
				}
			}
			String sort = auth.getSort().toString();
			dao.updateSonSortOne(splitArr(oldSort)*2,oldSort.toString().substring(0,oldSort.toString().length()- splitArr(oldSort)*2),splitArr(auth.getSort())*2,oldPid);			
			updateSortU(auth.getPid(),oldSort,insertSort,(int)Math.pow(100,splitArr(insertSort)),insertType,splitArr(oldSort)*2);//更新相关节点排序号
			dao.updateSonSortTwo(sort.substring(0,sort.length()- splitArr(auth.getSort())*2));			
		}
		dao.updateByPrimaryKey(auth);	
		return auth;
	}
	/*public Auth updateAuth(Auth auth,Integer oldPid,Integer pidSort,Integer insertSort,Integer insertId,Integer insertType,Errors error) throws FormValidFailureException{
		if(pidSort == null){//是否选择了父节点
			if(insertType != null){//是否选择了插入节点
				if(insertType==1){//保存节点到插入节点之前
					updateSortU(auth.getPid(),auth.getSort(),insertSort,(int)Math.pow(100,splitArr(insertSort)),insertType,splitArr(auth.getSort())*2);//更新相关节点排序号
					if(auth.getSort()>insertSort)
						auth.setSort(insertSort);
					else
						auth.setSort(insertSort-(int)Math.pow(100,splitArr(insertSort)));										
				}else if(insertType==2){//保存节点到插入节点之后
					updateSortU(auth.getPid(),auth.getSort(),insertSort,(int)Math.pow(100,splitArr(insertSort)),insertType,splitArr(auth.getSort())*2);//更新相关节点排序号
					if(auth.getSort()>insertSort)
						auth.setSort(insertSort+(int)Math.pow(100,splitArr(insertSort)));
					else
						auth.setSort(insertSort);						
				}
			}
		}else{
			if(insertType != null){
				if(auth.getPid()!=oldPid){//是否更换父节点
					Integer oldSort = auth.getSort();
					updateSort(auth.getPid(), insertSort, (int)Math.pow(100,splitArr(insertSort)), insertType,splitArr(insertSort)*2);
					if(insertType==1){
						auth.setSort(insertSort);
					}else if(insertType==2){
						auth.setSort(insertSort+(int)Math.pow(100,splitArr(insertSort)));
					}
					String sort = auth.getSort().toString();
					dao.updateSonSort(oldSort.toString().substring(0,oldSort.toString().length()- splitArr(oldSort)*2)+"%", sort.substring(0,sort.length()- splitArr(auth.getSort())*2), splitArr(auth.getSort())*2,oldPid);
					updateSortD(oldPid, oldSort, (int)Math.pow(100,splitArr(oldSort)),splitArr(oldSort)*2);
				}else{
					if(insertType==1){
						updateSortU(auth.getPid(),auth.getSort(),insertSort,(int)Math.pow(100,splitArr(insertSort)),insertType,splitArr(auth.getSort())*2);//更新相关节点排序号
						if(auth.getSort()>insertSort)
							auth.setSort(insertSort);
						else
							auth.setSort(insertSort-(int)Math.pow(100,splitArr(insertSort)));	
					}else if(insertType==2){
						updateSortU(auth.getPid(),auth.getSort(),insertSort,(int)Math.pow(100,splitArr(insertSort)),insertType,splitArr(auth.getSort())*2);//更新相关节点排序号
						if(auth.getSort()>insertSort)
							auth.setSort(insertSort+(int)Math.pow(100,splitArr(insertSort)));
						else
							auth.setSort(insertSort);	
					}
				}								
			}else{
				if(dao.isExistSon(auth.getPid(),auth.getId())){//是否存在可选插入节点，若存在则必选
					error.rejectValue("id", "Auth.id.restrict", "请选择插入节点");
					throw new FormValidFailureException(error);
				}
				Integer oldSort = auth.getSort();
				auth.setSort(pidSort+(int)Math.pow(100,(splitArr(pidSort)-1)));	
				String sort = auth.getSort().toString();
				dao.updateSonSort(oldSort.toString().substring(0,oldSort.toString().length()- splitArr(oldSort)*2)+"%", sort.substring(0,sort.length()- splitArr(auth.getSort())*2), splitArr(auth.getSort())*2,oldPid);
				updateSortD(oldPid, oldSort, (int)Math.pow(100,splitArr(oldSort)),splitArr(oldSort)*2);
			}
		}
		dao.updateByPrimaryKey(auth);	
		return auth;
	}*/
	
	public List<Auth> getAuthsTree(){
		return dao.getAuthsTree();
	}
	
	public List<Auth> getAuthsByPid(Integer pid){
		return dao.getAuthsByPid(pid);
	}
	
	/**添加时更新插入节点的排序号
	 * @param pid 父节点
	 * @param sort 插入节点的排序号
	 * @param plusSort 增加的排序号
	 * @param insertType 添加节点至插入节点之前（1）或之后（2）
	 * @param sortDigit 不匹配位数（从后往前） 
	 */
	public void updateSort(Integer pid,Integer sort,Integer plusSort,Integer insertType,Integer sortDigit){
		List<Short> sortLists = dao.updateSortList(pid, sort, "insert", insertType, null);
		for(int i=0;i<sortLists.size();i++){
			String sortLike = sortLists.get(i).toString().substring(0,sortLists.get(i).toString().length()-sortDigit);
			dao.updatePlusOrMinus(sortDigit,plusSort, sortLike, "plus");
		}
	}
	
	/**删除时更新插入节点的排序号
	 * @param pid 父节点
	 * @param sort 插入节点的排序号
	 * @param plusSort 增加的排序号
	 * @param sortDigit 不匹配位数（从后往前） 
	 */
	public void updateSortD(Integer pid,Integer sort,Integer plusSort,Integer sortDigit){
		List<Short> sortLists = dao.updateSortList(pid, sort, "delete", null, null);
		for(int i=0;i<sortLists.size();i++){
			String sortLike = sortLists.get(i).toString().substring(0,sortLists.get(i).toString().length()-sortDigit);
			dao.updatePlusOrMinus(sortDigit,plusSort, sortLike, "minus");
		}
	}
	
	/**修改时更新插入节点的排序号
	 * @param pid 父节点
	 * @param sort 排序号
	 * @param insertSort 插入节点的排序号
	 * @param insertType 添加节点至插入节点之前（1）或之后（2）
	 * @param plusSort 增加的排序号
	 * @param sortDigit 不匹配位数（从后往前） 
	 */
	public void updateSortU(Integer pid,Integer sort,Integer insertSort,Integer plusSort,Integer insertType,Integer sortDigit){
		List<Short> sortLists = dao.updateSortList(pid, sort, "update", insertType, insertSort);
		if(insertType == 1){
			if(sort>insertSort){
				for(int i=0;i<sortLists.size();i++){
					String sortLike = sortLists.get(i).toString().substring(0,sortLists.get(i).toString().length()-sortDigit);
					dao.updatePlusOrMinus(sortDigit,plusSort, sortLike, "plus");
				}
			}else{
				for(int i=0;i<sortLists.size();i++){
					String sortLike = sortLists.get(i).toString().substring(0,sortLists.get(i).toString().length()-sortDigit);
					dao.updatePlusOrMinus(sortDigit,plusSort, sortLike, "minus");
				}
			}
		}else{
			if(sort>insertSort){
				for(int i=0;i<sortLists.size();i++){
					String sortLike = sortLists.get(i).toString().substring(0,sortLists.get(i).toString().length()-sortDigit);
					dao.updatePlusOrMinus(sortDigit,plusSort, sortLike, "plus");
				}
			}else{
				for(int i=0;i<sortLists.size();i++){
					String sortLike = sortLists.get(i).toString().substring(0,sortLists.get(i).toString().length()-sortDigit);
					dao.updatePlusOrMinus(sortDigit,plusSort, sortLike, "minus");
				}
			}
		}			
	}
	
	public Integer splitArr(Integer sort){
		Integer count = 0;
		String str=String.valueOf(sort);
		if(str.length()%2 != 0){
			str = "0"+str;
		}
		String str1="";
		String arr[] = new String[str.length()/2];
		for(int i=0;i<str.length();i++){
			str1+=str.charAt(i);
			if((i+1)%2==0){
				arr[i/2]=str1;
				if(str1.equals("00")){
					count++;
				}
				str1="";
			}
		} 
		return count;
	}
}
