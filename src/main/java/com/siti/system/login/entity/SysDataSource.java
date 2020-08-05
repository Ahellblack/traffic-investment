package com.siti.system.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 多数据源管理
 *
 * @Date: 2019-12-25
 * @Version: V1.0
 */
@Data
@TableName("sys_data_source")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "sys_data_source对象", description = "多数据源管理")
public class SysDataSource {

    /**
     * id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 数据源编码
     */
    @ApiModelProperty(value = "数据源编码")
    private String code;
    /**
     * 数据源名称
     */
    @ApiModelProperty(value = "数据源名称")
    private String name;
    /**
     * 描述
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 数据库类型
     */
    @ApiModelProperty(value = "数据库类型")
    private String dbType;
    /**
     * 驱动类
     */
    @ApiModelProperty(value = "驱动类")
    private String dbDriver;
    /**
     * 数据源地址
     */
    @ApiModelProperty(value = "数据源地址")
    private String dbUrl;
    /**
     * 数据库名称
     */
    @ApiModelProperty(value = "数据库名称")
    private String dbName;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String dbUsername;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String dbPassword;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
}
