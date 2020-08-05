package com.siti.system.biz;

import org.springframework.stereotype.Service;
import com.siti.common.base.BaseBiz;
import com.siti.system.db.UserRole;
import com.siti.system.mapper.UserRoleMapper;

import java.util.List;

@Service
public class UserRoleBiz extends BaseBiz<UserRoleMapper,UserRole> {

	public void deleteByUserId(int userId){
		dao.deleteByUserId(userId);
	}
	
	public List<Integer> getRoleIdByUserId(int userId){
		return dao.getRoleIdByUserId(userId);
	}
}
