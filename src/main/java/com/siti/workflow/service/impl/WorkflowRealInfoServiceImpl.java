package com.siti.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.workflow.entity.WorkflowRealInfo;
import com.siti.workflow.mapper.WorkflowRealInfoMapper;
import com.siti.workflow.service.IWorkflowRealInfoService;
import com.siti.workflow.vo.WorkflowRealInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Solarie on 2020/6/18.
 */
@Service
public class WorkflowRealInfoServiceImpl extends ServiceImpl<WorkflowRealInfoMapper, WorkflowRealInfo> implements IWorkflowRealInfoService {

    @Resource
    WorkflowRealInfoMapper workflowRealInfoMapper;

    @Override
    public List<WorkflowRealInfoVo> realProcessInfo(String constructionCode) {
        List<WorkflowRealInfoVo> infoVos = workflowRealInfoMapper.getRealWorkflowByConstructionCode(constructionCode);

        //#TODO 对 infoVos添加任务信息
        return infoVos;
    }
}
