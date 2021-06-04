package com.rm.idataecs.idataecs.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rm.idataecs.idataecs.test.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<User> {

//    @Options(useGeneratedKeys = true,keyProperty = "id")
//    @Insert("insert into user(name,age,email,gender) values(#{name},#{age},#{email},#{gender})")
    int insertUser2(User user);

    List<User> selectAll();

}
