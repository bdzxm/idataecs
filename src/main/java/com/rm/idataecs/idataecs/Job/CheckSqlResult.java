package com.rm.idataecs.idataecs.Job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rm.idataecs.idataecs.monitor.entity.RunRecordEntity;
import com.rm.idataecs.idataecs.monitor.mapper.RunRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @program: idataecs
 * @description: 定时巡检
 * @author: xumeng.zhao
 * @create: 2021-06-08 13:44
 */
@EnableScheduling
@Component
@Slf4j
public class CheckSqlResult {


    @Autowired
    RunRecord record;

    /**
     * 巡检线程任务，定时按queryid 异步获取查询结果
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void getQueryData(){
        System.out.println("定时巡检");
        List<RunRecordEntity> recordByQueryTime = record.getRecordByQueryTime();

        if (recordByQueryTime.size()==0){
            log.error("未检测到异步查询");
        }else {
            //模拟获取返回值
            Random random = new Random();
            for (RunRecordEntity runRecordEntity : recordByQueryTime) {
                if (StringUtils.isBlank(runRecordEntity.getResultSqlcheck())){
                    runRecordEntity.setResultSqlcheck(random.nextInt(1000) + 1+"");
                    //回写mysql
                    record.updateById(runRecordEntity);
                }

            }

        }



    }
}