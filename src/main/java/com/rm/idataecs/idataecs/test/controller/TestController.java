package com.rm.idataecs.idataecs.test.controller;

import com.rm.idataecs.idataecs.test.constant.Gender;
import com.rm.idataecs.idataecs.test.dto.Addition;
import com.rm.idataecs.idataecs.test.dto.CommonResult;
import com.rm.idataecs.idataecs.test.dto.User;
import com.rm.idataecs.idataecs.test.services.ServicesTest;
import com.rm.idataecs.idataecs.constants.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-05-26 15:40
 */
@RestController
public class TestController {
    @Autowired
    ServicesTest servicesTest;

    @PostMapping("/test")
    public String  HelloTest(@RequestBody User user){

        System.out.println(user);
        int i = servicesTest.saveUser(user);
        Addition addition = user.getAddition();
        int id = user.getId();
        System.out.println("自增id："+id);
        System.out.println(i);
        addition.setUid(id);
        servicesTest.inserAddition(addition);
        return "hello";

    }
    @GetMapping("/test3")
    public void Test3(@RequestParam(value = "id",required = true) int id,User user){

        System.out.println("GetMapping:"+id);
//        user.setId(id);
        System.out.println(user);


    }

    @GetMapping("/test2")
    public String  HelloTest2(){

        System.out.println("枚举"+Gender.MALE.getCode());

        return "hello";

    }


    @GetMapping("/resultTest")
    public CommonResult resultTest(User user){

        return new CommonResult(ResultStatus.Success.getCode(),new RuntimeException("").getMessage(),"",user);

    }




}