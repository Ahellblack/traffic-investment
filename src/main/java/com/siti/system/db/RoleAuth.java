package com.siti.system.db;

import javax.persistence.*;
import java.util.Date;

@Table(name="sys_rlat_role_auth")
public class RoleAuth {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="role_id")
	private Integer roleId;
	
	@Column(name="auth_id")
	private Integer authId;
	
	@Column(name="update_by")
	public Integer updateBy;	//操作用户ID
	
	@Column(name="update_time")
	public Date updateTime;    //更新时间

	public RoleAuth() {}

	public RoleAuth(Integer roleId, Integer authId) {
		this.roleId = roleId;
		this.authId = authId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getAuthId() {
		return authId;
	}

	public void setAuthId(Integer authId) {
		this.authId = authId;
	}

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

}
