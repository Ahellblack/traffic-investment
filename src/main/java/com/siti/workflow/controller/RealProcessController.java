package com.siti.workflow.controller;

import com.siti.common.Result;
import com.siti.workflow.service.IWorkflowRealInfoService;
import com.siti.workflow.vo.WorkflowRealInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 12293 on 2020/8/6.
 */
@RestController
@RequestMapping("process")
public class RealProcessController {

    @Resource
    IWorkflowRealInfoService iWorkflowRealInfoService;

    @ApiOperation(value="主流程，详细信息查看", notes="主流程，详细信息查看")
    @GetMapping("info")
    public Result<?> realProcessInfo(String constructionCode) {

        List<WorkflowRealInfoVo> workflowRealInfoVos = iWorkflowRealInfoService.realProcessInfo(constructionCode);
        return Result.ok(workflowRealInfoVos);
    }


}
