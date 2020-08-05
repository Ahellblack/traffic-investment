package com.siti.system.biz;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siti.common.base.BaseBiz;
import com.siti.system.db.ConstType;
import com.siti.system.mapper.ConstTypeMapper;

import java.util.List;

@Service
@Transactional
public class ConstTypeBiz extends BaseBiz<ConstTypeMapper,ConstType>{

	public List<ConstType> getAll(){
		return dao.selectAll();
	}
}
