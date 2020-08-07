package com.siti.construction.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siti.common.AutoLog;
import com.siti.common.Result;
import com.siti.common.constant.CommonConstant;
import com.siti.construction.entity.BusinessConstruction;
import com.siti.construction.service.IConstructionService;
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
@RequestMapping("construction")
@Api(value = "项目列表")
public class ConstructionController {

    @Resource
    IConstructionService iConstructionService;

    /**
     * 分页列表查询
     *
     * @param BusinessConstruction
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取项目数据列表", notes = "获取项目数据列表")
    @GetMapping(value = "/list")
    public Result<?> list(BusinessConstruction BusinessConstruction, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          HttpServletRequest req) {
        QueryWrapper<BusinessConstruction> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("construction_code");
        Page<BusinessConstruction> page = new Page<>(pageNo, pageSize);
        //List<BusinessConstruction> list = iConstructionService.list(queryWrapper);
        IPage<BusinessConstruction> pageList = iConstructionService.page(page, queryWrapper);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param BusinessConstruction
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加项目信息")
    @ApiOperation(value = "添加项目信息", notes = "添加项目信息")
    public Result<?> add(@RequestBody BusinessConstruction BusinessConstruction) {
        iConstructionService.save(BusinessConstruction);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param BusinessConstruction
     * @return
     */
    @PutMapping(value = "/edit")
    @ApiOperation(value = "编辑项目信息", notes = "编辑项目信息")
    @AutoLog(value = "编辑项目信息", operateType = CommonConstant.OPERATE_TYPE_3)
    public Result<?> edit(@RequestBody BusinessConstruction BusinessConstruction) {
        iConstructionService.updateById(BusinessConstruction);
        return Result.ok("更新成功！");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "删除项目信息")
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "通过ID删除项目信息", notes = "通过ID删除项目信息")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        iConstructionService.removeById(id);
        return Result.ok("删除成功!");
    }


}
