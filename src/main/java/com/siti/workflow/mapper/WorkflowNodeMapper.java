package com.siti.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siti.workflow.entity.WorkflowNode;
import com.siti.workflow.vo.WorkflowNodeVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface WorkflowNodeMapper extends BaseMapper<WorkflowNode>{

    @Select("SELECT * FROM `workflow_node` wn left join workflow w on wn.workflow_id = w.id " +
            "where wn.workflow_id = #{workflowCode}")
    List<WorkflowNodeVo> findConfigNodeByworkflowCode(String workflowCode);
}
