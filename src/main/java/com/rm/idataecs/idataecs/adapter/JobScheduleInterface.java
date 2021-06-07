package com.rm.idataecs.idataecs.adapter;

import com.rm.idataecs.idataecs.monitor.entity.MonitorConfigEntity;
import com.rm.idataecs.idataecs.test.dto.CommonResult;
import com.rm.idataecs.idataecs.util.CommonException;
import org.quartz.Scheduler;

/**
 * 调度任务适配器接口
 * @author zhaoxumeng
 */
public interface JobScheduleInterface {

    /**
     * 新建监控
     * @param monitorConfigEntity
     * @return
     */
    CommonResult createSchedule(MonitorConfigEntity monitorConfigEntity, Scheduler scheduler);

    /**
     * 更新
     * @param monitorConfigEntity
     * @return
     */
    CommonResult updateSchedule(MonitorConfigEntity monitorConfigEntity, Scheduler scheduler);

    /**
     * 删除
     * @param monitorConfigEntity
     * @return
     */
    CommonResult deleteSchedule(MonitorConfigEntity monitorConfigEntity, Scheduler scheduler);

    /**
     * 暂停监控
     * @param monitorConfigEntity
     * @return
     */
    CommonResult stopSchedule(MonitorConfigEntity monitorConfigEntity, Scheduler scheduler);


}
