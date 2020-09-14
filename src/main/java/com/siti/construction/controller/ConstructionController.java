package com.siti.construction.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siti.common.AutoLog;
import com.siti.common.Result;
import com.siti.common.constant.CommonConstant;
import com.siti.common.vo.LoginUser;
import com.siti.construction.entity.BusinessConstruction;
import com.siti.construction.service.IConstructionService;
import com.siti.system.ctrl.LoginCtrl;
import com.siti.system.login.entity.SysUser;
import com.siti.system.login.service.ISysUserService;
import com.siti.utils.DateUtils;
import com.siti.workflow.service.IWorkflowNodeService;
import com.siti.workflow.vo.WorkflowNodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by 12293 on 2020/8/7.
 */
@Slf4j
@RestController
@RequestMapping("construction")
@Api(tags = "项目列表")
public class ConstructionController {

    @Resource
    IWorkflowNodeService iWorkflowNodeService;
    @Resource
    IConstructionService iConstructionService;
    @Resource
    LoginCtrl loginCtrl;
    @Resource
    ISysUserService iSysUserService;

    /**
     * 分页列表查询
     *
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取项目数据列表", notes = "获取项目数据列表")
    @GetMapping(value = "/list")
    public Result<?> list(Integer status, String year,String constructionName, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          HttpServletRequest req) {
        QueryWrapper<BusinessConstruction> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("construction_code");
        if(constructionName!=null){
            queryWrapper.like("construction_name", constructionName);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        if (year != null && year != "") {
            queryWrapper.like("initial_time", year);
        }
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
     * 分页列表查询
     *
     * @param constructionCode
     * @return
     */
    @ApiOperation(value = "获取一个项目数据", notes = "获取一个项目数据")
    @GetMapping(value = "/getOne")
    public Result<?> getOne(String constructionCode) {
        QueryWrapper<BusinessConstruction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("construction_code", constructionCode);
        BusinessConstruction construction = iConstructionService.getOne(queryWrapper);
        construction.setStartTime(construction.getInitialTime());
        construction.setEndTime( (new Date().before(DateUtils.str2Date2(construction.getFinalTime())))? construction.getFinalTime(): DateUtils.date2Str2(new Date()));
        return Result.ok(construction);
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
        String constructionCode = new StringBuilder(System.currentTimeMillis() / 1000 + "")
                .append("PDJT")
                .append("-").append((int) ((Math.random() * 9 + 1) * 100000)).toString();
        int id = loginCtrl.getLoginUserInfo().getId();
        BusinessConstruction.setConstructionCode(constructionCode);
        SysUser pm = iSysUserService.getById(BusinessConstruction.getPmId());
        SysUser enginPm = iSysUserService.getById(BusinessConstruction.getEnginPmId());
        BusinessConstruction.setEnginPm(enginPm.getRealname());
        BusinessConstruction.setPm(pm.getRealname());
        BusinessConstruction.setUpdateBy(id+"");
        BusinessConstruction.setCreateBy(id+"");
        //根据项目流程号生成流程节点
        try {
            if (BusinessConstruction.getType() != null) {
                List<WorkflowNodeVo> workflowNodeVos = iWorkflowNodeService.allWorkflowNodeConfig(BusinessConstruction.getType());
                iWorkflowNodeService.createRealWorkflow(workflowNodeVos, constructionCode);
            } else {
                return Result.error(500, "新增的项目出错，检查参数");
            }
            iConstructionService.save(BusinessConstruction);
        } catch (Exception e) {
            return Result.error(500, "新增的项目异常");
        }
        return Result.ok("添加并生成流程节点成功！", constructionCode);
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
        if (BusinessConstruction.getConstructionCode() != null) {
            com.siti.construction.entity.BusinessConstruction construction = iConstructionService.getById(BusinessConstruction.getConstructionCode());
            LoginUser loginUserInfo = loginCtrl.getLoginUserInfo();
            if (loginUserInfo.getId() != BusinessConstruction.getPmId()
                    && loginUserInfo.getId() != BusinessConstruction.getEnginPmId()) {
                return Result.ok("当前账户无权限操作项目信息");
            }
        }
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
