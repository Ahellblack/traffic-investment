package com.siti.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
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

    @TableId(value = "sheet_code")
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
    private int status;
    @ApiModelProperty(value = "类型 1前期 2工程")
    private int type;
    private String relaTableName;
    private long relevanceId;
}
