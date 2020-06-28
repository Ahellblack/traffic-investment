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
public class Workflow {

  @ApiModelProperty(value = "主键,流程号")
  private long workflowCode;
  @ApiModelProperty(value = "流程名称")
  private String workflowName;
  @ApiModelProperty(value = "流程描述")
  private String workflowDesc;


}
