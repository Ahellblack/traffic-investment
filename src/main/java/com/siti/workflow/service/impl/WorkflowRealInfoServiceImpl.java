package com.siti.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.common.vo.LoginUser;
import com.siti.construction.entity.BusinessConstruction;
import com.siti.construction.service.IConstructionService;
import com.siti.utils.DateUtils;
import com.siti.workflow.entity.Workflow;
import com.siti.workflow.entity.WorkflowRealInfo;
import com.siti.workflow.entity.WorkflowRealTaskProgress;
import com.siti.workflow.mapper.WorkflowNodeMapper;
import com.siti.workflow.mapper.WorkflowRealInfoMapper;
import com.siti.workflow.service.IWorkflowRealInfoService;
import com.siti.workflow.service.IWorkflowRealService;
import com.siti.workflow.service.IWorkflowRealTaskProgressService;
import com.siti.workflow.service.IWorkflowService;
import com.siti.workflow.vo.WorkflowRealInfoVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
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
    IConstructionService iConstructionService;
    @Resource
    IWorkflowService iWorkflowService;
    @Resource
    IWorkflowRealService iWorkflowRealService;
    @Resource
    IWorkflowRealTaskProgressService iWorkflowRealTaskProgressService;

    @Override
    public List<WorkflowRealInfoVo> realProcessInfo(String constructionCode) {
        List<WorkflowRealInfoVo> infoVos = workflowRealInfoMapper.getRealWorkflowByConstructionCode(constructionCode);
        if (infoVos != null && (!infoVos.isEmpty())) {
            infoVos.forEach(realinfo -> {

                List<WorkflowRealTaskProgress> workflowRealTaskProgresses = workflowRealInfoMapper.realProcessTaskBySheetCode(realinfo.getSheetCode(), realinfo.getNodeCode());
                realinfo.setTaskProgressList(workflowRealTaskProgresses);
                Integer status = realinfo.getStatus();
                if (status != 1) {
                    Map<String, String> tmap = workflowRealTaskProgresses.stream().filter(data -> data.getFinalTime() != null)
                            // java 8 stream Collectors.toMap 当value为空会报空指针，属于jdk8的bug 解决方式
                            //.collect(Collectors.toMap(WorkflowRealTaskProgress::getFinalTime, WorkflowRealTaskProgress::getFinishTime));
                            .collect(HashMap::new, (m, v) -> m.put(v.getFinalTime(), v.getFinishTime()), HashMap::putAll);
                    /*tmap.forEach((finalTime,finishTime)->{
                        DateUtils.str2Date2(finalTime).after(new Date());  Integer newStatus = 3;
                    });*/
                    for (String finalTime : tmap.keySet()) {
                        //若计划时间未填写 默认状态为0 未开始
                        if (finalTime == null && finalTime == "") {
                            break;
                        }
                        Date finaldate = DateUtils.str2Date2(finalTime);
                        if (finaldate.before(new Date()) ) {
                            // 未完成节点若时间超过当前时间 设置为逾期 #TODO 状态的判断
                            if(tmap.get(finaldate)==null){
                                realinfo.setStatus(3);
                                break;
                            }else if(DateUtils.str2Date2(tmap.get(finaldate)).after(finaldate) ) {
                                realinfo.setStatus(3);
                                break;
                            }
                        }
                    }
                }
            });
            return infoVos;
        }
        return null;
    }

    @Override
    public List<WorkflowRealTaskProgress> realProcessTask(String constructionCode, String nodeCode) {
        List<WorkflowRealTaskProgress> taskProgress = workflowRealInfoMapper.realProcessTask(constructionCode, nodeCode);
        return taskProgress;
    }

    /**
     * @param workflowNodeVos
     */
    @Transactional
    @Override
    public boolean updateRealInfo(List<WorkflowRealInfoVo> workflowNodeVos) {
        //TODO 添加权限
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Workflow workflow = new Workflow();
        if (workflowNodeVos.size() > 0 /*&& oConvertUtils.isEmpty(workflowNodeVos.get(0))*/) {
            workflow = iWorkflowService.getByKey(workflowNodeVos.get(0).getWorkflowCode());
            String constructionCode = workflowNodeVos.get(0).getConstructionCode();
            if (constructionCode == null) {
                return false;
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
                            List<WorkflowRealTaskProgress> workflowTaskList = nodevo.getTaskProgressList();
                            List<Date> el = new ArrayList<>();

                            List<Date> initialel = new ArrayList<>();
                            List<Date> finalel = new ArrayList<>();

                            List<Date> insideel = new ArrayList<>();
                            List<Date> finishel = new ArrayList<>();

                            List<Integer> taskStatusList = new ArrayList<>();
                            if (workflowTaskList != null && workflowTaskList.size() != 0) {
                                for (WorkflowRealTaskProgress task : workflowTaskList) {
                                    WorkflowRealTaskProgress rtask = new WorkflowRealTaskProgress();
                                    if (task.getFinishTime() != null && task.getInsideTime() != null) {
                                        task.setStatus(1);
                                    } else {
                                        task.setStatus(0);
                                    }
                                    //所有task完成状态集合
                                    taskStatusList.add(task.getStatus());
                                    BeanUtils.copyProperties(task, rtask);
                                    iWorkflowRealTaskProgressService.updateById(rtask);
                                    initialel.add(DateUtils.str2Date2(task.getInitialTime()));
                                    finalel.add(DateUtils.str2Date2(task.getFinalTime()));
                                    insideel.add(DateUtils.str2Date2(task.getInsideTime()));
                                    finishel.add(DateUtils.str2Date2(task.getFinishTime()));
                                }
                            }
                            //-------- 编辑流程实时表---------//
                            Integer nodeStatus = 0;
                            if (taskStatusList.size() != 0) {
                                boolean flag1 = taskStatusList.stream().allMatch(data -> data == 1);
                                boolean flag2 = taskStatusList.stream().allMatch(data -> data == 0);
                                if (flag1) {
                                    nodeStatus = 1;
                                } else if (flag2) {
                                    nodeStatus = 0;
                                } else {
                                    nodeStatus = 2;
                                }
                            }
                            nodevo.setStatus(nodeStatus);
                            WorkflowRealInfo node = new WorkflowRealInfo();
                            BeanUtils.copyProperties(nodevo, node);

                            // 取最小及最大时间生成node
                            //去null元素
                            initialel.removeAll(Collections.singleton(null));
                            if (initialel.size() > 0) {
                                //自然排序时间
                                List<Date> collect = initialel.stream().sorted().collect(Collectors.toList());
                                node.setInitialTime(DateUtils.date2Str2(collect.get(0)));
                                //node.setFinalTime(DateUtils.date2Str2(collect.get(collect.size() - 1)));
                            }
                            finalel.removeAll(Collections.singleton(null));
                            if (finalel.size() > 0) {
                                //自然排序时间
                                List<Date> collect = finalel.stream().sorted().collect(Collectors.toList());
                                //node.setInitialTime(DateUtils.date2Str2(collect.get(0)));
                                node.setFinalTime(DateUtils.date2Str2(collect.get(collect.size() - 1)));
                            }
                            insideel.removeAll(Collections.singleton(null));
                            if (insideel.size() > 0) {
                                //自然排序时间
                                List<Date> collect = insideel.stream().sorted().collect(Collectors.toList());
                                node.setInsideTime(DateUtils.date2Str2(collect.get(0)));
                                //node.setFinalTime(DateUtils.date2Str2(collect.get(collect.size() - 1)));
                            }
                            finishel.removeAll(Collections.singleton(null));
                            if (finishel.size() > 0) {
                                //自然排序时间
                                List<Date> collect = finishel.stream().sorted().collect(Collectors.toList());
                                //node.setInitialTime(DateUtils.date2Str2(collect.get(0)));
                                node.setFinishTime(DateUtils.date2Str2(collect.get(collect.size() - 1)));
                            }
                            this.updateById(node);
                        });

                List<WorkflowRealTaskProgress> workflowRealTaskProgresses = realProcessTask(constructionCode, null);
                //根据任务完成情况设置项目状态
                boolean a = workflowRealTaskProgresses.stream().allMatch(data -> data.getStatus() == 1);
                boolean b = workflowRealTaskProgresses.stream().allMatch(data -> data.getStatus() == 0);
                BusinessConstruction construction = iConstructionService.getById(constructionCode);
               /* UpdateWrapper<BusinessConstruction> wrapper = new UpdateWrapper();
                wrapper.set("construction_code",constructionCode);*/
                if (a) {
                    construction.setStatus(2);
                } else if (b) {
                    construction.setStatus(0);
                } else {
                    construction.setStatus(1);
                }
                iConstructionService.updateById(construction);

            } catch (Exception e) {
                e.printStackTrace();
                saveWN = false;
            }
            if (saveWN) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
