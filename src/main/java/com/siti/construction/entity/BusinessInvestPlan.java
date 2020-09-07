package com.siti.construction.entity;

import com.siti.common.Dict;
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

    @Dict(dicCode = "type") // 自定义字典注解 通过code找到唯一的字典值
    @Excel(name = "类型", width = 20, replace={"前期_1","工程_2"})
    @ApiModelProperty(value = "类型 1前期 2工程")
    private int type;

    @ApiModelProperty(value = "工程编号")
    private String constructionCode;

    @Excel(name = "实施月份", width = 20)
    @ApiModelProperty(value = "实施月份 YYYY-MM")
    private String ym;

    @Excel(name = "实施项目详情", width = 40)
    @ApiModelProperty(value = "实施项目详情")
    private String content;

    @Excel(name = "描述", width = 40)
    @ApiModelProperty(value = "描述")
    private String description; //描述

    @Excel(name = "资金", width = 20, groupName = "投资金额")
    @ApiModelProperty(value = "资金")
    private String sum;

    @Excel(name = "投资", width = 20, groupName = "投资金额")
    @ApiModelProperty(value = "投资")
    private String workload;



    private Date createTime;

    private String createBy;

    private java.sql.Timestamp updateTime;

    private String updateBy;

    private String finishTime;


}
