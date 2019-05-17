package com.sydml.authorization.controller;

import com.sydml.authorization.platform.job.DynamicScheduled;
import com.sydml.authorization.job.domain.DynamicJob;
import com.sydml.authorization.platform.job.DynamicTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liuym
 * @date 2019/4/4 0004
 */
@RestController
@RequestMapping("dynamic-scheduled")
public class DynamicTaskController {
    @Autowired
    private DynamicScheduled dynamicScheduled;

    @Autowired
    private DynamicTask dynamicTask;

    @GetMapping("set-cron")
    public void setCron(String cron) {
        DynamicJob job = new DynamicJob("job-0");
        dynamicScheduled.addTask(job, cron);
    }

    @GetMapping("stop-cron")
    public void stopTask() {
        dynamicScheduled.stopTask("job-0");
    }


    @RequestMapping("start")
    public void startDynamicJob(String cron) {
        DynamicJob job = new DynamicJob("dynamic-job");
        dynamicTask.startCron(job, cron);
    }

    @GetMapping("start-another-job")
    public void startDynamicJob1(String cron) {
        DynamicJob job = new DynamicJob("dynamic-another-job");
        dynamicTask.startCron(job, cron);
    }

    @RequestMapping("stop")
    public void stopDynamicJob() {
        dynamicTask.stopCron("dynamic-job");
    }

    @RequestMapping("stop-another")
    public void stopAnotherDynamicJob() {
        dynamicTask.stopCron("dynamic-another-job");
    }

}
