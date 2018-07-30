package com.yc.ycutilslibrary.phone;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * app版本相关信息
 */

public class YcUtilVersion {
    /**
     * 获取版本号
     *
     * @param application
     * @return
     */
    public static int getVersionCode(Application application) {
        try {
            PackageInfo packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取版本名
     *
     * @param application
     * @return
     */
    public static String getVersionName(Application application) {
        try {
            PackageInfo packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取包名
     *
     * @param application
     * @return
     */
    public static String getPackageName(Application application) {
        return application.getPackageName();
    }
}
