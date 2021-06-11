package com.rm.idataecs.idataecs.monitor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rm.idataecs.idataecs.Job.MonitorJob;
import com.rm.idataecs.idataecs.adapter.IdataEcsMesImplFactory;
import com.rm.idataecs.idataecs.adapter.IdataEcsMesInterface;
import com.rm.idataecs.idataecs.adapter.JobScheduleImplFactory;
import com.rm.idataecs.idataecs.adapter.JobScheduleInterface;
import com.rm.idataecs.idataecs.constants.CommonConstants;
import com.rm.idataecs.idataecs.monitor.entity.SendMessageEntity;
import com.rm.idataecs.idataecs.test.dto.CommonResult;
import com.rm.idataecs.idataecs.constants.ResultStatus;
import com.rm.idataecs.idataecs.monitor.dto.MonitorConfigDTO;
import com.rm.idataecs.idataecs.monitor.entity.MonitorConfigEntity;
import com.rm.idataecs.idataecs.monitor.mapper.CreateOrUpdateMonitorConfigMapper;
import com.rm.idataecs.idataecs.monitor.service.CreateMonitorConfigService;
//import com.rm.idataecs.idataecs.util.CommnUtils;
import com.rm.idataecs.idataecs.util.CommnUtils;
import com.rm.idataecs.idataecs.util.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-05-31 19:58
 */
@Service
@Transactional
public class CreateMonitorConfigServiceImpl extends ServiceImpl<CreateOrUpdateMonitorConfigMapper, MonitorConfigEntity> implements CreateMonitorConfigService {


    @Autowired
    CreateOrUpdateMonitorConfigMapper createOrUpdateMonitorConfigMapper;

    @Autowired
    JobScheduleImplFactory jobScheduleImplFactory;

    @Autowired
    IdataEcsMesImplFactory idataEcsMesImplFactory;

    @Autowired
    Scheduler scheduler;


    @Override
    public CommonResult createOrUpdateMonitorConfig(MonitorConfigDTO mc) {
        //调度器
        JobScheduleInterface scheduleImpl = jobScheduleImplFactory.getJobScheduleImpl();
        //参数审查
        CommonResult fcr = this.paramCheck(mc);

        if (StringUtils.isNotBlank(fcr.getMsg())) {
            fcr.setCode(ResultStatus.fail.getCode());
            return fcr;
        }
        MonitorConfigEntity monitorConfigEntity = this.init(mc);

        CommonResult scr = new CommonResult(ResultStatus.Success.getCode(),"提交成功",monitorConfigEntity);

        if(StringUtils.isNotBlank(monitorConfigEntity.getSqlCheck())){
             scr = scheduleImpl.createSchedule(monitorConfigEntity, scheduler);
        }
        if(scr.getCode()==0){
            this.save(monitorConfigEntity);
        }

        return scr;
    }

    @Override
    public List<MonitorConfigEntity> getMonitorByName(MonitorConfigDTO monitorConfigDTO) {
        return createOrUpdateMonitorConfigMapper.getMonitorByTableName(monitorConfigDTO.getTableName());

    }

    public MonitorConfigEntity init(MonitorConfigDTO mc) {
        String gc = JSONObject.toJSONString(mc.getGeneralCheck());
        String sqlJson = JSONObject.toJSONString(mc.getSqlCheck());
        mc.setTime(CommnUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        MonitorConfigEntity monitorConfigEntity = new MonitorConfigEntity();
        monitorConfigEntity.setId(mc.getId());
        monitorConfigEntity.setDt(mc.getDt());
        monitorConfigEntity.setTableName(mc.getTableName());
        monitorConfigEntity.setGeneralCheck(gc);
        monitorConfigEntity.setSqlCheck(sqlJson);
        monitorConfigEntity.setRepeatedCheck(mc.getRepeatedCheck());
        monitorConfigEntity.setNumCheck(mc.getNumCheckEnum().getCode());
        monitorConfigEntity.setIncrementCheck(mc.getIncrementCheckEnum().getCode());
        monitorConfigEntity.setStatus(1);
        monitorConfigEntity.setCreateTime(mc.getTime());
        monitorConfigEntity.setCron(mc.getCron());
        monitorConfigEntity.setLastModifyTime(mc.getTime());
        return monitorConfigEntity;
    }


    /**
     * 参数审查
     *
     * @param monitorConfigDTO
     * @return
     */
    public CommonResult paramCheck(MonitorConfigDTO monitorConfigDTO) {
        CommonResult commonResult = new CommonResult();
        if (StringUtils.isNotBlank(monitorConfigDTO.getSqlCheck().toString())){
            CommnUtils.requirePass((x)->StringUtils.isBlank(monitorConfigDTO.getCron()),monitorConfigDTO,commonResult,"运行时间规则不能为空,以idata为标准");
        }
        //表名为空
        CommnUtils.requirePass((x) -> StringUtils.isBlank(x.getTableName()), monitorConfigDTO, commonResult, "表名不能为空;");
        //没有监控内容
        CommnUtils.requirePass((x) -> CommnUtils.isBlank(x.getGeneralCheck()) && CommnUtils.isBlank(x.getSqlCheck()), monitorConfigDTO, commonResult, "基础检测和自定义sql不能都为空;");
        //已存在
        CommnUtils.requirePass((x) -> getMonitorByName(x).size() > 0, monitorConfigDTO, commonResult, "表监控已存在");
        return commonResult;

    }



}