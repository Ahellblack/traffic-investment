package com.siti.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.workflow.entity.*;
import com.siti.workflow.mapper.WorkflowNodeMapper;
import com.siti.workflow.service.IWorkflowNodeService;
import com.siti.workflow.service.IWorkflowRealInfoService;
import com.siti.workflow.service.IWorkflowRealService;
import com.siti.workflow.service.IWorkflowService;
import com.siti.workflow.vo.WorkflowNodeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Solarie on 2020/6/18.
 */
@Service
public class WorkflowNodeServiceImpl extends ServiceImpl<WorkflowNodeMapper, WorkflowNode> implements IWorkflowNodeService {

    @Resource
    WorkflowNodeMapper workflowNodeMapper;

    @Resource
    IWorkflowService iWorkflowService;
    @Resource
    IWorkflowRealService iWorkflowRealService;
    @Resource
    IWorkflowRealInfoService iWorkflowRealInfoService;
    @Override
    public List<WorkflowNodeVo> allWorkflowNodeConfig(String workflowCode) {
        List<WorkflowNodeVo> configNodeByworkflowCode = new ArrayList<>();
        if (workflowCode != "") {
            configNodeByworkflowCode = workflowNodeMapper.findConfigNodeByworkflowCode(workflowCode);
            configNodeByworkflowCode.forEach(data -> {
                WorkflowNodeVo workflowNodeVo = Optional.ofNullable(data).get();
                List<WorkflowTask> configTaskByworkflowNode = workflowNodeMapper
                        .findConfigTaskByworkflowNode(workflowNodeVo.getWorkflowCode(), workflowNodeVo.getNodeCode());
                data.setWorkflowTaskList(configTaskByworkflowNode);
            });
        }
        return configNodeByworkflowCode;
    }

    /**
     * @param workflowNodeVos
     * @param constructionCode 项目编号
     */
    @Transactional
    @Override
    public boolean createRealWorkflow(List<WorkflowNodeVo> workflowNodeVos, String constructionCode) {
        Workflow workflow = new Workflow();
        if (workflowNodeVos.size() > 0 /*&& oConvertUtils.isEmpty(workflowNodeVos.get(0))*/) {

            workflow = iWorkflowService.getByKey(workflowNodeVos.get(0).getWorkflowCode());
        }
        //----------提交主体----------//
        WorkflowReal workflowReal = new WorkflowReal();
        BeanUtils.copyProperties(workflow, workflowReal);
        workflowReal.setConstructionCode(constructionCode);
        String sheetCode = new StringBuilder(System.currentTimeMillis() / 1000 + "").append(workflowReal.getVersion())
                .append("-").append((int)(Math.random() * 9 + 1) * 100000).toString();
        workflowReal.setSheetCode(sheetCode);
        boolean saveW = iWorkflowRealService.save(workflowReal);
        boolean saveWN = true;
        try {
            workflowNodeVos.stream()
                    .filter(data -> data.getWorkflowTaskList().size() != 0)
                    .forEach(nodevo -> {
                        //-------- 添加流程实时表---------//
                        WorkflowRealInfo node = new WorkflowRealInfo();
                        BeanUtils.copyProperties(nodevo, node);
                        node.setSheetCode(sheetCode);
                        node.setConstructionCode(constructionCode);
                        iWorkflowRealInfoService.save(node);
                    });
        }catch (Exception e){
            e.printStackTrace();
            saveWN = false;
        }
        if(saveW && saveWN){
            return true;
        }else{
            return false;
        }
    }


}
