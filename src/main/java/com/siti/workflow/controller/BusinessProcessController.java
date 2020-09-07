package com.siti.workflow.controller;

import com.alibaba.fastjson.JSONObject;
import com.siti.common.vo.LoginUser;
import com.siti.system.ctrl.LoginCtrl;
import com.siti.utils.SpringBootBeanUtil;
import com.siti.workflow.entity.Workflow;
import com.siti.workflow.entity.WorkflowReal;
import com.siti.workflow.service.IWorkflowRealInfoService;
import com.siti.workflow.service.IWorkflowRealService;
import com.siti.workflow.service.IWorkflowService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    LoginCtrl loginCtrl;

    /**
     * 流程初始化
     */
    @PostMapping("init")
    public void initBusiProcess(String workflowCode, @RequestBody Object t) {
        LoginUser loginUserInfo = loginCtrl.getLoginUserInfo();
        Workflow workflow = iWorkflowService.getById(workflowCode);
        if (workflow != null) {
            String relaTableName = workflow.getRelaTableName();
            //relaTableName 做比对，将 obj插入 relaTableName 表， id 记录在workflow_real
            try {
                //从ApplicationContext中取出已创建好的的对象
                //不可直接反射创建serviceimpi对象，因为反射创建出来的对象无法实例化dao接口
                ApplicationContext applicationContext = SpringBootBeanUtil.getApplicationContext();
                //反射创建serviceimpi实体对象，和实体类
                Class<?> ServiceImplType = Class.forName("com.siti.workflow.service.impl."+relaTableName+"ServiceImpl");
                Class<?> entityType = Class.forName("com.siti.bussiness.entity."+relaTableName);
                //先转成JSON对象
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(t);

                //JSON对象转换成Java对象
                WorkflowReal workflowReal = JSONObject.toJavaObject(jsonObject, WorkflowReal.class);
                //反射设置方法参数。

                Method method = ServiceImplType.getMethod("insert", entityType);
                //BeanUtils.copyProperties(t,workflowReal);
                //在ApplicationContext中根据class取出已实例化的bean           //将获得的obj转译成实体对象
                if(workflowReal!=null){
                    method.invoke(applicationContext.getBean(ServiceImplType), workflowReal);
                }




            }  catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            //return GlobalResult.resOk("个性化表单数据插入失败");

        }


        }
        //if(workflow)

    }


}
