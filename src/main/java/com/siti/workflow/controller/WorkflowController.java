package com.siti.workflow.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.siti.common.Result;
import com.siti.workflow.entity.Workflow;
import com.siti.workflow.service.IWorkflowNodeService;
import com.siti.workflow.service.IWorkflowService;
import com.siti.workflow.vo.WorkflowNodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("allWorkflowNodeConfig")
    public Result<?> allWorkflowNode(String workflowCode) {

        List<WorkflowNodeVo> workflowNodeVos = iWorkflowNodeService.allWorkflowNodeConfig(workflowCode);
        return Result.ok(workflowNodeVos);
    }

    @ApiOperation(value="全部流程查询", notes="主流程，根据流程号获取节点配置信息")
    @GetMapping("allWorkflowConfig")
    public Result<?> allWorkflow() {
        QueryWrapper<Workflow> queryWrapper = new QueryWrapper();
        queryWrapper.eq("type",1);
        List<Workflow> workflow = iWorkflowService.list(queryWrapper);
        return Result.ok(workflow);
    }



    @ApiOperation(value="生成数据表workflow_reaList<WorkflowNodeVo> workflowNodeVosl,workflow_real_info,workflow_real_task_progress", notes="workflow_real,workflow_real_info,workflow_real_task_progress")
    @PostMapping("createRealWorkflow")
    public Result<?> createRealWorkflow(@RequestBody List<WorkflowNodeVo> workflowNodeVos,String constructionCode) {
        if(constructionCode==null || constructionCode == ""){
            return Result.error("添加失败,项目编号未添加");
        }
        boolean flag = iWorkflowNodeService.createRealWorkflow(workflowNodeVos,constructionCode);
        if(flag)
        return Result.ok(workflowNodeVos);
        return Result.error("添加失败，生成流程单异常");
    }

}
