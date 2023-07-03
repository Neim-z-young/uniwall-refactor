package com.oyoungy.util;


import java.util.Date;

/**
 * 日期时间工具类
 * create by oyoungy on 2019/10/27
 */
public class DateUtils {
    /**
     * 从unix时间戳转化为date对象
     * 注意：
     *      时间戳以秒为时间单位
     * @param epoch
     * @return
     */
    public static Date getDateFromEpoch(Integer epoch){
        //秒单位转化为毫秒单位
        return new Date((long)epoch *1000);
    }

    /**
     * date对象转化为unix时间戳
     * 以秒为单位
     * @param date
     * @return
     */
    public static Integer getEpochFromDate(Date date){
        return Math.toIntExact(date.getTime() / 1000);
    }

}
