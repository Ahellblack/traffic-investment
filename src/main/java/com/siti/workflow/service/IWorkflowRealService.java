package com.siti.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.siti.workflow.entity.WorkflowReal;

import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface IWorkflowRealService extends IService<WorkflowReal> {
    List<WorkflowReal> getBusinessWorkflowReal(String workflowCode);
}
