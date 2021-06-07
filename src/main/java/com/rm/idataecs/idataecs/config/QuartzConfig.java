package com.rm.idataecs.idataecs.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-06-07 17:04
 */
@Configuration
public class QuartzConfig {

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();

        return scheduler;
    }
    @Bean
    SchedulerFactoryBean schedulerFactoryBean( QuartzFactory quartzFactory){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //自定义job工厂，交给spring管理
        schedulerFactoryBean.setJobFactory(quartzFactory);
        return schedulerFactoryBean;
    }

}