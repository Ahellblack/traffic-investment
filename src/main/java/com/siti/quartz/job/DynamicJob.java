package com.siti.quartz.job;

import com.alibaba.fastjson.JSONObject;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.siti.quartz.utils.StringUtils;
import com.siti.quartz.vo.DataType;
import com.siti.quartz.vo.ParamVo;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by quyue1205 on 2019/1/22.
 * :@DisallowConcurrentExecution : 此标记用在实现Job的类上面,意思是不允许并发执行.
 * :注意org.quartz.threadPool.threadCount线程池中线程的数量至少要多个,否则@DisallowConcurrentExecution不生效
 * :假如Job的设置时间间隔为3秒,但Job执行时间是5秒,设置@DisallowConcurrentExecution以后程序会等任务执行完毕以后再去执行,否则会在3秒时再启用新的线程执行
 */
@DisallowConcurrentExecution
@Component
public class DynamicJob implements Job {

    private Logger logger = LoggerFactory.getLogger(DynamicJob.class);

    @Resource
    private ApplicationContext context;

    /**
     * 核心方法,Quartz Job真正的执行逻辑.
     *
     * @param executorContext executorContext JobExecutionContext中封装有Quartz运行所需要的所有信息
     * @throws JobExecutionException execute()方法只允许抛出JobExecutionException异常
     */
    @Override
    public void execute(JobExecutionContext executorContext) throws JobExecutionException {
        //JobDetail中的JobDataMap是共用的,从getMergedJobDataMap获取的JobDataMap是全新的对象
        JobDataMap map = executorContext.getMergedJobDataMap();
        String className = map.getString("className");
        String methodName = map.getString("methodName");
        String parameter = map.getString("parameter");
        String params = map.getString("params");
        logger.info("Running Job name : {} ", map.getString("name"));
        logger.info("Running Job description : " + map.getString("JobDescription"));
        logger.info("Running Job group: {} ", map.getString("group"));
        logger.info("Running Job cron : " + map.getString("cronExpression"));
        logger.info("Running Job method : {} ", className + "." + methodName);
        logger.info("Running Job parameter : {} ", parameter);
        logger.info("Running Job params : {} ", params);
        long startTime = System.currentTimeMillis();
        if (!StringUtils.getStringUtil.isEmpty(className) && !StringUtils.getStringUtil.isEmpty(methodName)) {
            Object object;
            Class clazz;
            try {
                clazz = Class.forName(className);
                Class<?> handler = Class.forName(className);
                object = context.getAutowireCapableBeanFactory().createBean(handler, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
                if (object == null) {
                    logger.error("任务名称 = [" + className + "." + methodName + "]---------------未启动成功，请检查是否配置正确！！！");
                    return;
                }
                List<ParamVo> paramVoList = JSONObject.parseArray(params, ParamVo.class);
                if (paramVoList != null && paramVoList.size() > 0) {
                    Class[] clazzes = new Class[paramVoList.size()];
                    Object[] paramses = new Object[paramVoList.size()];
                    for (int i = 0; i < paramVoList.size(); i++) {
                        clazzes[i] = Class.forName(DataType.dateTypeMap().get(paramVoList.get(i).getType()));
                        switch (paramVoList.get(i).getType()) {
                            case "Integer":
                                paramses[i] = paramVoList.get(i).getValue() == null ? null : Integer.parseInt(paramVoList.get(i).getValue());
                                break;
                            case "Float":
                                paramses[i] = paramVoList.get(i).getValue() == null ? null : Float.parseFloat(paramVoList.get(i).getValue());
                                break;
                            case "Double":
                                paramses[i] = paramVoList.get(i).getValue() == null ? null : Double.parseDouble(paramVoList.get(i).getValue());
                                break;
                            default:
                                paramses[i] = paramVoList.get(i).getValue() == null ? "" : paramVoList.get(i).getValue();
                                break;
                        }
                    }
                    Method method = clazz.getMethod(methodName, clazzes);
                    method.invoke(object, paramses);
                } else {
                    Method method = clazz.getMethod(methodName);
                    method.invoke(object);
                }
            } catch (ClassNotFoundException cnfe) {
                throw new JobExecutionException("Job Class not found >>  " + className);
            } catch (IllegalAccessException iae) { //如果该类或其 null 构造方法是不可访问的
                throw new JobExecutionException("Job method not found >>  " + className + "." + methodName);
            } catch (NoSuchMethodException e) {
                logger.error("任务名称 = [" + className + "." + methodName + "]---------------未启动成功，方法名设置错误！！！");
                throw new JobExecutionException("Job method not found >>  " + className + "." + methodName);
            } catch (Exception e) {
                e.printStackTrace();
                throw new JobExecutionException();
            }
            logger.error("任务名称 = [" + className + "." + methodName + "]---------------启动成功！");
        }
        long endTime = System.currentTimeMillis();
        logger.info(">>>>>>>>>>>>> Running Job has been completed , cost time :  " + (endTime - startTime) + "ms\n");
    }

    //打印Job执行内容的日志
    private void logProcess(InputStream inputStream, InputStream errorStream) throws IOException {
        String inputLine;
        String errorLine;
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
        while ((inputLine = inputReader.readLine()) != null) logger.info(inputLine);
        while ((errorLine = errorReader.readLine()) != null) logger.error(errorLine);
    }
}
