package com.siti.system.biz;

import org.springframework.stereotype.Service;
import com.siti.common.base.BaseBiz;
import com.siti.system.db.UserOrg;
import com.siti.system.mapper.UserOrgMapper;

@Service
public class UserOrgBiz extends BaseBiz<UserOrgMapper,UserOrg> {

	public void deleteByUserId(int userId){
		dao.deleteByUserId(userId);
	}
}
