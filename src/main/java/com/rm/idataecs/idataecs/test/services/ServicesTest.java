package com.rm.idataecs.idataecs.test.services;

import com.rm.idataecs.idataecs.test.dto.Addition;
import com.rm.idataecs.idataecs.test.dto.User;
import com.rm.idataecs.idataecs.test.mapper.AdditionMapper;
import com.rm.idataecs.idataecs.test.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-05-27 14:20
 */
@Service
public class ServicesTest {

    @Autowired
    UserMapper userMapper;
    @Autowired
    AdditionMapper additionMapper;

    public int saveUser(User user){
//        int insert = userMapper.insert(user);
//        System.out.println("插入返回值"+insert);
        userMapper.insertUser2(user);
        return  user.getId();

    }

    public User getUser(int id){
        return userMapper.selectById(id);

    }


    public void inserAddition(Addition addition){
        additionMapper.insert(addition);

    }

    public  List<User> selectAll(){
        return  userMapper.selectAll() ;


    }




}