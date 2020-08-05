package com.siti.workflow.controller;

import com.siti.common.Result;
import com.siti.workflow.service.IWorkflowNodeService;
import com.siti.workflow.service.IWorkflowService;
import com.siti.workflow.vo.WorkflowNodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
@Api(tags = "流程节点配置")
@Slf4j
@RestController
@RequestMapping("workflow")
public class WorkflowController {

    @Resource
    IWorkflowService iWorkflowService;

    @Resource
    IWorkflowNodeService iWorkflowNodeService;

    @ApiOperation(value="主流程，根据流程号获取节点配置信息", notes="主流程，根据流程号获取节点配置信息")
    @RequestMapping("allWorkflowNodeConfig")
    public Result<?> allWorkflowNode(String workflowCode) {

        List<WorkflowNodeVo> workflowNodeVos = iWorkflowNodeService.allWorkflowNodeConfig(workflowCode);
        return Result.ok(workflowNodeVos);
    }



    @ApiOperation(value="生成数据表workflow_reaList<WorkflowNodeVo> workflowNodeVosl,workflow_real_info,workflow_real_task_progress", notes="workflow_real,workflow_real_info,workflow_real_task_progress")
    @RequestMapping("createRealWorkflow")
    public Result<?> createRealWorkflow(@RequestBody List<WorkflowNodeVo> workflowNodeVos,String constructionCode) {

        boolean flag = iWorkflowNodeService.createRealWorkflow(workflowNodeVos,constructionCode);
        if(flag)
        return Result.ok(workflowNodeVos);
        return Result.error("添加失败");
    }





}
