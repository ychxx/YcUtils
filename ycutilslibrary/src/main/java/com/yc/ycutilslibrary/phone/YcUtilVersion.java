package com.yc.ycutilslibrary.phone;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.yc.ycutilslibrary.YcUtilsInit;
import com.yc.ycutilslibrary.common.YcLog;

import java.io.File;

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
    /**
     * 安装apk
     *
     * @param context
     * @param file
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data = FileProvider.getUriForFile(context, getPackageName(context) +".fileprovider", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
