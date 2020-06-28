package com.siti.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.workflow.entity.WorkflowNode;
import com.siti.workflow.mapper.WorkflowNodeMapper;
import com.siti.workflow.service.IWorkflowNodeService;
import com.siti.workflow.vo.WorkflowNodeVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
@Service
public class WorkflowNodeServiceImpl extends ServiceImpl<WorkflowNodeMapper, WorkflowNode> implements IWorkflowNodeService {

    @Resource
    WorkflowNodeMapper workflowNodeMapper;

    @Override
    public List<WorkflowNodeVo> allWorkflowNodeConfig(String workflowCode) {

        return workflowNodeMapper.findConfigNodeByworkflowCode(workflowCode);
    }
}
