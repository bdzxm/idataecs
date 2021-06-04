package com.rm.idataecs.idataecs.adapter;

import com.rm.idataecs.idataecs.monitor.entity.MonitorConfigEntity;
import com.rm.idataecs.idataecs.util.CommonException;

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
    CommonException createSchedule(MonitorConfigEntity monitorConfigEntity);

    /**
     * 更新
     * @param monitorConfigEntity
     * @return
     */
    CommonException updateSchedule(MonitorConfigEntity monitorConfigEntity);

    /**
     * 删除
     * @param monitorConfigEntity
     * @return
     */
    CommonException deleteSchedule(MonitorConfigEntity monitorConfigEntity);

    /**
     * 暂停监控
     * @param monitorConfigEntity
     * @return
     */
    CommonException stopSchedule(MonitorConfigEntity monitorConfigEntity);


}
