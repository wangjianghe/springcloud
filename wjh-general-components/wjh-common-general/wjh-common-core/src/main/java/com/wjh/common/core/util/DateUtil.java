package com.wjh.common.core.util;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

public class DateUtil {
    private static final String DAY_FORMAT_STRING="yyyy-MM-dd";
    private static final String TIME_FORMAT_STRING="yyyy-MM-dd HH:mm:ss";

    public static String formatCurrentDate(String pattern){
        return DateFormatUtils.format(new Date(),pattern);
    }
    public static String formatCurrentDate(){
        return DateFormatUtils.format(new Date(),DAY_FORMAT_STRING);
    }
}
