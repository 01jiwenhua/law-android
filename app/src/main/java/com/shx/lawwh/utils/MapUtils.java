package com.shx.lawwh.utils;

import java.util.Map;

/**
 * Created by 邵鸿轩 on 2016/4/20.
 */
public class MapUtils {
    /**
     * 从map中获取字符串
     *
     * @param map
     * @param key
     * @return
     */
    public static String getStringInMap(Map<String, Object> map, String key) {
        if (map.containsKey(key)) {
            return (String) map.get(key);
        }
        return "";
    }

    /**
     * 从map中获取数字
     *
     * @param map
     * @param key
     * @return
     */
    public static int getIntInMap(Map<String, Object> map, String key) {
        try {
            if (map.containsKey(key)) {
                return Integer.valueOf((String) map.get(key));
            } else {
                return 0;
            }
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * 从map中获取对象
     *
     * @param map
     * @param key
     * @return
     */
    public static Object getObjectInMap(Map<String, Object> map, String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }
}
