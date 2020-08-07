package com.siti.construction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.construction.entity.BusinessInvestPlan;
import com.siti.construction.mapper.ConstructionMapper;
import com.siti.construction.mapper.InvestPlanMapper;
import com.siti.construction.service.IInvestPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Solarie on 2020/6/18.
 */
@Service
public class InvestPlanServiceImpl extends ServiceImpl<InvestPlanMapper, BusinessInvestPlan> implements IInvestPlanService {

    @Resource
    InvestPlanMapper investPlanMapper;

}
