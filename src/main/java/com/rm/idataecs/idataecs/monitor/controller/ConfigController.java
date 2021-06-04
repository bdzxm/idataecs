package com.rm.idataecs.idataecs.monitor.controller;

import com.rm.idataecs.idataecs.test.dto.CommonResult;
import com.rm.idataecs.idataecs.monitor.dto.MonitorConfigDTO;
import com.rm.idataecs.idataecs.monitor.service.CreateMonitorConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-05-31 18:11
 */
@RestController
@Slf4j
public class ConfigController {


     @Autowired
    CreateMonitorConfigService createMonitorConfigServiceImpl;

    /**
     * create
     * @param mc
     * @return
     */
    @PostMapping("/createTableMonitorConfig")
    public CommonResult createTableMonitorConfig(@RequestBody MonitorConfigDTO mc){
        log.error("MonitorConfigDTO:{}",mc);
        return  createMonitorConfigServiceImpl.createOrUpdateMonitorConfig(mc);

    }
}