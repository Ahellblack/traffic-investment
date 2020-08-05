package com.siti.system.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 部门角色人员信息
 *
 * @Date:   2020-02-13
 * @Version: V1.0
 */
@Data
@TableName("sys_depart_role_user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_depart_role_user对象", description="部门角色人员信息")
public class SysDepartRoleUser {
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键id")
	private String id;
	/**用户id*/
    @ApiModelProperty(value = "用户id")
	private String userId;
	/**角色id*/
    @ApiModelProperty(value = "角色id")
	private String droleId;

	public SysDepartRoleUser() {

	}

	public SysDepartRoleUser(String userId, String droleId) {
		this.userId = userId;
		this.droleId = droleId;
	}
}
