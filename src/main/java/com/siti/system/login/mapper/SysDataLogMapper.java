package com.siti.system.login.mapper;

import org.apache.ibatis.annotations.Param;
import com.siti.system.login.entity.SysDataLog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface SysDataLogMapper extends BaseMapper<SysDataLog>{
	/**
	 * 通过表名及数据Id获取最大版本
	 * @param tableName
	 * @param dataId
	 * @return
	 */
	public String queryMaxDataVer(@Param("tableName") String tableName, @Param("dataId") String dataId);
	
}
