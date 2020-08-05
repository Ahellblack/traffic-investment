package com.siti.system.login.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siti.common.constant.CacheConstant;
import com.siti.common.constant.CommonConstant;
import com.siti.common.constant.DataBaseConstant;
import com.siti.common.vo.*;
import com.siti.system.login.entity.*;
import com.siti.system.login.mapper.*;
import com.siti.system.login.service.ISysBaseAPI;
import com.siti.system.login.service.ISysDepartService;
import com.siti.utils.IPUtils;
import com.siti.utils.SpringContextUtils;
import com.siti.utils.oConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 底层共通业务API，提供其他独立模块调用
 * @Author: scott
 * @Date:2019-4-20 
 * @Version:V1.0
 */
@Slf4j
@Service
public class SysBaseApiImpl implements ISysBaseAPI {
	/** 当前系统数据库类型 */
	public static String DB_TYPE = "";

	@Resource
	private SysLogMapper sysLogMapper;
	@Autowired
	private SysUserMapper userMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private ISysDepartService sysDepartService;

	@Resource
	private SysAnnouncementMapper sysAnnouncementMapper;
	@Resource
	private SysAnnouncementSendMapper sysAnnouncementSendMapper;

	@Resource
	private SysRoleMapper sysRoleMapper;
	@Resource
	private SysDepartMapper departMapper;
	@Resource
	private SysCategoryMapper categoryMapper;



