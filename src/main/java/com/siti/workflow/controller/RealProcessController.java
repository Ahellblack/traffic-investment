package com.siti.workflow.controller;

import com.siti.workflow.service.IWorkflowRealInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 12293 on 2020/8/6.
 */
@RestController
@RequestMapping("process")
public class RealProcessController {

    @Resource
    IWorkflowRealInfoService iWorkflowRealInfoService;



}
