package com.wjh.common.core.util;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

public class DateUtil {
    private static final String DAY_FORMAT_STRING="yyyy-MM-dd";

    public static String formatCurrentDate(String pattern){
        return DateFormatUtils.format(new Date(),pattern);
    }
    public static String formatCurrentDate(){
        return DateFormatUtils.format(new Date(),DAY_FORMAT_STRING);
    }
}
