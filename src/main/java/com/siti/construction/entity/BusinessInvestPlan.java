package com.siti.construction.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessInvestPlan {

    @ApiModelProperty(value = "id")
    private int id;
    @Excel(name = "工程编号", width = 20)
    @ApiModelProperty(value = "工程编号")
    private String constructionCode;
    @ApiModelProperty(value = "实施月份 YYYY-MM")
    private String ym;
    @Excel(name = "实施项目详情", width = 20)
    @ApiModelProperty(value = "实施项目详情")
    private String content;
    @Excel(name = "计划金额", width = 20)
    @ApiModelProperty(value = "计划金额")
    private String sum;
    @Excel(name = "工作量", width = 20)
    @ApiModelProperty(value = "工作量")
    private String workload;
    private Date createTime;
    private String createBy;
    private java.sql.Timestamp updateTime;
    private String updateBy;
    private String finishTime;
    @Excel(name = "类型", width = 20)
    @ApiModelProperty(value = "类型 1前期 2工程")
    private int type;
    @Excel(name = "描述", width = 20)
    private String description; //描述
}
