package com.rm.idataecs.idataecs.adapter.impl;

import com.rm.idataecs.idataecs.adapter.IdataEcsMesInterface;
import com.rm.idataecs.idataecs.monitor.entity.SendMessageEntity;
import com.rm.idataecs.idataecs.util.CommonException;
import org.springframework.stereotype.Repository;

/**
 * @program: idataecs
 * @description: 适配器实现类
 * @author: xumeng.zhao
 * @create: 2021-06-01 16:39
 */
public class IdataEcsMesImpl implements IdataEcsMesInterface {

    /**
     * 短信报警
     * @param sendMessageEntity
     * @return
     * @throws Exception
     */
    @Override
    public Object send2Message(SendMessageEntity sendMessageEntity) throws Exception {

        //TODO 引入外部框架逻辑


        try {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 电话报警
     * @param sendMessageEntity
     * @return
     * @throws Exception
     */
    @Override
    public Object send2Phone(SendMessageEntity sendMessageEntity) throws Exception {

        //TODO 引入外部框架逻辑

        return null;
    }

    /**
     * wx
     * @param sendMessageEntity
     * @return
     * @throws Exception
     */
    @Override
    public Object send2WX(SendMessageEntity sendMessageEntity) {
        //TODO 引入外部框架逻辑
        String msg ="注意查收测试信息";
        try {



        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getCause().toString();

        }
        sendMessageEntity.setMsgException(msg);
        return sendMessageEntity;
    }
}