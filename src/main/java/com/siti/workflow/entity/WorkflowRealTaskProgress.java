package com.siti.workflow.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowRealTaskProgress {

  @ApiModelProperty(value = "主键")
  private long id;
  @ApiModelProperty(value = "项目工程编号")
  private String constructionCode;
  @ApiModelProperty(value = "流程编号")
  private String workflowCode;
  @ApiModelProperty(value = "流程节点编号")
  private String NodeCode;
  @ApiModelProperty(value = "任务号")
  private String taskCode;
  @ApiModelProperty(value = "任务名称")
  private String taskName;
  @ApiModelProperty(value = "任务描述")
  private String taskDesc;
  @ApiModelProperty(value = "初始时间")
  private java.sql.Timestamp initialTime;
  @ApiModelProperty(value = "最后截止时间")
  private java.sql.Timestamp finalTime;
  @ApiModelProperty(value = "任务实际完成时间")
  private java.sql.Timestamp insideTime;
  @ApiModelProperty(value = "实际完成时间")
  private java.sql.Timestamp finishTime;
  @ApiModelProperty(value = "任务完成人")
  private long finishUserId;
  @ApiModelProperty(value = "任务完成人名称")
  private String finishUserName;
  @ApiModelProperty(value = "是否已完成 0 未完成 1 已完成")
  private int status;
  @ApiModelProperty(value = "类型 1前期 2工程")
  private int type;
  @ApiModelProperty(value = "流程编号")
  private String sheetCode;



}
