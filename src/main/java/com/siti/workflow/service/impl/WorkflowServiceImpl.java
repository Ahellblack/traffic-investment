package com.siti.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.workflow.entity.Workflow;
import com.siti.workflow.mapper.WorkflowMapper;
import com.siti.workflow.service.IWorkflowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Solarie on 2020/6/18.
 */
@Service
public class WorkflowServiceImpl extends ServiceImpl<WorkflowMapper, Workflow> implements IWorkflowService {

    @Resource
    WorkflowMapper workflowMapper;

    @Override
    public Workflow getByKey(String workflowCode) {
        return workflowMapper.selectByKey(workflowCode);
    }
}
