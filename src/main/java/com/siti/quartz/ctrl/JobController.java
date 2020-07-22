package com.siti.quartz.ctrl;

import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.siti.quartz.po.JobEntity;
import com.siti.quartz.service.DynamicJobService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by quyue1205 on 2019/1/22.
 * 任务调度
 */
@RestController
@RequestMapping("job")
public class JobController {

    @Resource
    private SchedulerFactoryBean factory;
    @Resource
    private DynamicJobService jobService;

    //被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次
    //初始化启动所有的Job
    @PostConstruct
    public void initialize() {
        jobService.reStartAllJobs();
    }

    //根据ID重启某个Job
    @PutMapping("/refresh/{id}")
    public String refresh(@PathVariable Integer id) throws SchedulerException {
        String result;
        JobEntity entity = jobService.getJobEntityById(id);
        if (entity == null) return "error: id is not exist ";
        TriggerKey triggerKey = new TriggerKey(entity.getName(), entity.getGroup());
        JobKey jobKey = jobService.getJobKey(entity);
        Scheduler scheduler = factory.getScheduler();
        try {
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
            JobDataMap map = jobService.getJobDataMap(entity);
            JobDetail jobDetail = jobService.geJobDetail(jobKey, entity.getDescription(), map);
            if (entity.getStatus().equals("OPEN")) {
                scheduler.scheduleJob(jobDetail, jobService.getTrigger(entity));
                result = "Refresh Job : " + entity.getName() + "\t method: " + entity.getClassName() + "." + entity.getMethodName() + " success !";
                jobService.updateJobEntityWorkStatusByKey(entity);
            } else {
                result = "Refresh Job : " + entity.getName() + "\t method: " + entity.getClassName() + "." + entity.getMethodName() + " failed ! , " + "Because the Job status is " + entity.getStatus();
            }
        } catch (SchedulerException e) {
            result = "Error while Refresh " + e.getMessage();
        }
        return result;
    }

    //重启数据库中所有的Job
    @PutMapping("/refresh/all")
    public String refreshAll() {
        String result;
        try {
            jobService.reStartAllJobs();
            result = "SUCCESS";
        } catch (Exception e) {
            result = "EXCEPTION : " + e.getMessage();
        }
        return "refresh all jobs : " + result;
    }
}