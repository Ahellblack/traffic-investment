package com.siti.construction.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("business_construction")
public class BusinessConstruction {

  @ApiModelProperty(value = "工程编号")
  private String constructionCode;
  @ApiModelProperty(value = "工程名称")
  private String constructionName;
  @ApiModelProperty(value = "工程类型")
  private long type;
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
  private java.sql.Timestamp createTime;
  @ApiModelProperty(value = "创建人")
  private String createBy;
  @ApiModelProperty(value = "修改时间")
  private java.sql.Timestamp updateTime;
  @ApiModelProperty(value = "修改人")
  private String updateBy;
  @ApiModelProperty(value = "工程名称")
  private String imageUrl;
  @ApiModelProperty(value = "初始时间")
  private java.sql.Timestamp initialTime;
  @ApiModelProperty(value = "最后截止时间")
  private java.sql.Timestamp finalTime;
  @ApiModelProperty(value = "任务实际完成时间")
  private java.sql.Timestamp insideTime;
  @ApiModelProperty(value = "实际完成时间")
  private java.sql.Timestamp finishTime;



}
