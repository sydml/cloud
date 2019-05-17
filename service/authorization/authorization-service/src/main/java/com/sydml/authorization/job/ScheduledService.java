package com.sydml.authorization.job;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Liuym
 * @Async 开启支持异步方式
 * 存在局限如果 @Scheduled(cron = "0/5 * * * * *")内按照参数动态传入则无法处理
 * @date 2019/4/4 0004
 */
@Component
@EnableScheduling
public class ScheduledService {

    /*@Scheduled(cron = "0/5 * * * * *")
    @Async
    public void scheduled(){
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=====>>>>>使用cron "+System.currentTimeMillis());
    }

    @Scheduled(cron = "${jobs.cron}")
    @Async
    public void configCron(){
        System.out.println("=====>>>>>使用环境变量cron "+System.currentTimeMillis());
    }


    @Scheduled(fixedRate = 5000)
    @Async
    public void scheduled1() {
        System.out.println("=====>>>>>使用fixedRate"+System.currentTimeMillis());
    }
    @Scheduled(fixedDelay = 5000)
    @Async
    public void scheduled2() {
        System.out.println("=====>>>>>fixedDelay " + System.currentTimeMillis());
    }*/
}
