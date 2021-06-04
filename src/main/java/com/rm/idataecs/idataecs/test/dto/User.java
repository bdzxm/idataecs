package com.rm.idataecs.idataecs.test.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rm.idataecs.idataecs.test.constant.Gender;
import lombok.Data;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-05-27 14:17
 */
@Data
@TableName("user")
public class User {

    @TableId(type=IdType.AUTO)
    private int id;
    private String name;
    private int age;
    private String email;
    private Gender gender;
    private Addition addition;

}