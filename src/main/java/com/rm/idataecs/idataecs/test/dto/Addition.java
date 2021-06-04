package com.rm.idataecs.idataecs.test.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-05-28 10:47
 */
@Data
@TableName("addition")
public class Addition {
    @TableId(type = IdType.AUTO)
    private int id;
    private int uid;
    private String address;
    private double height;
    private double weight;
}