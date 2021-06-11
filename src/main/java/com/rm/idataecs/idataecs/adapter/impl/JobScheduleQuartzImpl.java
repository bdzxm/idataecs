package com.rm.idataecs.idataecs.adapter.impl;

import com.rm.idataecs.idataecs.Job.MonitorJob;
import com.rm.idataecs.idataecs.adapter.JobScheduleInterface;
import com.rm.idataecs.idataecs.constants.ResultStatus;
import com.rm.idataecs.idataecs.monitor.entity.MonitorConfigEntity;
import com.rm.idataecs.idataecs.test.dto.CommonResult;
import org.quartz.*;
import org.springframework.stereotype.Service;

/**
 * @program: idataecs
 * @description: 调度引擎适配器实现类
 * @author: xumeng.zhao
 * @create: 2021-06-04 10:04
 */
@Service
public class JobScheduleQuartzImpl implements JobScheduleInterface {


    @Override
    public CommonResult createSchedule(MonitorConfigEntity monitorConfigEntity, Scheduler scheduler) {
        CommonResult commonResult = new CommonResult();
        String msg = "调度任务创建成功";
        commonResult.setCode(ResultStatus.Success.getCode());
        try {
            //触发器
            Trigger trigger = this.buildTrigger(monitorConfigEntity);
            //任务实例
            JobDetail jobDetail = this.buildJobDetail(monitorConfigEntity);
            //提交调度
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            commonResult.setCode(ResultStatus.fail.getCode());
            commonResult.setE(msg);
        }
        commonResult.setMsg(msg);
        return commonResult;
    }

    @Override
    public CommonResult updateSchedule(MonitorConfigEntity monitorConfigEntity, Scheduler scheduler) {
        //TODO 引入调度框架实现功能

        return null;
    }

    @Override
    public CommonResult deleteSchedule(MonitorConfigEntity monitorConfigEntity, Scheduler scheduler) {
        //TODO 引入调度框架实现功能

        return null;
    }

    @Override
    public CommonResult stopSchedule(MonitorConfigEntity monitorConfigEntity, Scheduler scheduler) {
        //TODO 引入调度框架实现功能
        return null;
    }


    public Trigger buildTrigger(MonitorConfigEntity me) {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(me.getCron());

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .forJob(JobKey.jobKey(me.getTableName(), "数仓测试组"))
                .withIdentity(me.getTableName(), "数仓测试组")
                .withSchedule(cronScheduleBuilder)
                .startNow()
                .build();
        return trigger;
    }

    public JobDetail buildJobDetail(MonitorConfigEntity me) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("entity",me);

        return JobBuilder.newJob(MonitorJob.class)
                .setJobData(jobDataMap)
                .withIdentity(me.getTableName(), "数仓测试组")
                .build();
    }

}