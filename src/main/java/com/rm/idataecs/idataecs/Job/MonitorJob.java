package com.rm.idataecs.idataecs.Job;
import com.rm.idataecs.idataecs.monitor.entity.MonitorConfigEntity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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
        //TODO 接口提交 反插入逻辑
        System.out.println("jobDetail实例运行中");



    }



}