package com.siti.workflow.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.siti.bussiness.entity.*;
import com.siti.common.BussiEntityConfig;
import com.siti.common.vo.LoginUser;
import com.siti.system.ctrl.LoginCtrl;
import com.siti.utils.DateUtils;
import com.siti.utils.SnowIdUtil;
import com.siti.utils.SpringBootBeanUtil;
import com.siti.workflow.entity.Workflow;
import com.siti.workflow.entity.WorkflowNode;
import com.siti.workflow.entity.WorkflowReal;
import com.siti.workflow.entity.WorkflowRealInfo;
import com.siti.workflow.service.IWorkflowNodeService;
import com.siti.workflow.service.IWorkflowRealInfoService;
import com.siti.workflow.service.IWorkflowRealService;
import com.siti.workflow.service.IWorkflowService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by 12293 on 2020/8/18.
 */
@RestController
@RequestMapping("business/process")
public class BusinessProcessController {

    @Resource
    IWorkflowRealInfoService iWorkflowRealInfoService;
    @Resource
    IWorkflowService iWorkflowService;
    @Resource
    IWorkflowRealService iWorkflowRealService;
    @Resource
    IWorkflowNodeService iWorkflowNodeService;
    @Resource
    LoginCtrl loginCtrl;

    /**
     * 流程初始化
     */
    @PostMapping("init")
    public void initBusiProcess(String workflowCode, @RequestBody Object t) {
        LoginUser user = loginCtrl.getLoginUserInfo();
        Workflow workflow = iWorkflowService.getById(workflowCode);
        if (workflow != null) {
            String relaTableName = workflow.getRelaTableName();
            //relaTableName 做比对，将 obj插入 relaTableName 表， id 记录在workflow_real
            try {
                //从ApplicationContext中取出已创建好的的对象
                //不可直接反射创建serviceimpi对象，因为反射创建出来的对象无法实例化dao接口
                ApplicationContext applicationContext = SpringBootBeanUtil.getApplicationContext();
                //反射创建serviceimpi实体对象，和实体类
                Class<?> ServiceImplType = Class.forName("com.siti.bussiness.service.impl." + relaTableName + "ServiceImpl");
                Class<?> entityType = Class.forName("com.siti.bussiness.entity." + relaTableName);

                //反射设置方法参数。
                Method method = ServiceImplType.getMethod("insert", entityType);

                //JSON对象转换成Java对象
                //先转成JSON对象
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(t);
                long id = new SnowIdUtil(3, 1).nextId();
                if (relaTableName.equals(BussiEntityConfig.BUSINESS_CONTRACT)) {
                    BusinessContract bussinessEntity = JSONObject.toJavaObject(jsonObject, BusinessContract.class);
                    bussinessEntity.setId(id);
                    //在ApplicationContext中根据class取出已实例化的bean           //将获得的obj转译成实体对象
                    if (bussinessEntity != null) {
                        method.invoke(applicationContext.getBean(ServiceImplType), bussinessEntity);
                    }
                } else if (relaTableName.equals(BussiEntityConfig.BUSINESS_TENDER_APPLY)) {
                    BusinessTenderApply bussinessEntity = JSONObject.toJavaObject(jsonObject, BusinessTenderApply.class);
                    bussinessEntity.setId(id);
                    //在ApplicationContext中根据class取出已实例化的bean           //将获得的obj转译成实体对象
                    if (bussinessEntity != null) {
                        method.invoke(applicationContext.getBean(ServiceImplType), bussinessEntity);
                    }
                } else if (relaTableName.equals(BussiEntityConfig.BUSINESS_TENDER_APPROVAL)) {
                    BusinessTenderApproval bussinessEntity = JSONObject.toJavaObject(jsonObject, BusinessTenderApproval.class);
                    bussinessEntity.setId(id);
                    //在ApplicationContext中根据class取出已实例化的bean           //将获得的obj转译成实体对象
                    if (bussinessEntity != null) {
                        method.invoke(applicationContext.getBean(ServiceImplType), bussinessEntity);
                    }
                } else if (relaTableName.equals(BussiEntityConfig.BUSINESS_TENDER_CHOOSE)) {

                    BusinessTenderChoose bussinessEntity = JSONObject.toJavaObject(jsonObject, BusinessTenderChoose.class);
                    bussinessEntity.setId(id);
                    //在ApplicationContext中根据class取出已实例化的bean           //将获得的obj转译成实体对象
                    if (bussinessEntity != null) {
                        method.invoke(applicationContext.getBean(ServiceImplType), bussinessEntity);
                    }
                } else if (relaTableName.equals(BussiEntityConfig.BUSINESS_TENDER_PLAN)) {
                    BusinessTenderPlan bussinessEntity = JSONObject.toJavaObject(jsonObject, BusinessTenderPlan.class);
                    bussinessEntity.setId(id);
                    //在ApplicationContext中根据class取出已实例化的bean           //将获得的obj转译成实体对象
                    if (bussinessEntity != null) {
                        method.invoke(applicationContext.getBean(ServiceImplType), bussinessEntity);
                    }
                }
                String sheetCode = new StringBuilder(System.currentTimeMillis() / 1000 + "").append(workflow.getVersion())
                        .append("-").append((int) ((Math.random() * 9 + 1) * 100000)).toString();

                //----------提交主体----------//
                WorkflowReal workflowReal = WorkflowReal.builder().createBy(user.getId()).updateBy(user.getId())
                        .createTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")))
                        .updateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")))
                        .relevanceId(id).sheetCode(sheetCode).build();
                BeanUtils.copyProperties(workflow, workflowReal);
                boolean saveW = iWorkflowRealService.save(workflowReal);

                //TODO 配置数据库业务表节点
                QueryWrapper<WorkflowNode> wrapper = new QueryWrapper();
                wrapper.eq("workflow_code",workflowReal.getWorkflowCode()).orderByAsc("sort");
                WorkflowNode workflowNode = iWorkflowNodeService.getOne(wrapper);
                WorkflowRealInfo workflowRealInfo = new WorkflowRealInfo();
                BeanUtils.copyProperties(workflowNode,workflowRealInfo);
                workflowRealInfo.setSheetCode(sheetCode);workflowRealInfo.setInsideTime(DateUtils.date2Str2(new Date()));
                workflowRealInfo.setApprovalUserId(user.getId());
                workflowRealInfo.setApprovalUserName(user.getRealname());
                iWorkflowRealInfoService.save(workflowRealInfo);



            } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                //return GlobalResult.resOk("个性化表单数据插入失败");

            }
        }
        //if(workflow)
    }


}
