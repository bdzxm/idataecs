package com.rm.idataecs.idataecs.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoxumeng
 */

@AllArgsConstructor
@Getter
public enum NumCheckEnum {

    off(0, "关闭"),
    on(1, "开启");

    private int code;
    private String desc;


}
