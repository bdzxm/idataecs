package com.rm.idataecs.idataecs;

import com.rm.idataecs.idataecs.test.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-05-27 14:24
 */

@SpringBootTest
public class Test {

    @Autowired
    UserMapper UserMapper;

    private String tbname;
    @org.junit.jupiter.api.Test
    public  void test() {



//        List<User> users = UserMapper.selectList(Wrappers.<User>lambdaQuery());
//        int insert = UserMapper.insert(user);
//        System.out.println(insert);


    }

}