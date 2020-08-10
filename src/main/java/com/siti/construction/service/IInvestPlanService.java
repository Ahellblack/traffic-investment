package com.siti.construction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.siti.construction.entity.BusinessInvestPlan;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface IInvestPlanService extends IService<BusinessInvestPlan> {
    void createInvestPlan(String constructionCode);
}
