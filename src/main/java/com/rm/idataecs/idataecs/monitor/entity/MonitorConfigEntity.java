package com.rm.idataecs.idataecs.monitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rm.idataecs.idataecs.monitor.dto.MonitorConfigDTO;
import lombok.Data;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-05-31 18:53
 */
@Data
@TableName("dataM_meta_conf")
public class MonitorConfigEntity {

    @TableId(type = IdType.AUTO)
    private int id;

    private String dt;

    private String tableName;

    private String generalCheck;

    private String sqlCheck;

    private String createTime;

    /**
     * 启用状态 1是0否
     */
    private int status;

    /**
     * 重复值检测字段
     */
    private String repeatedCheck;

    /**
     * 是否开启数字异常值检测 1是 0否
     */
    private int numCheck;

    /**
     * 是否开启增量大小检测 1是 0否
     */
    private int incrementCheck;



//    public MonitorConfigEntity(MonitorConfigDTO mc) {
//        this.id = mc.getId();
//        this.dt = mc.getDt();
//        this.tableName = mc.getTableName();
//        this.generalCheck = (String) mc.getGeneralCheck();
//        this.sqlCheck = (String) mc.getSqlCheck();
//        this.repeatedCheck = mc.getRepeatedCheck();
//        this.numCheck = mc.getNumCheckEnum().getCode();
//        this.incrementCheck = mc.getIncrementCheckEnum().getCode();
//        this.status = 1;
//        this.createTime=mc.getTime();
//
//    }
}