package com.siti.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 部门角色权限
 *
 * @Date:   2020-02-12
 * @Version: V1.0
 */
@Data
@TableName("sys_depart_role_permission")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_depart_role_permission对象", description="部门角色权限")
public class SysDepartRolePermission {
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id")
	private String id;
	/**部门id*/
    @ApiModelProperty(value = "部门id")
	private String departId;
	/**角色id*/
    @ApiModelProperty(value = "角色id")
	private String roleId;
	/**权限id*/
    @ApiModelProperty(value = "权限id")
	private String permissionId;
	/**dataRuleIds*/
    @ApiModelProperty(value = "dataRuleIds")
	private String dataRuleIds;

	public SysDepartRolePermission() {
	}

	public SysDepartRolePermission(String roleId, String permissionId) {
		this.roleId = roleId;
		this.permissionId = permissionId;
	}
}
