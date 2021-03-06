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

import java.util.List;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-05-31 19:58
 */
@Service
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

        JobScheduleInterface scheduleImpl = jobScheduleImplFactory.getJobScheduleImpl();

//        IdataEcsMesInterface mesImpl = idataEcsMesImplFactory.getIdataEcsMesImpl("upush");

        //????????????
        CommonResult fcr = this.paramCheck(mc);

        if (StringUtils.isNotBlank(fcr.getMsg())) {
            fcr.setCode(ResultStatus.fail.getCode());
            return fcr;
        }
        String gc = JSONObject.toJSONString(mc.getGeneralCheck());
        String sqlJson = JSONObject.toJSONString(mc.getSqlCheck());
        mc.setGeneralCheck(gc);
        mc.setSqlCheck(sqlJson);
        mc.setTime(CommnUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        MonitorConfigEntity monitorConfigEntity = this.init(mc);
        //TODO ????????????
        this.save(monitorConfigEntity);
        CommonResult scr = new CommonResult(ResultStatus.Success.getCode(),"????????????",monitorConfigEntity);

        System.out.println(StringUtils.isBlank(monitorConfigEntity.getSqlCheck()));
        System.out.println(monitorConfigEntity.getSqlCheck().length());
        if(StringUtils.isNotBlank(monitorConfigEntity.getSqlCheck())){

             scr = scheduleImpl.createSchedule(monitorConfigEntity, scheduler);
        }

        return scr;
    }

    @Override
    public List<MonitorConfigEntity> getMonitorByName(MonitorConfigDTO monitorConfigDTO) {
        return createOrUpdateMonitorConfigMapper.getMonitorByTableName(monitorConfigDTO.getTableName());

    }

    public MonitorConfigEntity init(MonitorConfigDTO mc) {
        MonitorConfigEntity monitorConfigEntity = new MonitorConfigEntity();
        monitorConfigEntity.setId(mc.getId());
        monitorConfigEntity.setDt(mc.getDt());
        monitorConfigEntity.setTableName(mc.getTableName());
        monitorConfigEntity.setGeneralCheck((String) mc.getGeneralCheck());
        monitorConfigEntity.setSqlCheck((String) mc.getSqlCheck());
        monitorConfigEntity.setRepeatedCheck(mc.getRepeatedCheck());
        monitorConfigEntity.setNumCheck(mc.getNumCheckEnum().getCode());
        monitorConfigEntity.setIncrementCheck(mc.getIncrementCheckEnum().getCode());
        monitorConfigEntity.setStatus(1);
        monitorConfigEntity.setCreateTime(mc.getTime());
        return monitorConfigEntity;
    }


    /**
     * ????????????
     *
     * @param monitorConfigDTO
     * @return
     */
    public CommonResult paramCheck(MonitorConfigDTO monitorConfigDTO) {
        CommonResult commonResult = new CommonResult();
        //????????????
        CommnUtils.requirePass((x) -> StringUtils.isBlank(x.getTableName()), monitorConfigDTO, commonResult, "??????????????????;");
        //??????????????????
        CommnUtils.requirePass((x) -> CommnUtils.isBlank(x.getGeneralCheck()) && CommnUtils.isBlank(x.getSqlCheck()), monitorConfigDTO, commonResult, "????????????????????????sql???????????????;");
        //?????????
        CommnUtils.requirePass((x) -> getMonitorByName(x).size() > 0, monitorConfigDTO, commonResult, "??????????????????");
        return commonResult;

    }



}