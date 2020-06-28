package com.siti.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.siti.workflow.entity.Workflow;
import com.siti.workflow.entity.WorkflowNode;
import com.siti.workflow.vo.WorkflowNodeVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface IWorkflowNodeService extends IService<WorkflowNode> {

    /**
     * 获取对应工作流id号的流程节点配置
     * @param workflowCode
     *
     * */
    List<WorkflowNodeVo> allWorkflowNodeConfig(String workflowCode);

}
