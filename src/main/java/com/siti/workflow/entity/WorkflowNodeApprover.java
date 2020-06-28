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
public class WorkflowNodeApprover {

  @ApiModelProperty(value = "主键")
  private long id;
  @ApiModelProperty(value = "流程编号")
  private long workflowCode;
  @ApiModelProperty(value = "流程节点编号")
  private long workflowNodeCode;
  @ApiModelProperty(value = "用户id")
  private long userId;
  @ApiModelProperty(value = "角色id")
  private long roleId;
  @ApiModelProperty(value = "组织id")
  private long orgId;




}
