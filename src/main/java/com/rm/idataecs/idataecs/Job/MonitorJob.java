package com.rm.idataecs.idataecs.Job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rm.idataecs.idataecs.monitor.entity.MonitorConfigEntity;
import com.rm.idataecs.idataecs.monitor.entity.SqlCheckEntity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-06-04 20:13
 */
public class MonitorJob implements Job {




    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        MonitorConfigEntity monitorConfigEntity = (MonitorConfigEntity) context.get("entity");
        System.out.println("jobDetail实例运行中");



    }



}