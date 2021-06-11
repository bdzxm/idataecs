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
        String[] variates = JSON.parseObject(sqlCheck).get("variates").toString().split(";");
        //同一次调度用同一个时间戳
        String currentTime = CommnUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
        /**
         * {
         *     "sql":"select count(1) as cnt  sum(total) as total_num from lucky_stock.t_warehouse_stock_detail ;select sum(total) as total_num_day from lucky_stock.t_warehouse_stock_detail_everyday ",
         *     "variates":"cnt,total_num;total_num_day",
         *     "exe":[{"cnt":"cnt>1000" },{"total_num,total_num_day":"total_num=total_num_day"}]
         * }
         */
        for (int i = 0; i < sqls.length; i++) {
            RunRecordEntity runRecordEntity = new RunRecordEntity();

            //模仿查询id
            runRecordEntity.setQueryId(System.currentTimeMillis());
            runRecordEntity.setQueryTime(currentTime);
            runRecordEntity.setTableName(key.getName());
            runRecordEntity.setVariates(variates[i]);
            runRecord.insert(runRecordEntity);

        }



    }


}