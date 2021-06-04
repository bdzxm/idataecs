package com.rm.idataecs.idataecs.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoxumeng
 */

@Getter
@AllArgsConstructor
public enum ResultStatus {

    Success(0,"成功"),
    fail(1,"失败");

    private final Integer code;
    private final String desc;


}
