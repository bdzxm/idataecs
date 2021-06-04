package com.rm.idataecs.idataecs.adapter;

import com.rm.idataecs.idataecs.adapter.impl.JobScheduleQuartzImpl;
import org.springframework.stereotype.Repository;

/**
 * @program: idataecs
 * @description:调度引擎工厂类
 * @author: xumeng.zhao
 * @create: 2021-06-04 13:18
 */
@Repository
public class JobScheduleImplFactory {
    private JobScheduleInterface jobScheduleImpl;


    /**
     * 调度引擎默认实现顺序
     * @return
     */
    public JobScheduleInterface getJobScheduleImpl() {
        defaultJSI(this::useQuartz);
        return jobScheduleImpl;
    }

    /**
     * 可配置实现
     * @param scheduleType
     * @return
     */
    public JobScheduleInterface getJobScheduleImpl(String scheduleType) {

        if (scheduleType.toLowerCase().equals("quartz")) {
            jobScheduleImpl = new JobScheduleQuartzImpl();
        }
        return jobScheduleImpl;

    }

    private void defaultJSI(Runnable runnable) {
        if (jobScheduleImpl == null) {
            runnable.run();

        }
    }

    private void useQuartz() {
        jobScheduleImpl = new JobScheduleQuartzImpl();
    }
    //扩展实现

}