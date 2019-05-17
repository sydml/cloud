package com.sydml.authorization.platform.job;

import com.sydml.authorization.job.domain.DynamicJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 这个类已经验证通过
 * 支持启动和停止任务
 * 支持动态增加新任务，动态修改cron
 *
 * @author Liuym
 * @date 2019/4/8 0008
 */
@Component
public class DynamicTask {

    /**
     * 定时任务存储器
     * fixme 暂时用map缓存定时任务，这里感觉不太合适，后期需要修改
     */
    public static ConcurrentHashMap<String, ScheduledFuture> scheduledMap = new ConcurrentHashMap<>();

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    private int taskSchedulerCorePoolSize = 50;

    static boolean isinitialized = false;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(taskSchedulerCorePoolSize);
        threadPoolTaskScheduler.setThreadNamePrefix("DynamicTask-scheduledTask-");
        // 需要实例化线程
        threadPoolTaskScheduler.initialize();
        isinitialized = true;
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        return threadPoolTaskScheduler;
    }

    /**
     * scheduledMap内存存放任务，然后根据key指定停止
     *
     * @return
     */
    public String stopCron(String taskName) {
        ScheduledFuture scheduledFuture = scheduledMap.get(taskName);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            System.out.println("DynamicTask.stopCron()");
            return "stopCron";
        } else {
            return "stopCron failure";
        }
    }

    public String startCron(DynamicJob task, String cron) {
        // 修改cron 后需要停止之前的任务，之后再重启
        if (scheduledMap.get(task.getName()) != null) {
            stopCron(task.getName());
        }
        future = threadPoolTaskScheduler.schedule(task, new CronTrigger(cron));
        scheduledMap.put(task.getName(), future);
        System.out.println("DynamicTask.startCron()");
        return "startCron";
    }


}
