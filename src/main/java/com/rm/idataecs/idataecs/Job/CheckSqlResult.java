package com.rm.idataecs.idataecs.Job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rm.idataecs.idataecs.monitor.entity.MonitorConfigEntity;
import com.rm.idataecs.idataecs.monitor.entity.RunRecordEntity;
import com.rm.idataecs.idataecs.monitor.mapper.RunRecord;
import com.rm.idataecs.idataecs.util.CommnUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.jvm.hotspot.ui.tree.FloatTreeNodeAdapter;

import javax.script.ScriptEngine;
import java.util.*;

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

    /**
     * 返回值变量缓存
     */
    private static final HashMap<String, Map<String, String>> TABLE_WARNER = new HashMap<>();



    @Autowired
    RunRecord record;

    @Autowired


    /**
     * 巡检线程任务，定时按queryid 异步获取结果
     * 返回条件：自定义sql 查询结果字段为空值result_sqlcheck is null 并且 查询时间与当前执行时间差小于"100"（暂定）分钟,首次为扫到结果的查询会重复扫 直到超过时间范围，
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void getQueryData(){

        System.out.println("定时巡检");
        List<RunRecordEntity> recordByQueryTime = record.getRecordByQueryTime();
        //TODO  超时sql告警

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

    /**
     * 报警控制
     * 检查满足一定条件的返回数据 是否应该报警
     * 返回条件：自定义sql查询返回值 非空，变量字段非空，sql_check != 1
     * sql_check 字段用于标记当前数据是否被检测过 =1是 已经被加入到缓存
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void warner(){

        List<RunRecordEntity> probRecord = record.getProbRecord();
         //构建全局缓存 表的 变量和返回值对应关系
        for (RunRecordEntity re : probRecord) {
            //以表为单位的 变量和返回值集合  {cnt=100,sum=2000}
            HashMap<String, String> tm = new HashMap<>(32);
            //变量查询返回值 数组  [100,2000]
            String[] result = re.getResultSqlcheck().split(",");
            //变量名称数组   [cnt,sum]
            String[] variate = re.getVariates().split(",");
            //为每个表的变量和和返回值维护一个缓存Map  { 表a ={cnt=100,sum=2000}}
            Map<String, String> recordMap = TABLE_WARNER.get(re.getTableName());
            if(recordMap==null){
                for (int i = 0; i < result.length; i++) {
                    tm.put(variate[i],result[i]);
                }
                TABLE_WARNER.put(re.getTableName(),tm);

            }else {
                //已存在则按key 更新缓存
                for (int i = 0; i < result.length; i++) {
                    recordMap.put(variate[i],result[i]);
                }

            }
            re.setCheckFlag(1);
            record.updateById(re);
        }
        //检测是否发送报警逻辑
        for (RunRecordEntity runRecordEntity : probRecord) {
            String tableName = runRecordEntity.getTableName();
            String sqlJson = record.getTableByName(tableName).getSqlCheck();
            JSONObject sj = JSON.parseObject(sqlJson);
            //变量和表达式关系
            Set<Map.Entry<String, Object>> exeSet = sj.getJSONObject("exe").entrySet();
            Map<String, String> stringStringMap = TABLE_WARNER.get(tableName);
            System.out.println("map:"+TABLE_WARNER);
            for (Map.Entry<String, Object> stringObjectEntry : exeSet) {
                ScriptEngine parseEngine = CommnUtils.getParseEngine();
                String[] vArr = stringObjectEntry.getKey().split(",");

                Set<String> strings = stringStringMap.keySet();
               //检测全局缓存中包含本次表达式中的所有变量
                if(!strings.containsAll(Arrays.asList(vArr))){
                    log.error("全能局缓存为包含表达式全部变量"+Arrays.asList(vArr).toString()+",过本次执行");
                    return;
                }

                for (String v : vArr) {
                    String queryData = stringStringMap.get(v);
                    log.error("变量:{},查询结果:{},表达式:{}",v,queryData,stringObjectEntry.getValue().toString());
                    parseEngine.put(v,queryData);
                }
                boolean logicExpressionResult = CommnUtils.getLogicExpressionResult(parseEngine, stringObjectEntry.getValue().toString());
                if(!logicExpressionResult){
                    //报警处理
                    System.out.println(stringObjectEntry.getValue().toString()+":检测异常");
                }

            }

        }









        System.out.println( "map:"+TABLE_WARNER+","+TABLE_WARNER.size());

        TABLE_WARNER.clear();
    }

}