package com.sydml.authorization.platform.job;

import com.sydml.authorization.job.domain.DynamicJob;
import com.sydml.common.utils.ReflectionUtil;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

/**
 * 和com.sydml.authorization.platform.job.DynamicTask功能类似,个人喜欢DynamicTask
 * 支持动态新增和停止任务
 *
 * @author Liuym
 * @date 2019/4/4 0004
 */

@Component
@EnableScheduling
public class DynamicScheduled implements SchedulingConfigurer {

    private Set<ScheduledTask> scheduledTasks = null;

    private volatile ScheduledTaskRegistrar registrar;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        this.registrar = taskRegistrar;
    }

    public void addTask(DynamicJob task, String cron) {
        Trigger trigger = triggerContext -> {
            //任务触发，可修改任务的执行周期.
            CronTrigger CronTrigger = new CronTrigger(cron);
            Date nextExec = CronTrigger.nextExecutionTime(triggerContext);
            return nextExec;
        };
        if (task != null) {
            TriggerTask triggerTask = new TriggerTask(task, trigger);
            // 既然加入了scheduledFutures,就不要在加入TriggerTask了
//            registrar.addTriggerTask(triggerTask);
//            ScheduledFuture<?> schedule = registrar.getScheduler().schedule(task, trigger);

            ScheduledTask scheduledTask = registrar.scheduleTriggerTask(triggerTask);
            getScheduledTasks().add(scheduledTask);
        }
    }

    /**
     * 停止指定task,这里停止的意义不大，实际业务中如果停止定时任务，要做回滚操作，不如执行完毕，错误的数据进行修复
     * 任务应该有详细的记录
     *
     * @param taskName
     */
    public void stopTask(String taskName) {
//        Set<ScheduledTask> tasks = registrar.getScheduledTasks(); 获取的是一个不可变集合,无法删除
        for (ScheduledTask scheduledTask : scheduledTasks) {
            DynamicJob job = (DynamicJob) scheduledTask.getTask().getRunnable();
            if (job.getName().equals(taskName)) {
                scheduledTask.cancel();
                scheduledTasks.remove(scheduledTask);
            }
        }
    }

    private Set<ScheduledTask> getScheduledTasks() {
        if (scheduledTasks == null) {
            try {

                scheduledTasks = (Set<ScheduledTask>) ReflectionUtil.getProperty(registrar, "scheduledTasks");
                registrar.getScheduledTasks();
            } catch (NoSuchFieldException e) {
                throw new SchedulingException("not found scheduledFutures field.");
            }
        }
        return scheduledTasks;
    }

}