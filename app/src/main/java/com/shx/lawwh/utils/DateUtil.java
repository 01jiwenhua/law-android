package com.shx.lawwh.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 邵鸿轩 on 2016/11/25.
 */

public class DateUtil {
    /**
     * 得到某年某月的第一天
     *
     * @param year
     * @param month (真实月份减一)
     * @return
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * 得到某年某月的最后一天
     *
     * @param year
     * @param month (真实月份减一)
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }

    /**
     * 获取当前日期的年份
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前日期的月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取当前日期的日
     *
     * @return
     */
    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期的年份
     *
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取指定日期的月份
     *
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取指定日期的日
     *
     * @return
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取任意日期对应的是周几
     *
     * @param date
     * @return 返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
     */

    public static int getDayofweek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 将日期字符串转成日期
     *
     * @param strDate 字符串日期
     * @return java.util.date日期类型
     */
    public static String parseDate(Date strDate) {
        if(strDate!=null) {
            SimpleDateFormat sdf1 = new SimpleDateFormat(
                    "yyyy年MM月dd日");
            String returnDate = sdf1.format(strDate);
            return returnDate;
        }else{
            return "";
        }
    }

    /**
     * 根据不同的type格式化为不同格式日期
     *
     * @param timestamp
     * @return
     */
    public static String parseDate(long timestamp) {
        if (timestamp == 0L) {
            return "";
        } else {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            return sdf2.format(new Date(timestamp));
        }
    }

    /**
     * @param timestamp
     * @return
     */
    public static String parseTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }
    public static String parseDateTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }


    /**
     * 根据分钟得到天数
     */
    public static String minuteToDay(int minute) {
        int hour = minute / 60;
        int day = hour / 24;
        String moneyStr = "%d日达";
        return String.format(moneyStr, day);
    }


    /**
     * 根据分钟得到天数
     * */
    public static String getDay(int minute){
        int hour=minute/60;
        int day=hour/24;
        return String.valueOf(day);
    }


    /**
     * 得到到达日期
     */
    public static String getArriveDate(long sendDate, int minute) {
        long millisecond = minute * 60 * 1000;
        long temp = sendDate + millisecond;
        return parseDate(temp);
    }

    /**
     * 得到增加天数
     * */
    public static String getDate(int dateOne, int dateTwo){
        StringBuilder sb=new StringBuilder();
        int dayOne=dateOne/60/24;
        int dayTwo=dateTwo/60/24;
        int difference=dayTwo-dayOne;
        if(difference==0){
            return "";
        }else if(difference>0){
            sb.append("+");
        }
        sb.append(dayTwo - dayOne);
        sb.append("天");
        return sb.toString();
    }



    /**
     * 计算相差多少天
     * */
    public static String daysBetween(String smdate, String bdate){
        long between_days=0l;
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days=(time2-time1)/(1000*3600*24);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(between_days==0){
            return "(1天)";
        }else {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(between_days);
            sb.append("天");
            sb.append(")");
            return sb.toString();
        }
    }
    /**
     * 计算相差多少天
     * */
    public static String calculate2Days(String smdate, String bdate) {
        int between_days=0;
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days= (int) ((time2-time1)/(1000*3600*24));
        }catch (Exception e){
            e.printStackTrace();
        }
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(between_days+1);
            sb.append("天");
            sb.append(")");
            return sb.toString();
        }

    /**
     * 计算发布时间
     * */
    public static String calculatePublishDate(long publishTime){
        long currentTime= System.currentTimeMillis();
        //用当前时间减去发布的时间得到相差的时间
        long time=currentTime-publishTime;
        int betweenDay=(int)(time/(24*60*60*1000));
        StringBuilder strBuilder=new StringBuilder();
        if(betweenDay<=1){
            strBuilder.append("当天");
        }else if(betweenDay<7){
            strBuilder.append(betweenDay);
            strBuilder.append("天前");
        }else if(betweenDay<=30){
            int week=betweenDay/7;
            strBuilder.append(week);
            strBuilder.append("周前");
        }else if(betweenDay<=365){
            int month=betweenDay/30;
            strBuilder.append(month);
            strBuilder.append("个月前");
        }else if(betweenDay>365){
            int year=betweenDay/365;
            strBuilder.append(year);
            strBuilder.append("年前");
        }
        return strBuilder.toString();
    }

    /**
     * 起始时间是否早于(小于)终止时间
     * @param startDateStr 开始时间
     * @param endDateStr 结束时间
     * @return 是
     */
    public static boolean compareDate(String startDateStr, String endDateStr) {
        boolean flag = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);
            if (startDate.compareTo(endDate) > 0) {
                //结束日期早于开始日期,后者小
                flag = true;
            }
        } catch (ParseException e) {
            System.out.println("比较失败，原因：" + e.getMessage());
        }
        return flag;
    }

    /**
     * 计算相差多少天
     * */
    public static int calculateDays(String smdate, String bdate){
        long between_days=0l;
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days=(time2-time1)/(1000*3600*24);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(between_days==0){
            return 1;
        }else {
            return (int)between_days;
        }
    }

    /**
     * 计算两个时间字符串之间相隔几天
     * @param endTime
     * @param startTime
     * @return
     */
    public static int calculate2Days(Date startTime, Date endTime){
        long between_days=0l;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startTime);
            long time1 = cal.getTimeInMillis();
            cal.setTime(endTime);
            long time2 = cal.getTimeInMillis();
            between_days=(time2-time1)/(1000*3600*24);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(between_days==0){
            return 1;
        }else {
            return (int)between_days;
        }
    }
    /**
     * 计算两个时间字符串之间相隔几天
     * @param endTime
     * @param startTime
     * @return
     */
    public static int calculateDaysBetween(Date startTime, Date endTime){
        int between_days;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startTime);
            long time1 = cal.getTimeInMillis();
            cal.setTime(endTime);
            long time2 = cal.getTimeInMillis();
            between_days= (int) ((time2-time1)/(1000*3600*24));
            return between_days+1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 计算相差多少天
     * */
    public static int calculateDaysBetween(String smdate, String bdate){
        int between_days;
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days= (int) ((time2-time1)/(1000*3600*24));
            return between_days+1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将2015-2-6格式的字符串转化为时间戳
     * */
    public static long convertTimestamp(String date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date time=null;
        try {
            time= sdf.parse(date);
            return time.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前时间下一天的日期
     * */
    public static String getTomorrowDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();// 获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();   //这个时间就是日期往后推一天的结果
        return  df.format(date);
    }

    public static boolean isInDate(String date, long strDateBegin, long strDateEnd) {
        long tempDate = convertTimestamp(date);
        if((tempDate>strDateBegin && tempDate<strDateEnd)||(tempDate==strDateBegin) || (tempDate==strDateEnd)){
            return true;
        }else{
            return false;
        }
    }
}
