package com.siti.workflow.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Solarie on 2020/6/18.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowNodeVo {


    private long id;
    private long workflowCode;
    private String nodeName;
    private String nodeLevel;
    private String parentPath;
    private String descr;
    private long updateBy;
    private java.sql.Timestamp updateTime;
    private long sort;
    private long approvalMethod;
    private String relatTable;

    private String workflowName;
    private String workflowDesc;


}
