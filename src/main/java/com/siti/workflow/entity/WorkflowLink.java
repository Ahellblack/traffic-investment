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
public class WorkflowLink {

  @ApiModelProperty(value = "主键")
  private long id;
  @ApiModelProperty(value = "流程编号")
  private long workflowCode;
  @ApiModelProperty(value = "流程节点编号")
  private long workflowNodeCode;
  @ApiModelProperty(value = "链接名称")
  private String workflowLinkName;
  @ApiModelProperty(value = "上一级流程节点编号")
  private String workflowLinkPreNode;
  @ApiModelProperty(value = "下一级流程节点编号")
  private String workflowLinkNextNode;



}
