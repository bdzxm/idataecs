package com.rm.idataecs.idataecs.monitor.entity;

import com.rm.idataecs.idataecs.util.CommonException;
import lombok.Data;

/**
 * @program: idataecs
 * @description:
 * @author: xumeng.zhao
 * @create: 2021-06-01 16:31
 */
@Data
public class SendMessageEntity {
    /**
     * 消息体
     */
    private String text;

    /**
     * title
     */
    private String title;

    /**
     * 封装邮件异常
     */
    private String MsgException;


    public SendMessageEntity(String text, String title) {
        this.text = text;
        this.title = title;
    }
}