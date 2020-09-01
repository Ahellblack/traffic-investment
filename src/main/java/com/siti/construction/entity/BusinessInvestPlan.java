package com.siti.construction.entity;

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
public class BusinessInvestPlan {

  @ApiModelProperty(value = "工程编号")
  private String constructionCode;
  @ApiModelProperty(value = "实施月份 YYYY-MM")
  private String ym;
  @ApiModelProperty(value = "实施项目详情")
  private String content;
  @ApiModelProperty(value = "计划金额")
  private String sum;
  @ApiModelProperty(value = "工作量")
  private String workload;
  private Date createTime;
  private String createBy;
  private java.sql.Timestamp updateTime;
  private String updateBy;
  private String finishTime;
  @ApiModelProperty(value = "类型 1前期 2工程")
  private int type;

  private String description; //描述
}
