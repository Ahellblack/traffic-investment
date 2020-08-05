package com.siti.system.login.model;

import lombok.Data;
import com.siti.system.login.entity.SysDepart;
import com.siti.system.login.entity.SysUser;

/**
 * 包含 SysUser 和 SysDepart 的 Model
 *
 * @author sunjianlei
 */
@Data
public class SysUserSysDepartModel {

    private SysUser sysUser;
    private SysDepart sysDepart;

}
