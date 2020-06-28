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
public class WorkflowTask {

  @ApiModelProperty(value = "主键")
  private long id;
  @ApiModelProperty(value = "主键")
  private String taskCode;
  @ApiModelProperty(value = "主键")
  private String taskName;
  @ApiModelProperty(value = "主键")
  private String taskDesc;
  @ApiModelProperty(value = "主键")
  private long workflowCode;
  @ApiModelProperty(value = "主键")
  private String workflowNodeCode;
  @ApiModelProperty(value = "主键")
  private long isNecessary;


}
