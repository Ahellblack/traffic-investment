package com.siti.construction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("business_construction")
public class BusinessConstruction {

    @TableId(value = "construction_code")
    @ApiModelProperty(value = "工程编号")
    private String constructionCode;
    @ApiModelProperty(value = "工程名称")
    private String constructionName;
    @ApiModelProperty(value = "工程类型")
    private String type;
    @ApiModelProperty(value = "工程状态")
    private long status;
    @ApiModelProperty(value = "建设内容/项目描述")
    private String buildContent;
    @ApiModelProperty(value = "四至范围")
    private String fourRange;
    @ApiModelProperty(value = "施工纬度")
    private String constructionPurpose;
    @ApiModelProperty(value = "主办部门")
    private String manageDepart;
    @ApiModelProperty(value = "建设规模描述/规划指标")
    private String constructionScale;
    @ApiModelProperty(value = "建设投资金额")
    private double constructionInvest;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "修改时间")
    private java.sql.Timestamp updateTime;
    @ApiModelProperty(value = "修改人")
    private String updateBy;
    @ApiModelProperty(value = "工程名称")
    private String imageUrl;
    @ApiModelProperty(value = "初始时间")
    private String initialTime;
    @ApiModelProperty(value = "最后截止时间")
    private String finalTime;
    @ApiModelProperty(value = "任务实际完成时间")
    private String insideTime;
    @ApiModelProperty(value = "实际完成时间")
    private String finishTime;

    @ApiModelProperty(value = "项目经理")
    private String pm;
    @ApiModelProperty(value = "工程项目经理")
    private String enginPm;
    @ApiModelProperty(value = "建设单位")
    private String buildUnit;
    @ApiModelProperty(value = "施工单位/代建单位")
    private String constructionUnit;
    @ApiModelProperty(value = "监管单位")
    private String superviseUnit;
    @ApiModelProperty(value = "财务监管单位")
    private String financeUnit;



}
