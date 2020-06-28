package com.siti.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.system.entity.SysDepartRole;
import com.siti.system.mapper.SysDepartRoleMapper;
import com.siti.system.service.ISysDepartRoleService;
import com.siti.workflow.entity.Workflow;
import com.siti.workflow.mapper.WorkflowMapper;
import com.siti.workflow.service.IWorkflowService;
import org.springframework.stereotype.Service;

/**
 * Created by Solarie on 2020/6/18.
 */
@Service
public class WorkflowServiceImpl extends ServiceImpl<WorkflowMapper, Workflow> implements IWorkflowService {
}
