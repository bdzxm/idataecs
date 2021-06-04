package com.rm.idataecs.idataecs.test.dto;

import lombok.Data;

/**
 * @program: idataecs
 * @description: 封账后端通用返回信息体
 * @author: xumeng.zhao
 * @create: 2021-06-03 15:34
 */
@Data
public class CommonResult {

    /**
     * 状态提示 0成功 1失败
     */
    private int code;
    /**
     * 失败日志
     */
    private String e;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

    public CommonResult() {
    }

    public CommonResult(int code, String msg, Object data) {
        this.code = code;
        this.e = e;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult(int code, String e, String msg, Object data) {
        this.code = code;
        this.e = e;
        this.msg = msg;
        this.data = data;
    }

    public static  CommonResult  success(int code, String msg, Object data) {
       return  new CommonResult(code,msg,data);
    }

    public static  CommonResult  failed(int code,String e, String msg, Object data) {
        return  new CommonResult(code,e,msg,data);
    }
}