package com.siti.workflow.vo;

import com.siti.workflow.entity.WorkflowTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowNodeVo {

    private String nodeCode;
    private String workflowCode;
    private String nodeName;
    private String nodeLevel;
    private String parentPath;
    private String descr;
    private long updateBy;
    private java.sql.Timestamp updateTime;
    private long sort;
    private long approvalMethod;
    private String relatTable;
    private String insideTime;
    private String initialTime;
    private String finalTime;
    private String finishTime;
    private String workflowName;
    private String workflowDesc;
    @ApiModelProperty(value = "项目编号")
    private String constructionCode;
    @ApiModelProperty(value = "项目名称")
    private String constructionName;

    private List<WorkflowTask> workflowTaskList;



}
