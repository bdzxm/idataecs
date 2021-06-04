package com.rm.idataecs.idataecs.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rm.idataecs.idataecs.test.dto.CommonResult;
import com.rm.idataecs.idataecs.monitor.dto.MonitorConfigDTO;
import com.rm.idataecs.idataecs.monitor.entity.MonitorConfigEntity;

import java.util.List;

/**
 * @author zhaoxumeng
 */
public interface CreateMonitorConfigService extends IService<MonitorConfigEntity> {
    /**
     * create or update
     * @param mc
     */
    CommonResult createOrUpdateMonitorConfig(MonitorConfigDTO mc);


    /**
     * 根据表名查监控
     * @param
     * @return
     */
    List<MonitorConfigEntity> getMonitorByName(MonitorConfigDTO monitorConfigDTO);
}
