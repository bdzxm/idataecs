package com.rm.idataecs.idataecs.test.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE(0),
    FAMALE(1);
    @EnumValue
    @JsonValue
    private final Integer code;


}
