package com.siti.construction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.construction.entity.BusinessInvestPlan;
import com.siti.construction.mapper.InvestPlanMapper;
import com.siti.construction.service.IInvestPlanService;
import com.siti.utils.DateUtils;
import com.siti.workflow.entity.WorkflowRealTaskProgress;
import com.siti.workflow.service.IWorkflowRealInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Solarie on 2020/6/18.
 */
@Service
public class InvestPlanServiceImpl extends ServiceImpl<InvestPlanMapper, BusinessInvestPlan> implements IInvestPlanService {

    @Resource
    InvestPlanMapper investPlanMapper;

    @Resource
    IWorkflowRealInfoService iWorkflowRealInfoService;

    @Override
    public void createInvestPlan(String constructionCode) {
        List<WorkflowRealTaskProgress> workflowRealTaskProgresses = iWorkflowRealInfoService.realProcessTask(constructionCode, null);
        Map<String, Set<String>> map = new HashMap<>();
        workflowRealTaskProgresses.stream().filter(data -> Optional.ofNullable(data.getTaskName()).get() != "").forEach(data -> {

            Date finalTime = data.getFinalTime();
            Date initialTime = data.getInitialTime();
            String name = data.getTaskName();
            Set<String> stringSet = Optional.ofNullable(map.get(DateUtils.formatDateYearMonth(initialTime)))
                    .orElse(new HashSet<>());
            stringSet.add(name);
            map.put(DateUtils.formatDateYearMonth(initialTime), stringSet);
            //起始时间与结束时间年月不同
            if (DateUtils.getMonth(finalTime) != DateUtils.getMonth(initialTime)
                    && DateUtils.getYear(finalTime) != DateUtils.getYear(initialTime)) {
                stringSet = map.get(DateUtils.formatDateYearMonth(finalTime));
                stringSet.add(name);
                map.put(DateUtils.formatDateYearMonth(finalTime), stringSet);
            }
            System.out.println(map);
        });
    }
}
