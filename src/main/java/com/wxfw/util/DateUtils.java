package com.wxfw.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtils
 *
 * @author gaohw
 * @date 2020/3/19
 */
public class DateUtils {
    private static String fm = "yyyy-MM-dd";
    private static String fm1 = "yyyy-MM";
    private static String fm2 = "yyyyMMdd";
    private static String fm3 = "yyyy-MM-dd HH:mm:ss";
    private static String fm4 = "yyyyMM";
    private static String fm5 = "yyyy-MM-ddHH:mm:ss";
    private static String fm6 = "yyyy年MM月";
    private static String fm7 = "yyyy年MM月dd日";

    public static Date stringToDate(String dateStr) throws Exception {
        String[] arr = null;
        if (dateStr != null) {
            arr = dateStr.split("-");
        }
        String format = fm;
        if (dateStr.contains(":") && dateStr.contains(" ")) {
            format = fm3;
        } else if (dateStr.contains(":")) {
            format = fm5;
        }
        if (arr != null && arr.length == 2) {
            return new SimpleDateFormat(format).parse(dateStr + "-01");
        } else {
            return new SimpleDateFormat(format).parse(dateStr);
        }
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(fm).format(date);
    }

    public static String dateToStringYYMMDD(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(fm2).format(date);
    }

    public static String dateToStringMM(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(fm3).format(date);
    }

    public static Date addMonths(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);
        return calendar.getTime();
    }

    public static Date addDates(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, num);
        return calendar.getTime();
    }

    public static Date addSecond(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 1);
        return calendar.getTime();
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.MONTH + 1);
        return day;
    }

    public static String getMonStr(Date date) {
        String mon = String.format("%tm", date);
        return mon;
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.YEAR);
        return day;
    }

    /**
     * 获取当日0点
     *
     * @return
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取当日23点
     *
     * @return
     */
    public static Date getnowEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static Date timeStamp2Date(String seconds,String format) throws ParseException {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return null;
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String time = sdf.format(new Date(Long.valueOf(seconds)));
        return sdf.parse(time);
    }
    /**
     * 日期格式字符串转换成时间戳
     * @param date_str 字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     * @return
     */
    public static String timeStamp(){
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);
        return t;
    }


}
