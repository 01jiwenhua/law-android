package com.shx.lawwh.common;

/**
 * Created by 邵鸿轩 on 2016/11/15.
 */
public class SystemConfig {
    /**
     * 打印日志的开关,生产版本时改为false
     */
    public static boolean LOGFLAG = true;
    public static String appName="危化";
    public static String URL="http://60.210.40.196:8086/laws/%s.html";
//    public static String BASEURL="http://192.168.8.120:8090";
    //测试地址
    public static String TEST_URL="http://192.168.1.127:8080";
    //生产地址
    public static String PRODUCE_URL="http://60.210.40.196:25018/law-server";
    public static String BASEURL=PRODUCE_URL;
}
