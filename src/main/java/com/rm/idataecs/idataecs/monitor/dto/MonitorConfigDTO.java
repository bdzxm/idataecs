package com.rm.idataecs.idataecs.monitor.dto;

import com.rm.idataecs.idataecs.constants.IncrementCheckEnum;
import com.rm.idataecs.idataecs.constants.NumCheckEnum;
import lombok.Data;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-05-31 19:33
 */
@Data
public class MonitorConfigDTO {

    private int id;

    private String dt;

    private String tableName;

    private Object generalCheck;

    private Object sqlCheck;

    private String time;


    /**
     * 重复值检测字段
     */
    private String repeatedCheck;

    /**
     * 是否开启数字异常值检测 1是 0否
     */
    private NumCheckEnum numCheckEnum;

    /**
     * 是否开启增量大小检测 1是 0否
     */
    private IncrementCheckEnum incrementCheckEnum;

}