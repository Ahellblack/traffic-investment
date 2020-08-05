package com.siti.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import com.siti.system.db.ConstDict;

import java.util.List;

public interface ConstDictMapper extends Mapper<ConstDict>{
	
	@Select("select * from sys_const_dict where name like concat(concat('%',#{name}),'%')")  
	@Results(id = "constDictMap",value = {
		@Result(property = "typeId",column = "type_id"),
		@Result(property = "updateBy",column = "update_by"),
		@Result(property = "updateTime",column = "update_time")
	})
	public List<ConstDict> findByNameLike(String name); 

	@Select("select count(1) from sys_const_dict where type_id=#{typeId}")
	public Boolean isExistTypeId(@Param("typeId") Integer typeId);

}
