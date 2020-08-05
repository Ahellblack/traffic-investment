package com.siti.system.db;

import javax.persistence.*;
import java.util.Date;

@Table(name="sys_auth")
public class Auth{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;	//权限(资源)名称
	private Integer pid;	//父ID
	private Byte type;	//资源类型 1:menu 2:控制器 3:方法
	private String url;	//访问url地址
	private String code;	//权限编码	
	private String props;	//不同资源类型的属性信息
	private Integer sort;	//排序号	
	private String pids;	//父ID路径
	private String descr;	//描述
	@Column(name="update_by")
	public Integer updateBy;	//操作用户ID
	@Column(name="update_time")
	public Date updateTime;    //更新时间	
	
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
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getPids() {
		return pids;
	}
	public void setPids(String pids) {
		this.pids = pids;
	}
	public String getProps() {
		return props;
	}
	public void setProps(String props) {
		this.props = props;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
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
