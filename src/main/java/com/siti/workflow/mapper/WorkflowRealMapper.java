package com.siti.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siti.workflow.entity.WorkflowReal;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface WorkflowRealMapper extends BaseMapper<WorkflowReal> {
    @Select("")
    List<WorkflowReal> getBusinessWorkflowReal(String workflowCode);
}
