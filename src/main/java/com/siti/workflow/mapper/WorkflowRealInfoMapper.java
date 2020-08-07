package com.siti.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siti.workflow.entity.WorkflowRealInfo;
import com.siti.workflow.vo.WorkflowRealInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface WorkflowRealInfoMapper extends BaseMapper<WorkflowRealInfo> {

    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tworkflow_real wr\n" +
            "\tLEFT JOIN workflow_real_info wri ON wr.sheet_code = wri.sheet_code \n" +
            "WHERE\n" +
            "\twr.sheet_code = #{sheetCode} ")
    List<WorkflowRealInfoVo> getRealWorkflowByConstructionCode(@Param("sheetCode")String sheetCode);
}
