package com.rm.idataecs.idataecs.Job;

import com.alibaba.fastjson.JSON;
import com.rm.idataecs.idataecs.monitor.entity.MonitorConfigEntity;
import com.rm.idataecs.idataecs.monitor.entity.RunRecordEntity;
import com.rm.idataecs.idataecs.monitor.mapper.RunRecord;
import com.rm.idataecs.idataecs.util.CommnUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-06-04 20:13
 */
public class MonitorJob implements Job {


    @Autowired
    RunRecord runRecord;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        MonitorConfigEntity monitorConfigEntity = (MonitorConfigEntity) context.getMergedJobDataMap().get("entity");
        System.out.println("shiti:"+monitorConfigEntity);
        //TODO 接口提交 反插入逻辑
        System.out.println("jobDetail实例运行中");
        JobKey key = context.getJobDetail().getKey();
        String sqlCheck = monitorConfigEntity.getSqlCheck();
        System.out.println(sqlCheck);
        String[] sqls = JSON.parseObject(sqlCheck).get("sql").toString().split(";");
        RunRecordEntity runRecordEntity = new RunRecordEntity();

        for (String sql : sqls) {
            //模仿查询id
            runRecordEntity.setQueryId(System.currentTimeMillis());
            runRecordEntity.setQueryTime(CommnUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
            runRecordEntity.setTableName(key.getName());
            runRecord.insert(runRecordEntity);
        }


    }


}