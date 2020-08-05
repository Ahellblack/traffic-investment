package com.siti.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siti.workflow.entity.WorkflowNode;
import com.siti.workflow.entity.WorkflowTask;
import com.siti.workflow.vo.WorkflowNodeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface WorkflowNodeMapper extends BaseMapper<WorkflowNode>{

    @Select("SELECT * FROM `workflow_node` wn left join workflow w on wn.workflow_code = w.workflow_code " +
            "where wn.workflow_code = #{workflowCode}")
    List<WorkflowNodeVo> findConfigNodeByworkflowCode(@Param("workflowCode") String workflowCode);

    @Select("SELECT * FROM workflow_task where workflow_code = #{workflowCode} and node_code = #{workflowNode}  ")
    List<WorkflowTask> findConfigTaskByworkflowNode(@Param("workflowCode") String workflowCode,@Param("workflowNode")String workflowNode);
}
