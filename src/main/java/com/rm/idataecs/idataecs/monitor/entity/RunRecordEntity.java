package com.rm.idataecs.idataecs.monitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-06-08 09:58
 */
@Data
@TableName("dataM_everyday_run_record")
public class RunRecordEntity {

    @TableId(type = IdType.AUTO)
    private int id;

    private long queryId;

    private String queryTime;

    private String resultSqlcheck;

    private String resultGeneral;

    private String tableName;

    private String variates;

    private int checkFlag;

}