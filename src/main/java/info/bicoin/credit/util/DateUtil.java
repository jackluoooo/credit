package info.bicoin.credit.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * file:DateUtil
 * <p>
 * 文件简要说明
 *
 * @author 2018-06-22 hgl 创建初始版本
 * @version V1.0  简要版本说明
 * @par 版权信息：
 * 2018 Copyright 河南艾鹿网络科技有限公司 All Rights Reserved.
 */
@Slf4j
public class DateUtil {

    /**
     * 显示日期的格式,yyyyMMddHHmmssSSS
     */
    public static final String TIMESSS_STR_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String FORMAT_1 = "yyyy-MM-dd HH:mm:ss SSS";
    public static final String FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_3 = "yyyy-MM-dd";

    public static final String FORMAT_ZHCN_1 = "yyyy年MM月dd日 HH:mm:ss";


    /**
     * 时间转为化为字符串
     * <p>
     * 格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getDateToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_2);
        Date date = new Date();
        String str = dateFormat.format(date);
        return str;
    }


    /**
     * 时间转为化为字符串
     * <p>
     * 格式为：yyyy-MM-dd
     *
     * @return
     */
    public static String getDayDateToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_3);
        Date date = new Date();
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * 转换 为指定时间格式
     */
    public static String getLongDateToString(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(time);
    }

    /**
     * 把 Date 转换 为指定时间格式  字符串
     */
    public static String getDateToString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 把 String 格式 的 Date 转换 为 Date
     */
    public static Date getStringToDate(String date, String format) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    //大额转账 报告生成 专用
    public static String getBuildReportTime() {
        try {
            Calendar cl = Calendar.getInstance();
            String endTime = getDateToString(cl.getTime(), FORMAT_2);

            cl.add(Calendar.HOUR_OF_DAY, -24);
            String startTime = getDateToString(cl.getTime(), FORMAT_2);

            return startTime + "—" + endTime;

        } catch (Exception e) {
            log.error(" 异常：", e);
            return "";
        }

    }

    /**
     * 当前时间 超过 指定时间 指点毫秒数 当前时间 超过 time  passValue毫秒
     */
    public static boolean compareSurpass(long time, long passValue) {
        if ((Calendar.getInstance().getTimeInMillis() - time) > passValue) {
            return true;
        }
        return false;
    }


    /**
     * 是否 在 夜间模式 时间范围内
     */
//    public static boolean isInNight(){
//        Calendar cl = Calendar.getInstance();
//        if(Dic.NIGHT_TIME_HOUR.contains("["+String.valueOf(cl.get(Calendar.HOUR_OF_DAY)) + "]")){
//            return true;
//        }
//        return false;
//    }

    //获取前一天的凌晨时间
    public static Long getLastDateNight() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String format = sdf.format(new Date());
        try {
            Date parse = sdf.parse(format);
            return parse.getTime() - 24 * 3600;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取整数时间
    public static boolean  differenceOneDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date2);

        return cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)
                &&cal1.get(Calendar.MONTH)==cal2.get(Calendar.MONTH)
                &&(cal1.get(Calendar.DAY_OF_MONTH)-cal2.get(Calendar.DAY_OF_MONTH)==1);

    }


    public static boolean  differenceZeroDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date2);

        return cal1.get(Calendar.YEAR)==cal1.get(Calendar.YEAR)
                &&cal1.get(Calendar.MONTH)==cal2.get(Calendar.MONTH)
                &&(cal1.get(Calendar.DAY_OF_MONTH)-cal2.get(Calendar.DAY_OF_MONTH)==0);

    }

    public static void main(String[] args) {
        System.out.println(getDayDateToString());
    }

}
