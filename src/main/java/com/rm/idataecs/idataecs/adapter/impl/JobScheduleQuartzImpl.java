package com.rm.idataecs.idataecs.adapter.impl;

import com.rm.idataecs.idataecs.adapter.JobScheduleInterface;
import com.rm.idataecs.idataecs.monitor.entity.MonitorConfigEntity;
import com.rm.idataecs.idataecs.util.CommonException;
import org.springframework.stereotype.Repository;

/**
 * @program: idataecs
 * @description: 调度引擎适配器实现类
 * @author: xumeng.zhao
 * @create: 2021-06-04 10:04
 */
public class JobScheduleQuartzImpl implements JobScheduleInterface {


    @Override
    public CommonException createSchedule(MonitorConfigEntity monitorConfigEntity) {
        String msg ="调度任务创建成功";

        try {
            //TODO 引入调度框架实现功能

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public CommonException updateSchedule(MonitorConfigEntity monitorConfigEntity) {
        //TODO 引入调度框架实现功能

        return null;
    }

    @Override
    public CommonException deleteSchedule(MonitorConfigEntity monitorConfigEntity) {
        //TODO 引入调度框架实现功能

        return null;
    }

    @Override
    public CommonException stopSchedule(MonitorConfigEntity monitorConfigEntity) {
        //TODO 引入调度框架实现功能
        return null;
    }
}