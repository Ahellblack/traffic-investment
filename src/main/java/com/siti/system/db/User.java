package com.siti.system.db;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "sys_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "real_name")
	private String realName;

	private String password;
	
	@Column(name = "user_tel")
	private String userTel;

	@Column(name="update_by")
	public Integer updateBy;	//操作用户ID
	
	@Column(name="update_time")
	public Date updateTime;    //更新时间
	
	@Transient
	private List<Role> roles;
	
	@Transient
	private List<Org> orgs;
	
    public User() {
    }
	
    public User(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.realName = user.getRealName();
        this.password = user.getPassword();
        this.updateBy = user.getUpdateBy();
        this.updateTime = user.getUpdateTime();
        this.roles = user.getRoles();
        this.orgs = user.getOrgs();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Org> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<Org> orgs) {
		this.orgs = orgs;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	
}
