package com.siti.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siti.workflow.entity.Workflow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface WorkflowMapper extends BaseMapper<Workflow> {

    @Select("SELECT * FROM `workflow` where workflow_code =#{workflowCode}")
    Workflow selectByKey(@Param("workflowCode") String workflowCode);
}
