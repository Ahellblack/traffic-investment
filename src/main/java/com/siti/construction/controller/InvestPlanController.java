package com.siti.construction.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 12293 on 2020/8/7.
 */
@Slf4j
@RestController
@RequestMapping("annualPlan")
@Api(value = "年度计划")
public class InvestPlanController {

    @Resource
    IInvestPlanService iInvestPlanService;

    /**
     * 分页列表查询
     *
     * @param ym 年月 YYYY-MM
     * @param constructionCode
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取年度计划列表", notes = "获取年度计划列表")
    @GetMapping(value = "/list")
    public Result<?> list(String ym, String constructionCode, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          HttpServletRequest req) {
        QueryWrapper<BusinessInvestPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("construction_code",constructionCode);
        if(ym!=""){
            queryWrapper.eq("ym",ym);
        }
        Page<BusinessInvestPlan> page = new Page<>(pageNo, pageSize);
        IPage<BusinessInvestPlan> pageList = iInvestPlanService.page(page, queryWrapper);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return Result.ok(pageList);
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


    /***
     * 生成某个项目的年度计划
     * @Param constructionCode
     * * */
    @PostMapping("createInvestPlan")
    public Result<?> createInvestPlan(String constructionCode) {
        iInvestPlanService.createInvestPlan(constructionCode);
        return Result.ok("生成成功!");
    }

}
