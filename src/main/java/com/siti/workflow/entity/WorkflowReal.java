package com.siti.workflow.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by 12293 on 2020/8/3.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowReal {

    private String sheetCode;
    private String workflowCode;
    private String workflowName;
    private String workflowDesc;
    private String version;
    private String createTime;
    private Integer createBy;
    private String updateTime;
    private Integer updateBy;
    private String constructionCode;
    private String constructionName;
    private String status;
}
