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
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

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
    private static final HashMap<String, Map<String, Double>> TABLE_WARNER = new HashMap<>();


    @Autowired
    RunRecord record;


    /**
     * 巡检线程任务，定时按queryid 异步获取结果
     * 返回条件：自定义sql 查询结果字段为空值result_sqlcheck is null 并且 查询时间与当前执行时间差小于"100"（暂定）分钟,首次为扫到结果的查询会重复扫 直到超过时间范围，
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void getQueryData() {

        System.out.println("定时巡检");
        List<RunRecordEntity> recordByQueryTime = record.getRecordByQueryTime();
        //TODO  超时sql告警

        if (recordByQueryTime.size() == 0) {
            log.error("未检测到异步查询");
        } else {
            //模拟获取返回值
            Random random = new Random();
            for (RunRecordEntity runRecordEntity : recordByQueryTime) {
                if (StringUtils.isBlank(runRecordEntity.getResultSqlcheck())) {
                    runRecordEntity.setResultSqlcheck(random.nextInt(1000) + 1 + "");
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
    public void warner() {

        List<RunRecordEntity> probRecord = record.getProbRecord();
        if (probRecord.size() == 0) {
            return;
        }
        //构建全局缓存 表的 变量和返回值对应关系
        for (RunRecordEntity re : probRecord) {
            //变量查询返回值 数组
            String[] result = re.getResultSqlcheck().split(",");
            //变量名称数组   [cnt,sum]
            String[] variate = re.getVariates().split(",");
            Map<String, Double> recordMap = TABLE_WARNER.get(re.getTableName());
            if (recordMap == null && variate.length==result.length) {
                HashMap<String, Double> tm = new HashMap<>(32);
                for (int i = 0; i < result.length; i++) {
                    tm.put(variate[i], Double.parseDouble(result[i]));
                }
                TABLE_WARNER.put(re.getTableName(), tm);

            } else {
                //已存在则按key 更新缓存
                for (int i = 0; i < result.length; i++) {
                    recordMap.put(variate[i], Double.parseDouble(result[i]));
                }
            }
            re.setCheckFlag(1);
            record.updateById(re);
        }

        List<String> tbList = distinctTable(probRecord);
        //检测是否发送报警逻辑
        for (String tableName : tbList) {
            //获取解析器 表粒度 避免不同表相同变量名称冲突解析错误
            ScriptEngine parseEngine = CommnUtils.getParseEngine();
            //表对应的所有返回数据的缓存map
            Map<String, Double> tableResultMap = TABLE_WARNER.get(tableName);

            //获取拼接变量和表达式的对应关系json
            JSONObject exe = this.getVariatesAndExe(tableName);
            //获取拼接变量
            Set<String> rvariates = exe.keySet();

            for (String variates : rvariates) {
                //变量拼接key对应的表达式
                String vexe = (String) exe.get(variates);
                //拼接变量展开 是否在缓存map中命中该表达式的所有变量
                String[] vArr = variates.split(",");
                if (! tableResultMap.keySet().containsAll(Arrays.asList(vArr))) {
                    log.error("全能局缓存为包含表达式全部变量" + Arrays.asList(vArr).toString() + ",过本次执行");
                    continue;
                }

                //变量和变量的查询返回值 添加到解析器中
                pushVitAndData2Engine(parseEngine,vArr,tableResultMap);

                boolean logicExpressionResult = CommnUtils.getLogicExpressionResult(parseEngine, vexe);
                if (!logicExpressionResult) {
                    //报警处理
                    System.out.println(vexe + ":检测异常");
                }else{
                    System.out.println("表达式式："+vexe+"正常");
                }
            }
        }
        System.out.println("map数据更新:" + TABLE_WARNER + "," + TABLE_WARNER.size());

    }

    public JSONObject getVariatesAndExe(String tableName) {
        String sqlJson = record.getTableByName(tableName).getSqlCheck();
        JSONObject sj = JSON.parseObject(sqlJson);
        //变量和表达式关系
        return sj.getJSONObject("exe");
    }

    public List<String> distinctTable(List<RunRecordEntity> t){
        return  t.stream().map(x -> x.getTableName()).distinct().collect(Collectors.toList());

    }

    public void pushVitAndData2Engine(ScriptEngine se, String[] arr,  Map<String, Double> map){
        for (String varr : arr) {
            double queryData = map.get(arr);
            se.put(varr,queryData);
        }

    }

}