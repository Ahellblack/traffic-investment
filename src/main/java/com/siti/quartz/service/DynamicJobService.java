package com.siti.quartz.service;

import com.siti.common.MyException;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import com.siti.quartz.job.DynamicJob;
import com.siti.quartz.mapper.JobEntityMapper;
import com.siti.quartz.po.JobEntity;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by quyue1205 on 2019/1/22.
 */
@Service
public class DynamicJobService {

    private static Logger logger = LoggerFactory.getLogger(DynamicJobService.class);

    @Resource
    private SchedulerFactoryBean factory;
    @Resource
    private JobEntityMapper jobEntityMapper;

    /**
     * 重新启动所有的job
     */
    public void reStartAllJobs() {
        try {
            Scheduler scheduler = factory.getScheduler();
            Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
            for (JobKey jobKey : set) {
                scheduler.deleteJob(jobKey);
            }
            for (JobEntity job : this.loadJobs()) {
                logger.info("Job register name : {} , group : {} , cron : {}", job.getName(), job.getGroup(), job.getCron());
                JobDataMap map = this.getJobDataMap(job);
                JobKey jobKey = this.getJobKey(job);
                JobDetail jobDetail = this.geJobDetail(jobKey, job.getDescription(), map);
                if ("OPEN".equals(job.getStatus())) {
                    scheduler.scheduleJob(jobDetail, this.getTrigger(job));
                } else {
                    logger.info("Job jump name : {} , Because {} status is {}", job.getName(), job.getName(), job.getStatus());
                }
                updateJobEntityWorkStatusByKey(job);
            }
            logger.info("INIT SUCCESS");
        } catch (SchedulerException se) {
            throw new MyException("INIT EXCEPTION : " + se.getMessage());
        }
    }

    //通过Id获取Job
    public JobEntity getJobEntityById(Integer id) {
        return jobEntityMapper.selectByPrimaryKey(id);
    }

    //更新Job的工作状态
    public void updateJobEntityWorkStatusByKey(JobEntity entity) {
        entity.setWorkStatus(1);
        jobEntityMapper.updateJobEntityWorkStatusByKey(entity.getId(), entity.getWorkStatus());
    }

    //从数据库中加载获取到所有Job
    private List<JobEntity> loadJobs() {
        //return new ArrayList<>();
        return jobEntityMapper.selectAll();
    }

    //获取JobDataMap.(Job参数对象)
    public JobDataMap getJobDataMap(JobEntity job) {
        JobDataMap map = new JobDataMap();
        map.put("name", job.getName());
        map.put("group", job.getGroup());
        map.put("cronExpression", job.getCron());
        map.put("parameter", job.getParameter());
        map.put("JobDescription", job.getDescription());
        map.put("className", job.getClassName());
        map.put("methodName", job.getMethodName());
        map.put("params", job.getParams());
        map.put("status", job.getStatus());
        return map;
    }

    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    public JobDetail geJobDetail(JobKey jobKey, String description, JobDataMap map) {
        return JobBuilder.newJob(DynamicJob.class)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }

    //获取Trigger (Job的触发器,执行规则)
    public Trigger getTrigger(JobEntity job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getName(), job.getGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();
    }

    //获取JobKey,包含Name和Group
    public JobKey getJobKey(JobEntity job) {
        return JobKey.jobKey(job.getName(), job.getGroup());
    }
}
