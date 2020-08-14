package com.siti.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.common.vo.LoginUser;
import com.siti.workflow.entity.Workflow;
import com.siti.workflow.entity.WorkflowRealInfo;
import com.siti.workflow.entity.WorkflowRealTaskProgress;
import com.siti.workflow.mapper.WorkflowNodeMapper;
import com.siti.workflow.mapper.WorkflowRealInfoMapper;
import com.siti.workflow.service.IWorkflowRealInfoService;
import com.siti.workflow.service.IWorkflowRealService;
import com.siti.workflow.service.IWorkflowRealTaskProgressService;
import com.siti.workflow.service.IWorkflowService;
import com.siti.workflow.vo.WorkflowNodeVo;
import com.siti.workflow.vo.WorkflowRealInfoVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Solarie on 2020/6/18.
 */
@Service
public class WorkflowRealInfoServiceImpl extends ServiceImpl<WorkflowRealInfoMapper, WorkflowRealInfo> implements IWorkflowRealInfoService {

    @Resource
    WorkflowRealInfoMapper workflowRealInfoMapper;
    @Resource
    WorkflowNodeMapper workflowNodeMapper;

    @Resource
    IWorkflowService iWorkflowService;
    @Resource
    IWorkflowRealService iWorkflowRealService;
    @Resource
    IWorkflowRealTaskProgressService iWorkflowRealTaskProgressService;

    @Override
    public List<WorkflowRealInfoVo> realProcessInfo(String constructionCode) {
        List<WorkflowRealInfoVo> infoVos = workflowRealInfoMapper.getRealWorkflowByConstructionCode(constructionCode);

        //#TODO 对 infoVos添加任务信息
        return infoVos;
    }

    @Override
    public List<WorkflowRealTaskProgress> realProcessTask(String constructionCode, Integer nodeCode) {
        List<WorkflowRealTaskProgress> taskProgress = workflowRealInfoMapper.realProcessTask(constructionCode, nodeCode);
        return taskProgress;
    }

    /**
     * @param workflowNodeVos
     */
    @Transactional
    @Override
    public boolean updateRealInfo(List<WorkflowNodeVo> workflowNodeVos) {
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Workflow workflow = new Workflow();
        if (workflowNodeVos.size() > 0 /*&& oConvertUtils.isEmpty(workflowNodeVos.get(0))*/) {
            workflow = iWorkflowService.getByKey(workflowNodeVos.get(0).getWorkflowCode());
        }
        //----------提交主体----------//
        /*WorkflowReal workflowReal = WorkflowReal.builder().createBy(user.getId()).updateBy(user.getId())
                .createTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")))
                .updateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"))).build();
        BeanUtils.copyProperties(workflow, workflowReal);

        String sheetCode = new StringBuilder(System.currentTimeMillis() / 1000 + "").append(workflowReal.getVersion())
                .append("-").append((int) ((Math.random() * 9 + 1) * 100000)).toString();
        workflowReal.setSheetCode(sheetCode);

        boolean saveW = iWorkflowRealService.updateById(workflowReal);*/
        boolean saveWN = true;
        try {
            workflowNodeVos.stream()
                    //.filter(data -> data.getWorkflowTaskList().size() != 0)
                    .forEach(nodevo -> {

                        //----------编辑任务明细表-----------//
                        List<WorkflowRealTaskProgress> workflowTaskList = nodevo.getWorkflowTaskList();
                        List<Date> el = new ArrayList<>();
                        for (WorkflowRealTaskProgress task : workflowTaskList) {
                            WorkflowRealTaskProgress rtask = new WorkflowRealTaskProgress();
                            BeanUtils.copyProperties(task, rtask);
                            iWorkflowRealTaskProgressService.updateById(rtask);
                            el.add(task.getInitialTime());
                            el.add(task.getFinalTime());
                        }

                        //-------- 编辑流程实时表---------//
                        WorkflowRealInfo node = new WorkflowRealInfo();
                        BeanUtils.copyProperties(nodevo, node);
                        //自然排序时间 取最小及最大时间生成node
                        el.removeAll(Collections.singleton(null));
                        if (el.size() > 0) {
                            List<Date> collect = el.stream().sorted().collect(Collectors.toList());
                            node.setInitialTime(new Timestamp(collect.get(0).getTime()));
                            node.setFinalTime(new Timestamp(collect.get(collect.size() - 1).getTime()));
                        }
                        this.updateById(node);
                    });
        } catch (Exception e) {
            e.printStackTrace();
            saveWN = false;
        }
        if (saveWN) {
            return true;
        } else {
            return false;
        }
    }
}
