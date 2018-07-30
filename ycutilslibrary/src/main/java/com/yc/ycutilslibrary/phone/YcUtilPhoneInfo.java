package com.yc.ycutilslibrary.phone;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.yc.ycutilslibrary.common.YcEmpty;

/**
 * 手机信息
 */

public class YcUtilPhoneInfo {
    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取手机IMEI(设备id)
     * 需要Manifest.permission.READ_PHONE_STATE权限
     *
     * @param context
     * @return
     */
    public static final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            return YcEmpty.getString(telephonyManager.getDeviceId());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取手机Sn（设备序列号）
     */
    public static String getSn(Context context) {
        return android.os.Build.SERIAL;
    }

    /**
     * 获取手机IMSI（sim卡序列号）
     * 需要Manifest.permission.READ_PHONE_STATE权限
     */
    public static String getIMSI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            //获取IMSI号
            return YcEmpty.getString(telephonyManager.getSubscriberId());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
