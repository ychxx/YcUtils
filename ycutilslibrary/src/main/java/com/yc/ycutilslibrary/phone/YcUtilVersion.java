package com.yc.ycutilslibrary.phone;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.yc.ycutilslibrary.YcUtilsInit;
import com.yc.ycutilslibrary.common.YcLog;

/**
 * app版本相关信息
 */

public class YcUtilVersion {
    public static int getVersionCode() {
        if (YcUtilsInit.mApplication == null) {
            YcLog.e("请先初始化，YcUtilsInit.init(Application)");
            return -1;
        }
        return getVersionCode(YcUtilsInit.mApplication);
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getVersionName() {
        if (YcUtilsInit.mApplication == null) {
            YcLog.e("请先初始化，YcUtilsInit.init(Application)");
            return "";
        }
        return getVersionName(YcUtilsInit.mApplication);
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPackageName() {
        if (YcUtilsInit.mApplication == null) {
            YcLog.e("请先初始化，YcUtilsInit.init(Application)");
            return "";
        }
        return getPackageName(YcUtilsInit.mApplication);
    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }
}
