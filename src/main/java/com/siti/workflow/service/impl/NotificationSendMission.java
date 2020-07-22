package com.siti.workflow.service.impl;

import com.siti.common.executor.ColorsExecutor;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * Created by Solarie on 2020/7/15.
 */
public class NotificationSendMission {
    //线程池不放在方法内 静态定义                                                                                                                       //无界队列  核心线程10个 最大线程50 30个任务 运行的线程10个
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(200, 500, 500, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>(200));


        @Resource
    ColorsExecutor colorsExecutor;

    public void msg2AllUser() {
        ThreadPoolExecutor executor = colorsExecutor.init();
        //executor.submit(this);
    }


    public static void main(String[] args) {
        //new 一个runnable是任务 不是一个线程
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //并发时出现问题
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

        //---> Executors // 阿里网易的生产环境不允许使用
        //并发情况下会达到Integer的最大值 2 31次

        executor.execute(runnable);
        executor.shutdown();



    }

}
