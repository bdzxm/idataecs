package com.rm.idataecs.idataecs.monitor.entity;

import lombok.Data;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-06-04 20:22
 */
@Data
public class SqlCheckEntity {

    private String sql;

    private String function;

}