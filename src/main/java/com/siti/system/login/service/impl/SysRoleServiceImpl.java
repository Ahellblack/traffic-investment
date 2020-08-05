package com.siti.system.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.system.login.entity.SysRole;
import com.siti.system.login.mapper.SysRoleMapper;
import com.siti.system.login.mapper.SysUserMapper;
import com.siti.system.login.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 *
 * @since 2018-12-19
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    SysRoleMapper sysRoleMapper;
    @Resource
    SysUserMapper sysUserMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRole(String roleid) {
        //1.删除角色和用户关系
        sysRoleMapper.deleteRoleUserRelation(roleid);
        //2.删除角色和权限关系
        sysRoleMapper.deleteRolePermissionRelation(roleid);
        //3.删除角色
        this.removeById(roleid);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchRole(String[] roleIds) {
        //1.删除角色和用户关系
        sysUserMapper.deleteBathRoleUserRelation(roleIds);
        //2.删除角色和权限关系
        sysUserMapper.deleteBathRolePermissionRelation(roleIds);
        //3.删除角色
        this.removeByIds(Arrays.asList(roleIds));
        return true;
    }
}
