package com.rm.idataecs.idataecs.util;

import com.rm.idataecs.idataecs.constants.ResultStatus;
import lombok.Data;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-06-03 16:31
 */
@Data
public class CommonException extends RuntimeException {

    private int  code;

    private String eMsg;

}