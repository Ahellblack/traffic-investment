package com.siti.workflow.vo;

import com.siti.workflow.entity.WorkflowRealTaskProgress;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowRealInfoVo {

  @ApiModelProperty(value = "主键")
  private long id;

  @ApiModelProperty(value = "流程单号")
  private String sheetCode;
  @ApiModelProperty(value = "项目工程号")
  private String constructionCode;
  @ApiModelProperty(value = "项目工程名")
  private String constructionName;
  @ApiModelProperty(value = "流程号")
  private String workflowCode;
  @ApiModelProperty(value = "流程节点号")
  private String NodeCode;
  @ApiModelProperty(value = "流程状态")
  private String workflowStatus;
  @ApiModelProperty(value = "节点描述")
  private  String description;
  @ApiModelProperty(value = "节点名称")
  private String nodeName;
  @ApiModelProperty(value = "进入当前节点时间")
  private String insideTime;
  @ApiModelProperty(value = "最后截止时间")
  private String finalTime;
  @ApiModelProperty(value = "实际完成时间")
  private String finishTime;
  @ApiModelProperty(value = "任务负责人id")
  private long approvalUserId;
  @ApiModelProperty(value = "任务负责人名称")
  private String approvalUserName;
  @ApiModelProperty(value = "是否已经完成 0 未完成 1 已完成")
  private Integer status;
  private Integer onUsed;// 是否启用 0 否 1 是

  private List<WorkflowRealTaskProgress> taskProgressList;
}
