package com.siti.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.siti.workflow.entity.Workflow;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface IWorkflowService extends IService<Workflow> {
    Workflow getByKey(String workflowCode);
}
