package com.rm.idataecs.idataecs.adapter;

import com.rm.idataecs.idataecs.monitor.entity.SendMessageEntity;

/**
 * 接入报警框架 适配器接口
 * @author zhaoxumeng
 */
public interface IdataEcsMesInterface {

    /**
     * 短信
     * @param sendMessageEntity
     * @return
     * @throws Exception
     */
    Object send2Message(SendMessageEntity sendMessageEntity)throws Exception;


    /**
     * 电话
     * @param sendMessageEntity
     * @return
     * @throws Exception
     */
    Object send2Phone(SendMessageEntity sendMessageEntity)throws Exception;


    /**
     * wx
     * @param sendMessageEntity
     * @return
     * @throws Exception
     */
    Object send2WX(SendMessageEntity sendMessageEntity);

}
