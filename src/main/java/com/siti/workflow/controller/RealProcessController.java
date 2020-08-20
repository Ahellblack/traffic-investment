package com.siti.workflow.controller;

import com.siti.common.Result;
import com.siti.workflow.entity.WorkflowRealTaskProgress;
import com.siti.workflow.service.IWorkflowRealInfoService;
import com.siti.workflow.vo.WorkflowNodeVo;
import com.siti.workflow.vo.WorkflowRealInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 12293 on 2020/8/6.
 */
@RestController
@RequestMapping("process")
@Api(tags = "工程流程")
public class RealProcessController {

    @Resource
    IWorkflowRealInfoService iWorkflowRealInfoService;

    @ApiOperation(value="主流程，详细信息查看", notes="主流程，详细信息查看")
    @GetMapping("info")
    public Result<?> realProcessInfo(String constructionCode) {
        List<WorkflowRealInfoVo> workflowRealInfoVos = iWorkflowRealInfoService.realProcessInfo(constructionCode);
        return Result.ok(workflowRealInfoVos);
    }

    @ApiOperation(value="项目任务详细信息查看", notes="项目任务详细信息查看")
    @GetMapping("task")
    public Result<?> realProcessTask(String constructionCode,String nodeCode) {

        List<WorkflowRealTaskProgress> workflowRealTaskProgress = iWorkflowRealInfoService.realProcessTask(constructionCode,nodeCode);
        return Result.ok(workflowRealTaskProgress);
    }

    @ApiOperation(value="修改数据表workflow_reaList<WorkflowNodeVo> workflowNodeVosl,workflow_real_info,workflow_real_task_progress", notes="workflow_real,workflow_real_info,workflow_real_task_progress")
    @PostMapping("updateRealInfo")
    public Result<?> updateRealInfo(@RequestBody List<WorkflowNodeVo> workflowNodeVos) {
        boolean flag = iWorkflowRealInfoService.updateRealInfo(workflowNodeVos);
        if(flag)
            return Result.ok(workflowNodeVos);
        return Result.error("添加失败");
    }




}
