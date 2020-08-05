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
    @ApiModelProperty(value = "任务点")
    private String taskCode;
    @ApiModelProperty(value = "任务名")
    private String taskName;
    @ApiModelProperty(value = "任务描述")
    private String taskDesc;
    @ApiModelProperty(value = "流程号")
    private String workflowCode;
    @ApiModelProperty(value = "节点号")
    private String nodeCode;
    @ApiModelProperty(value = "是否需要上传文件")
    private long needFiles;
    @ApiModelProperty(value = "是否有衍生流程")
    private long haveRamification;
    @ApiModelProperty(value = "衍生流程号")
    private long ramificWorkflowId;


}