	@Override
	public void addLog(String LogContent, Integer logType, Integer operatetype) {
		SysLog sysLog = new SysLog();
		//注解上的描述,操作日志内容
		sysLog.setLogContent(LogContent);
		sysLog.setLogType(logType);
		sysLog.setOperateType(operatetype);

		//请求的方法名
		//请求的参数

		try {
			//获取request
			HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
			//设置IP地址
			sysLog.setIp(IPUtils.getIpAddr(request));
		} catch (Exception e) {
			sysLog.setIp("127.0.0.1");
		}

		//获取登录用户信息
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser!=null){
			sysLog.setUserid(sysUser.getUsername());
			sysLog.setUsername(sysUser.getRealname());

		}
		sysLog.setCreateTime(new Date());
		//保存系统日志
		sysLogMapper.insert(sysLog);
	}

	@Override
	@Cacheable(cacheNames= CacheConstant.SYS_USERS_CACHE, key="#username")
	public LoginUser getUserByName(String username) {
		if(oConvertUtils.isEmpty(username)) {
			return null;
		}
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = userMapper.getUserByName(username);
		if(sysUser==null) {
			return null;
		}
		BeanUtils.copyProperties(sysUser, loginUser);
		return loginUser;
	}
	
	@Override
	public LoginUser getUserById(String id) {
		if(oConvertUtils.isEmpty(id)) {
			return null;
		}
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = userMapper.selectById(id);
		if(sysUser==null) {
			return null;
		}
		BeanUtils.copyProperties(sysUser, loginUser);
		return loginUser;
	}

	@Override
	public List<String> getRolesByUsername(String username) {
		return sysUserRoleMapper.getRoleByUserName(username);
	}

	@Override
	public List<String> getDepartIdsByUsername(String username) {
		List<SysDepart> list = sysDepartService.queryDepartsByUsername(username);
		List<String> result = new ArrayList<>(list.size());
		for (SysDepart depart : list) {
			result.add(depart.getId());
		}
		return result;
	}

	@Override
	public List<String> getDepartNamesByUsername(String username) {
		List<SysDepart> list = sysDepartService.queryDepartsByUsername(username);
		List<String> result = new ArrayList<>(list.size());
		for (SysDepart depart : list) {
			result.add(depart.getDepartName());
		}
		return result;
	}

	@Override
	public String getDatabaseType() throws SQLException {
		DataSource dataSource = SpringContextUtils.getApplicationContext().getBean(DataSource.class);
		return getDatabaseTypeByDataSource(dataSource);
	}

	@Override
	public List<DictModel> queryDictItemsByCode(String code) {
		return null;
	}

	@Override
	public List<DictModel> queryAllDict() {
		return null;
	}


	@Override
	public void sendSysAnnouncement(String fromUser, String toUser, String title, String msgContent) {
		this.sendSysAnnouncement(fromUser, toUser, title, msgContent, CommonConstant.MSG_CATEGORY_2);
	}

	@Override
	public void sendSysAnnouncement(String fromUser, String toUser, String title, Map<String, String> map, String templateCode) {

	}

	@Override
	public void sendSysAnnouncement(String fromUser, String toUser, String title, Map<String, String> map, String templateCode, String busType, String busId) {

	}

	@Override
	public String parseTemplateByCode(String templateCode, Map<String, String> map) {
		return null;
	}

	@Override
	public void sendSysAnnouncement(String fromUser, String toUser, String title, String msgContent, String setMsgCategory) {
		SysAnnouncement announcement = new SysAnnouncement();
		announcement.setTitile(title);
		announcement.setMsgContent(msgContent);
		announcement.setSender(fromUser);
		announcement.setPriority(CommonConstant.PRIORITY_M);
		announcement.setMsgType(CommonConstant.MSG_TYPE_UESR);
		announcement.setSendStatus(CommonConstant.HAS_SEND);
		announcement.setSendTime(new Date());
		announcement.setMsgCategory(setMsgCategory);
		announcement.setDelFlag(String.valueOf(CommonConstant.DEL_FLAG_0));
		sysAnnouncementMapper.insert(announcement);
		// 2.插入用户通告阅读标记表记录
		String userId = toUser;
		String[] userIds = userId.split(",");
		String anntId = announcement.getId();
		for(int i=0;i<userIds.length;i++) {
			if(oConvertUtils.isNotEmpty(userIds[i])) {
				SysUser sysUser = userMapper.getUserByName(userIds[i]);
				if(sysUser==null) {
					continue;
				}
				SysAnnouncementSend announcementSend = new SysAnnouncementSend();
				announcementSend.setAnntId(anntId);
				announcementSend.setUserId(sysUser.getId());
				announcementSend.setReadFlag(CommonConstant.NO_READ_FLAG);
				sysAnnouncementSendMapper.insert(announcementSend);
				JSONObject obj = new JSONObject();
		    	obj.put("cmd", "user");
		    	obj.put("userId", sysUser.getId());
				obj.put("msgId", announcement.getId());
				obj.put("msgTxt", announcement.getTitile());
		    	//webSocket.sendOneMessage(sysUser.getId(), obj.toJSONString());
			}
		}

	}

	@Override
	public void sendSysAnnouncement(String fromUser, String toUser, String title, String msgContent, String setMsgCategory, String busType, String busId) {

	}


	@Override
	public void updateSysAnnounReadFlag(String busType, String busId) {
		SysAnnouncement announcement = sysAnnouncementMapper.selectOne(new QueryWrapper<SysAnnouncement>().eq("bus_type",busType).eq("bus_id",busId));
		if(announcement != null){
			LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
			String userId = sysUser.getId();
			LambdaUpdateWrapper<SysAnnouncementSend> updateWrapper = new UpdateWrapper().lambda();
			updateWrapper.set(SysAnnouncementSend::getReadFlag, CommonConstant.HAS_READ_FLAG);
			updateWrapper.set(SysAnnouncementSend::getReadTime, new Date());
			updateWrapper.last("where annt_id ='"+announcement.getId()+"' and user_id ='"+userId+"'");
			SysAnnouncementSend announcementSend = new SysAnnouncementSend();
			sysAnnouncementSendMapper.update(announcementSend, updateWrapper);
		}
	}

	@Override
	public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql) {
		return null;
	}

	@Override
	public List<String> queryTableDictByKeys(String table, String text, String code, String[] keyArray) {
		return null;
	}


	/**
	 * 获取数据库类型
	 * @param dataSource
	 * @return
	 * @throws SQLException
	 */
	private String getDatabaseTypeByDataSource(DataSource dataSource) throws SQLException{
		if("".equals(DB_TYPE)) {
			Connection connection = dataSource.getConnection();
			try {
				DatabaseMetaData md = connection.getMetaData();
				String dbType = md.getDatabaseProductName().toLowerCase();
				if(dbType.indexOf("mysql")>=0) {
					DB_TYPE = DataBaseConstant.DB_TYPE_MYSQL;
				}else if(dbType.indexOf("oracle")>=0) {
					DB_TYPE = DataBaseConstant.DB_TYPE_ORACLE;
				}else if(dbType.indexOf("sqlserver")>=0||dbType.indexOf("sql server")>=0) {
					DB_TYPE = DataBaseConstant.DB_TYPE_SQLSERVER;
				}else if(dbType.indexOf("postgresql")>=0) {
					DB_TYPE = DataBaseConstant.DB_TYPE_POSTGRESQL;
				}else {
					//throw new JeecgBootException("数据库类型:["+dbType+"]不识别!");
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}finally {
				connection.close();
			}
		}
		return DB_TYPE;
		
	}



	@Override
	public List<SysCategoryModel> queryAllDSysCategory() {
		List<SysCategory> ls = categoryMapper.selectList(null);
		List<SysCategoryModel> res = oConvertUtils.entityListToModelList(ls,SysCategoryModel.class);
		return res;
	}

	@Override
	public List<DictModel> queryTableDictItemsByCode(String table, String text, String code) {
		return null;
	}

	@Override
	public List<DictModel> queryAllDepartBackDictModel() {
		return null;
	}

	@Override
	public List<ComboModel> queryAllUser() {
		List<ComboModel> list = new ArrayList<ComboModel>();
		List<SysUser> userList = userMapper.selectList(new QueryWrapper<SysUser>().eq("status","1").eq("del_flag","0"));
		for(SysUser user : userList){
			ComboModel model = new ComboModel();
			model.setTitle(user.getRealname());
			model.setId(user.getId());
			model.setUsername(user.getUsername());
			list.add(model);
		}
		return list;
	}

    @Override
    public JSONObject queryAllUser(String[] userIds,int pageNo,int pageSize) {
		JSONObject json = new JSONObject();
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().eq("status","1").eq("del_flag","0");
        List<ComboModel> list = new ArrayList<ComboModel>();
		Page<SysUser> page = new Page<SysUser>(pageNo, pageSize);
		IPage<SysUser> pageList = userMapper.selectPage(page, queryWrapper);
        for(SysUser user : pageList.getRecords()){
            ComboModel model = new ComboModel();
            model.setUsername(user.getUsername());
            model.setTitle(user.getRealname());
            model.setId(user.getId());
            model.setEmail(user.getEmail());
            if(oConvertUtils.isNotEmpty(userIds)){
                for(int i = 0; i<userIds.length;i++){
                    if(userIds[i].equals(user.getId())){
                        model.setChecked(true);
                    }
                }
            }
            list.add(model);
        }
		json.put("list",list);
        json.put("total",pageList.getTotal());
        return json;
    }

	@Override
	public List<ComboModel> queryAllRole() {
		List<ComboModel> list = new ArrayList<ComboModel>();
		List<SysRole> roleList = sysRoleMapper.selectList(new QueryWrapper<SysRole>());
		for(SysRole role : roleList){
			ComboModel model = new ComboModel();
			model.setTitle(role.getRoleName());
			model.setId(role.getId());
			list.add(model);
		}
		return list;
	}

    @Override
    public List<ComboModel> queryAllRole(String[] roleIds) {
        List<ComboModel> list = new ArrayList<ComboModel>();
        List<SysRole> roleList = sysRoleMapper.selectList(new QueryWrapper<SysRole>());
        for(SysRole role : roleList){
            ComboModel model = new ComboModel();
            model.setTitle(role.getRoleName());
            model.setId(role.getId());
            model.setRoleCode(role.getRoleCode());
            if(oConvertUtils.isNotEmpty(roleIds)) {
                for (int i = 0; i < roleIds.length; i++) {
                    if (roleIds[i].equals(role.getId())) {
                        model.setChecked(true);
                    }
                }
            }
            list.add(model);
        }
        return list;
    }

	@Override
	public List<String> getRoleIdsByUsername(String username) {
		return sysUserRoleMapper.getRoleIdByUserName(username);
	}

	@Override
	public String getDepartIdsByOrgCode(String orgCode) {
		return departMapper.queryDepartIdByOrgCode(orgCode);
	}

	@Override
	public DictModel getParentDepartId(String departId) {
		SysDepart depart = departMapper.getParentDepartId(departId);
		DictModel model = new DictModel(depart.getId(),depart.getParentId());
		return model;
	}

	@Override
	public List<SysDepartModel> getAllSysDepart() {
		List<SysDepartModel> departModelList = new ArrayList<SysDepartModel>();
		List<SysDepart> departList = departMapper.selectList(new QueryWrapper<SysDepart>().eq("del_flag","0"));
		for(SysDepart depart : departList){
			SysDepartModel model = new SysDepartModel();
			BeanUtils.copyProperties(depart,model);
			departModelList.add(model);
		}
		return departModelList;
	}

	@Override
	public DynamicDataSourceModel getDynamicDbSourceById(String dbSourceId) {
		return null;
	}

	@Override
	public DynamicDataSourceModel getDynamicDbSourceByCode(String dbSourceCode) {
		return null;
	}


	@Override
	public List<String> getDeptHeadByDepId(String deptId) {
		List<SysUser> userList = userMapper.selectList(new QueryWrapper<SysUser>().like("depart_ids",deptId).eq("status","1").eq("del_flag","0"));
		List<String> list = new ArrayList<>();
		for(SysUser user : userList){
			list.add(user.getUsername());
		}
		return list;
	}

	@Override
	public String upload(MultipartFile file, String bizPath, String uploadType) {
		return null;
	}


}