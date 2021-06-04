package com.rm.idataecs.idataecs.monitor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public CommonResult createOrUpdateMonitorConfig(MonitorConfigDTO mc) {

        JobScheduleInterface scheduleImpl = jobScheduleImplFactory.getJobScheduleImpl();

        IdataEcsMesInterface mesImpl = idataEcsMesImplFactory.getIdataEcsMesImpl("upush");

        CommonResult commonResult = this.paramCheck(mc);
        //参数审查
        if (StringUtils.isNotBlank(commonResult.getMsg())) {
            commonResult.setCode(ResultStatus.fail.getCode());
            return commonResult;
        }
        String gc = JSONObject.toJSONString(mc.getGeneralCheck());
        String sqlJson = JSONObject.toJSONString(mc.getSqlCheck());
        mc.setGeneralCheck(gc);
        mc.setSqlCheck(sqlJson);
        mc.setTime(CommnUtils.getCurrentTime());
        MonitorConfigEntity monitorConfigEntity = this.init(mc);

        //TODO 事务控制
        this.save(monitorConfigEntity);
        CommonException schedule = scheduleImpl.createSchedule(monitorConfigEntity);
        SendMessageEntity sme = (SendMessageEntity)mesImpl.send2WX(new SendMessageEntity(mc.getTableName(), CommonConstants.SUCCESS_MSG));
        return new CommonResult(ResultStatus.Success.getCode(),sme.getMsgException(), CommonConstants.SUCCESS_MSG, schedule);
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
     * 参数审查
     *
     * @param monitorConfigDTO
     * @return
     */
    public CommonResult paramCheck(MonitorConfigDTO monitorConfigDTO) {
        CommonResult commonResult = new CommonResult();
        //表名为空
        CommnUtils.requirePass((x) -> StringUtils.isBlank(x.getTableName()), monitorConfigDTO, commonResult, "表名不能为空;");
        //没有监控内容
        CommnUtils.requirePass((x) -> CommnUtils.isBlank(x.getGeneralCheck()) && CommnUtils.isBlank(x.getSqlCheck()), monitorConfigDTO, commonResult, "基础检测和自定义sql不能都为空;");
        //已存在
        CommnUtils.requirePass((x) -> getMonitorByName(x).size() > 0, monitorConfigDTO, commonResult, "表监控已存在");
        return commonResult;

    }


}