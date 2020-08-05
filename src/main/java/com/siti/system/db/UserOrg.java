package com.siti.system.db;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="sys_rlat_user_org")
public class UserOrg {
	
	@Id
	@Column(name="user_id")
	public Integer userId;
	
	@Id
	@Column(name="org_id")
	public Integer orgId;
	
	@Column(name="update_by")
	public Integer updateBy;	//操作用户ID
	
	@Column(name="update_time")
	public Date updateTime;    //更新时间

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	
}
