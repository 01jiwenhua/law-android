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
    public static String URL="http://60.210.40.196:25018/law-server/files/%s.html";
    //版本说明
    public static String VERSIONURL="http://60.210.40.196:25018/law-server/version.jsp";
    //服务协议
    public static String SERVICEITEMURL="http://60.210.40.196:25018/law-server/serviceitem.jsp";
//    帮助
    public static String QAURL="http://60.210.40.196:25018/law-server/qa.jsp";
    public static String PDFURL="http://60.210.40.196:25018/law-server/files/%s.pdf";
//    public static String BASEURL="http://192.168.8.120:8090";
    //测试地址
    public static String TEST_URL="http://192.168.8.127:8080";
//    public static String TEST_URL="http://192.168.1.127:8080";
    //生产地址
    public static String PRODUCE_URL="http://60.210.40.196:25018/law-server";
    public static String BASEURL=PRODUCE_URL;
}
