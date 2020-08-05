package com.siti.system.db;

import javax.persistence.*;
import java.util.Date;

@Table(name="sys_org")
public class Org{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private Integer pid;
	
	//节点路径,以逗号分割
	private String path;
	@Column(name = "type_code")
	private String typeCode;
	
	@Column(name="update_by")
	public Integer updateBy;	//操作用户ID
	
	@Column(name="update_time")
	public Date updateTime;    //更新时间

	public String getTypeCode() {
		return typeCode;
	}

	public Org setTypeCode(String typeCode) {
		this.typeCode = typeCode;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
