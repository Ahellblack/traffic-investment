package com.siti.construction.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.siti.common.AutoLog;
import com.siti.common.Result;
import com.siti.common.constant.CommonConstant;
import com.siti.construction.entity.BusinessInvestPlan;
import com.siti.construction.service.IInvestPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 12293 on 2020/8/7.
 */
@Slf4j
@RestController
@RequestMapping("annualPlan")
@Api(tags = "年度计划")
public class InvestPlanController {

    @Resource
    IInvestPlanService iInvestPlanService;


    /**
     * 分页列表查询
     *
     * @param year               年月 YYYY-MM
     * @param constructionCode
     * @return
     */
    @ApiOperation(value = "获取年度计划列表", notes = "获取年度计划列表")
    @GetMapping(value = "/list")
    public Result<?> list(String year, String constructionCode,String type) {
        QueryWrapper<BusinessInvestPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("construction_code", constructionCode)
                .orderByAsc("ym")
                .orderByAsc("type");
        if (year != "" && year !=null) {
            queryWrapper.like("ym", year);
        }
        if (type != "" && type !=null) {
            queryWrapper.like("type", type);
        }
        List<BusinessInvestPlan> pageList = iInvestPlanService.list(queryWrapper);
        return Result.ok(pageList);
    }

    /***
     * 生成某个项目的年度计划
     * @Param constructionCode
     * * */
    @AutoLog(value = "生成某个项目的年度计划")
    @PostMapping("createInvestPlan")
    @ApiOperation(value = "生成某个项目的年度计划", notes = "生成某个项目的年度计划")
    public Result<?> createInvestPlan(String constructionCode) {
        boolean investPlan = iInvestPlanService.createInvestPlan(constructionCode);
        if (investPlan) {
            return Result.ok("生成成功!");
        }
        return Result.error("生成失败，检查参数");
    }

    /**
     * 编辑
     *
     * @param BusinessInvestPlan
     * @return
     */
    @PutMapping(value = "/editList")
    @ApiOperation(value = "批量编辑年度计划表", notes = "批量编辑年度计划表")
    @AutoLog(value = "批量编辑年度计划表", operateType = CommonConstant.OPERATE_TYPE_3)
    public Result<?> editList(@RequestBody List<BusinessInvestPlan> BusinessInvestPlan) {
        BusinessInvestPlan.forEach(data->{
            iInvestPlanService.updateById(data);
        });
        return Result.ok("更新成功！");
    }

    /**
     * 添加
     *
     * @param BusinessInvestPlan
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加年度计划表")
    @ApiOperation(value = "添加年度计划表", notes = "添加年度计划表")
    public Result<?> add(@RequestBody BusinessInvestPlan BusinessInvestPlan) {
        iInvestPlanService.save(BusinessInvestPlan);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param BusinessInvestPlan
     * @return
     */
    @PutMapping(value = "/edit")
    @ApiOperation(value = "编辑年度计划表", notes = "编辑年度计划表")
    @AutoLog(value = "编辑年度计划表", operateType = CommonConstant.OPERATE_TYPE_3)
    public Result<?> edit(@RequestBody BusinessInvestPlan BusinessInvestPlan) {
        iInvestPlanService.updateById(BusinessInvestPlan);
        return Result.ok("更新成功！");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "删除年度计划表")
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "通过ID删除年度计划表", notes = "通过ID删除年度计划表")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        iInvestPlanService.removeById(id);
        return Result.ok("删除成功!");
    }




}
