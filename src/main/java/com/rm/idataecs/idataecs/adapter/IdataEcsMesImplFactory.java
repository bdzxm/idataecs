package com.rm.idataecs.idataecs.adapter;

import com.rm.idataecs.idataecs.adapter.IdataEcsMesInterface;
import com.rm.idataecs.idataecs.adapter.impl.IdataEcsMesImpl;
import org.springframework.stereotype.Repository;

/**
 * @program: idataecs
 * @description:消息推送工厂类
 * @author: xumeng.zhao
 * @create: 2021-06-04 13:14
 */
@Repository
public class IdataEcsMesImplFactory {
    private IdataEcsMesInterface mesImpl=null;

    /**
     * 目前只能接入upush 懒得适配了 日
     * @param mesType
     * @return
     */
        public IdataEcsMesInterface getIdataEcsMesImpl(String mesType) {

            if (mesType.toLowerCase().equals("upush")) {
                mesImpl= new IdataEcsMesImpl();
            }
            return mesImpl;

        }





}