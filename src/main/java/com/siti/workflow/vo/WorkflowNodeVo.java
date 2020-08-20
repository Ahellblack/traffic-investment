package com.siti.workflow.vo;

import com.siti.workflow.entity.WorkflowRealTaskProgress;
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
    private Integer status;//是否已经完成 0 未完成 1 已完成
    private Integer onUsed;// 是否启用 0 否 1 是

    private List<WorkflowRealTaskProgress> workflowTaskList;



}
