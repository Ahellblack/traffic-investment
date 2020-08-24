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
  private String workflowCode;
  @ApiModelProperty(value = "流程名称")
  private String workflowName;
  @ApiModelProperty(value = "流程描述")
  private String workflowDesc;
  @ApiModelProperty(value = "版本")
  private String version;
  @ApiModelProperty(value = "创建时间")
  private String create_time;
  @ApiModelProperty(value = "创建人")
  private String create_by;
  @ApiModelProperty(value = "更新时间")
  private String update_time;
  @ApiModelProperty(value = "更新人")
  private String update_by;
  @ApiModelProperty(value = "类型 1前期 2工程")
  private int type;

}
