package com.siti.system.ctrl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.siti.common.base.BaseCtrl;
import com.siti.system.biz.ConstDictBiz;
import com.siti.system.db.ConstDict;

import java.util.List;

/**
 * 字典常量配置
 */
@RestController
@RequestMapping("constDicts")
public class ConstDictCtrl extends BaseCtrl<ConstDictBiz,ConstDict>{
	
	/**
	 * 找出某一字典类型的所有字典常量
	 * @param typeId
	 * @return
	 */
	@GetMapping(params="typeId")
	public List<ConstDict> getByTypeId(Integer typeId){
		return biz.getByTypeId(typeId);
	}
	
	@GetMapping(params={"page","pageSize"})
	public PageInfo<ConstDict> findByPageAndParams(int page, int pageSize,String name){	
		PageHelper.startPage(page, pageSize);  
	    List<ConstDict> list = biz.findByNameLike(name);  
	    PageInfo<ConstDict> p=new PageInfo<ConstDict>(list);              
	    return p;  
	}

}
