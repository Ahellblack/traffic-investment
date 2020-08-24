package com.siti.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siti.workflow.entity.WorkflowRealInfo;
import com.siti.workflow.entity.WorkflowRealTaskProgress;
import com.siti.workflow.vo.WorkflowRealInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface WorkflowRealInfoMapper extends BaseMapper<WorkflowRealInfo> {

    @Select("SELECT " +
            " wri.*  " +
            "FROM " +
            " ( SELECT * FROM workflow_real WHERE construction_code = #{constructionCode} ORDER BY create_time desc limit 1) wr " +
            " LEFT JOIN workflow_real_info wri ON wr.sheet_code = wri.sheet_code  " +
            " AND wr.type = 1 ")
    List<WorkflowRealInfoVo> getRealWorkflowByConstructionCode(@Param("constructionCode")String constructionCode);

    @Select("<script>SELECT * FROM `workflow_real_task_progress` wrtp " +
            "where construction_code = #{constructionCode}" +
            "<if test = \'nodeCode!= null\'>and node_code = #{nodeCode}</if>" +
            "</script>")
    List<WorkflowRealTaskProgress> realProcessTask(@Param("constructionCode")String constructionCode,@Param("nodeCode") String nodeCode);

    @Select("<script>SELECT * FROM `workflow_real_task_progress` wrtp " +
            "where sheet_code = #{sheetCode}" +
            "<if test = \'nodeCode!= null\'>and node_code = #{nodeCode}</if>" +
            "</script>")
    List<WorkflowRealTaskProgress> realProcessTaskBySheetCode(@Param("sheetCode")String sheetCode,@Param("nodeCode") String nodeCode);

}
