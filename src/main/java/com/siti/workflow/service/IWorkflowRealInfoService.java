package com.siti.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.siti.workflow.entity.WorkflowRealInfo;
import com.siti.workflow.vo.WorkflowRealInfoVo;

import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface IWorkflowRealInfoService extends IService<WorkflowRealInfo> {

    List<WorkflowRealInfoVo> realProcessInfo(String constructionCode);

}