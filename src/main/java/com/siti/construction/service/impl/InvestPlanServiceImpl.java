package com.siti.construction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.construction.entity.BusinessConstruction;
import com.siti.construction.entity.BusinessInvestPlan;
import com.siti.construction.mapper.InvestPlanMapper;
import com.siti.construction.service.IConstructionService;
import com.siti.construction.service.IInvestPlanService;
import com.siti.utils.DateUtils;
import com.siti.workflow.entity.WorkflowRealTaskProgress;
import com.siti.workflow.service.IWorkflowRealInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Solarie on 2020/6/18.
 */
@Service
public class InvestPlanServiceImpl extends ServiceImpl<InvestPlanMapper, BusinessInvestPlan> implements IInvestPlanService {

    private final int TYPE_PRE = 1; //前期
    private final int TYPE_ENG = 2; //工程

    @Resource
    InvestPlanMapper investPlanMapper;

    @Resource
    IWorkflowRealInfoService iWorkflowRealInfoService;

    @Resource
    IConstructionService iConstructionService;

    @Override
    public boolean createInvestPlan(String constructionCode) {
        //获取项目的计划开始和结束时间
        QueryWrapper<BusinessConstruction> queryWrapper = new QueryWrapper();
        queryWrapper.eq("construction_code", constructionCode);
        BusinessConstruction construction = iConstructionService.getOne(queryWrapper);
        if (construction != null) {
            List<WorkflowRealTaskProgress> workflowRealTaskProgresses = iWorkflowRealInfoService.realProcessTask(constructionCode, null);
            List<WorkflowRealTaskProgress> progress1 = workflowRealTaskProgresses.stream()
                    .filter(data -> data.getType() == TYPE_PRE).collect(Collectors.toList());
            List<WorkflowRealTaskProgress> progress2 = workflowRealTaskProgresses.stream()
                    .filter(data -> data.getType() == TYPE_ENG).collect(Collectors.toList());
            contentMapWihtProgress(progress1, construction);
            contentMapWihtProgress(progress2, construction);
            return true;
        }
        return false;
    }

    public Map<String, Set<String>> contentMapWihtProgress(List<WorkflowRealTaskProgress> workflowRealTaskProgresses, BusinessConstruction construction) {
        if (workflowRealTaskProgresses.size() > 0 && workflowRealTaskProgresses.get(0).getConstructionCode() != "") {
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            Map<String, Set<String>> map = new HashMap<>();
            workflowRealTaskProgresses.stream().filter(data -> Optional.ofNullable(data.getTaskName()).get() != "").forEach(data -> {
                if(data.getInitialTime()!=null && data.getFinalTime()!=null) {
                    Date finalTime = DateUtils.str2Date2(data.getFinalTime());
                    Date initialTime = DateUtils.str2Date2(data.getInitialTime());
                    String name = data.getTaskName();
                    Set<String> stringSet = Optional.ofNullable(map.get(DateUtils.formatDateYearMonth(initialTime)))
                            .orElse(new HashSet<>());
                    stringSet.add(name);
                    String ym = DateUtils.formatDateYearMonth(initialTime);
                    map.put(ym, stringSet);
                    //起始时间与结束时间年月不同
                    if (DateUtils.formatDateYearMonth(finalTime) != DateUtils.formatDateYearMonth(initialTime)
                           ) {
                        stringSet = map.get(DateUtils.formatDateYearMonth(finalTime));
                        if(stringSet==null){
                            stringSet = new HashSet<>();
                        }
                        stringSet.add(name);
                        map.put(DateUtils.formatDateYearMonth(finalTime), stringSet);
                    }
                }
            });
            //生成中间月份 #TODO 是否删除多余的月份。
            List<String> list = innerYearMonth(construction.getInitialTime(),construction.getFinalTime());
            list.removeAll(map.keySet());
            list.forEach(ym -> {
                HashSet<String> objects = new HashSet<>();
                objects.add("无");
                map.put(ym, objects);
            });
            //录入年度计划配置
            map.forEach((key, value) -> {
                String content = StringUtils.join(value.toArray(), ",");
                String ccode0 = workflowRealTaskProgresses.get(0).getConstructionCode();
                Integer type0 = workflowRealTaskProgresses.get(0).getType();
                BusinessInvestPlan plan = BusinessInvestPlan.builder()
                        .ym(key).content(content)
                        .constructionCode(ccode0)
                        .createTime(new Timestamp(System.currentTimeMillis()))
                        .type(type0).build();
                BusinessInvestPlan findPlan = investPlanMapper.getByYmAndConstructionCode(key,ccode0,type0);
                if(findPlan!=null){
                    investPlanMapper.updateByYm(plan);
                }else{
                    investPlanMapper.insert(plan);
                }

            });
            //System.out.println(map);
            return map;
        }
        return new HashMap<>();
    }


    public List<String> innerYearMonth(String y1, String y2) {
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM").parse(y1);
            Date endDate = new SimpleDateFormat("yyyy-MM").parse(y2);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            // 获取开始年份和开始月份
            int startYear = calendar.get(Calendar.YEAR);
            int startMonth = calendar.get(Calendar.MONTH);
            // 获取结束年份和结束月份
            calendar.setTime(endDate);
            int endYear = calendar.get(Calendar.YEAR);
            int endMonth = calendar.get(Calendar.MONTH);
            //
            List<String> list = new ArrayList<String>();
            for (int i = startYear; i <= endYear; i++) {
                String date = "";
                if (startYear == endYear) {
                    for (int j = startMonth; j <= endMonth; j++) {
                        if (j < 9) {
                            date = i + "-0" + (j + 1);
                        } else {
                            date = i + "-" + (j + 1);
                        }
                        list.add(date);
                    }

                } else {
                    if (i == startYear) {
                        for (int j = startMonth; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    } else if (i == endYear) {
                        for (int j = 0; j <= endMonth; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    } else {
                        for (int j = 0; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    }

                }

            }

            // 所有的月份已经准备好
            //System.out.println(list);
           /* for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }*/
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }
}
