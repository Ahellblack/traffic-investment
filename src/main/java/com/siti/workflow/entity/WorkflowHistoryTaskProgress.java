package com.siti.workflow.entity;

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
public class WorkflowHistoryTaskProgress {

  @ApiModelProperty(value = "主键")
  private long id;
  @ApiModelProperty(value = "工程号")
  private String constructionCode;
  @ApiModelProperty(value = "流程号")
  private long workflowCode;
  @ApiModelProperty(value = "节点号")
  private long workflowNodeCode;
  @ApiModelProperty(value = "任务编号")
  private String taskCode;
  @ApiModelProperty(value = "任务名称")
  private String taskName;
  @ApiModelProperty(value = "任务描述")
  private String taskDesc;
  @ApiModelProperty(value = "进入任务时间")
  private String initialTime;
  @ApiModelProperty(value = "规定任务完成时间")
  private String finalTime;
  @ApiModelProperty(value = "实际任务结束时间")
  private String finishTime;
  @ApiModelProperty(value = "完成用户id")
  private long finishUserId;
  @ApiModelProperty(value = "完成用户人姓名")
  private String finishUserName;



}
