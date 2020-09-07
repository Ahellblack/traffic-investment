package com.siti.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.workflow.entity.WorkflowReal;
import com.siti.workflow.mapper.WorkflowRealMapper;
import com.siti.workflow.service.IWorkflowRealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Solarie on 2020/6/18.
 */
@Service
public class WorkflowRealServiceImpl extends ServiceImpl<WorkflowRealMapper, WorkflowReal> implements IWorkflowRealService {

    @Resource
    WorkflowRealMapper workflowRealMapper;

    public int insert(WorkflowReal var1){

        return workflowRealMapper.insert(var1);
    }

}
