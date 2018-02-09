package com.shx.lawwh.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by 邵鸿轩 on 2016/11/24.
 */

public class DeviceUtils {
    /**
     * dp转px
     * @param context
     * @param dp
     * @return
     */
    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转dp
     * @param context
     * @param px
     * @return
     */
    public int px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取当前版本名称
     *
     * @param activity
     * @return
     */
    public static String getVersionName(Context activity) {
        PackageInfo info = null;
        try {
            info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(info!=null){
            // 当前应用的版本名称
            String versionName = info.versionName;
            return versionName;
        }else{
            return "";
        }
    }

    /**
     * 获取当前版本
     *
     * @param activity
     * @return
     */
    public static int getVersionCode(Activity activity) {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            // 当前应用的版本名称
            int versionCode = info.versionCode;
            return versionCode;
//            // 当前版本的版本号
//
//
//            // 当前版本的包名
//            String packageNames = info.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
