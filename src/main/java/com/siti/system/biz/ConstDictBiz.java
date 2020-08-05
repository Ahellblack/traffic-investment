package com.siti.system.biz;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siti.common.base.BaseBiz;
import com.siti.system.db.ConstDict;
import com.siti.system.mapper.ConstDictMapper;

import java.util.List;

@Service
@Transactional
public class ConstDictBiz extends BaseBiz<ConstDictMapper,ConstDict>{
	
	/**
	 * 分页查询
	 */
	public List<ConstDict> findByNameLike(String name){
		return dao.findByNameLike(name);
	}

	/**
	 * 找出某一字典类型的所有字典常量
	 * @param typeId
	 * @return
	 */
	public List<ConstDict> getByTypeId(Integer typeId){
		ConstDict constDict = new ConstDict();
		constDict.setTypeId(typeId);
		return dao.select(constDict);
	}
	
	/**
	 * 查询是否存在该类型字典常量
	 * @param typeId
	 * @return
	 */
	public Boolean isExistTypeId(Integer typeId){
		return dao.isExistTypeId(typeId);
	}
}
