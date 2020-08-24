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
public class WorkflowNode {

  @ApiModelProperty(value = "主键")
  private String nodeCode;
  @ApiModelProperty(value = "流程号")
  private String workflowCode;
  @ApiModelProperty(value = "结点名称")
  private String workflowNodeCode;
  @ApiModelProperty(value = "节点等级")
  private String nodeLevel;
  @ApiModelProperty(value = "父级节点")
  private String parentPath;
  @ApiModelProperty(value = "描述")
  private  String description;
  @ApiModelProperty(value = "修改人")
  private long updateBy;
  @ApiModelProperty(value = "修改日期")
  private java.sql.Timestamp updateTime;
  @ApiModelProperty(value = "排序号")
  private long sort;
  @ApiModelProperty(value = "审批方式 0 串行 1并行 所有人都要审批  2抢占 一人审批就行  approval_method")
  private long approvalMethod;
  @ApiModelProperty(value = "关联表")
  private String relatTable;
  @ApiModelProperty(value = "进入节点时间")
  private String insideTime;
  @ApiModelProperty(value = "配置的初始时间")
  private String initialTime;
  @ApiModelProperty(value = "配置的结束时间")
  private String finalTime;
  @ApiModelProperty(value = "实际完成时间")
  private String finishTime;




}
