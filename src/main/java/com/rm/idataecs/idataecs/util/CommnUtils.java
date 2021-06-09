package com.rm.idataecs.idataecs.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rm.idataecs.idataecs.test.dto.CommonResult;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @program: idataecs
 * @description: t通用工具类
 * @author: xumeng.zhao
 * @create: 2021-06-01 17:27
 */
@Slf4j
public class CommnUtils {


    /**
     * \
     * 对象判空 返回ture 为非空
     *
     * @param obj
     * @return
     */
    public static boolean isObjBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 判断当前对象 是否是空白（为null 或者 size为0 或者length为0 ）
     *
     * @param obj 待判断的对象
     * @return 为空 : true
     */
    public static boolean isBlank(Object obj) {
        boolean res = false;
        if (null == obj) {
            res = true;
        } else if (obj instanceof Map) {
            res = ((Map) obj).size() == 0;
        } else if (obj instanceof Collection) {
            res = ((Collection) obj).size() == 0;
        } else if (obj instanceof String) {
            res = StringUtils.isBlank((String) obj);
        } else if (obj instanceof Object[]) {
            res = ((Object[]) obj).length == 0;
        }
        return res;
    }


    public static <T> void requirePss(T inner, Predicate<T> predicate, String msg) {
        if (predicate.test(inner)) {
            return;
        }
        log.error(inner + msg);

    }

    /**
     * 当前时间
     *
     * @return
     */
    public static String getCurrentTime(String pat) {
        return new SimpleDateFormat(pat).format(new Date());
    }


    public static<T> void requirePass(Predicate<T> ck, T t, CommonResult cr, String msg) {
        if (!ck.test(t)) {
            return;
        }
        cr.setMsg(msg);

    }

    public static boolean sqlParse(String sql){
     boolean requerSql =false;





     return requerSql;

    }




}