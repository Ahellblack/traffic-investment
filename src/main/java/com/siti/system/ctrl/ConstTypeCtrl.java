package com.siti.system.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import com.siti.common.base.BaseCtrl;
import com.siti.common.base.FormValidFailureException;
import com.siti.system.biz.ConstDictBiz;
import com.siti.system.biz.ConstTypeBiz;
import com.siti.system.db.ConstDict;
import com.siti.system.db.ConstType;

import java.util.List;

/**
 * 字典常量类型配置
 */
@RestController
@RequestMapping("constTypes")
public class ConstTypeCtrl extends BaseCtrl<ConstTypeBiz,ConstType>{

	@Autowired
	private ConstDictBiz constDictBiz;
	
	/**@param id 主键
	 * @return 0:删除失败，1:删除成功
	 * @throws FormValidFailureException 
	 * */
	@DeleteMapping("id/{id}")
	public Integer delete(@PathVariable Integer id, ConstDict constDict, Errors error) throws FormValidFailureException{
		if(constDictBiz.isExistTypeId(id)){
			error.rejectValue("id", "ConstType.id.restrict", "存在该类型的字典常量信息");
			throw new FormValidFailureException(error);
		}
		try{
			biz.deleteById(id);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@GetMapping
	public List<ConstType> getAll(){
		return biz.getAll();
	}
}