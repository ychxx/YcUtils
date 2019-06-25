package com.yc.ycutilslibrary.bluetooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.constant.YcBluetoothExceptionErrorEnum;
import com.yc.ycutilslibrary.exception.YcBltException;
import com.yc.ycutilslibrary.permissions.YcUtilPermission;
import com.yc.ycutilslibrary.toactivity.YcForResult;
import com.yc.ycutilslibrary.toactivity.YcForResultBean;

import io.reactivex.functions.Consumer;

/**
 * 蓝牙帮助类
 * 1.检测蓝牙是否开启
 * 2.开启蓝牙
 */
public class YcBluetoothHelper {
    public static boolean isHasBltPermission(Activity activity) {
        return YcUtilPermission.newInstance(activity).isHasPermission(YcUtilPermission.PERMISSION_BLT);
    }

    public static void requestBltPermission(Activity activity, YcUtilPermission.FailCall failCall) throws YcBltException {
        requestBltPermission(activity, null, failCall);
    }

    public static void requestBltPermission(Activity activity, YcUtilPermission.SuccessCall successCall) throws YcBltException {
        requestBltPermission(activity, successCall, null);
    }

    public static void requestBltPermission(Activity activity, YcUtilPermission.SuccessCall successCall, YcUtilPermission.FailCall failCall) throws YcBltException {
        YcUtilPermission.newInstance(activity)
                .addPermissions(YcUtilPermission.PERMISSION_BLT)
                .setSuccessCall(() -> {
                    if (successCall != null)
                        successCall.onCall();
                })
                .setFailCall(() -> {
                    if (failCall != null) {
                        failCall.onCall();
                    }
                }).start();
    }

    public static void openBlt(Activity activity) throws YcBltException {
        openBlt(activity, new Consumer<YcForResultBean>() {
            @Override
            public void accept(YcForResultBean ycForResultBean) throws Exception {
                if (ycForResultBean.isSuccess()) {
                    YcLog.e("开启蓝牙功能成功");
                } else {
                    throw new YcBltException("开启蓝牙功能失败", YcBluetoothExceptionErrorEnum.ERROR_OPEN_BLT_FAIL);
                }
            }
        });
    }

    /**
     * 开启蓝牙功能模块
     *
     * @param activity
     * @param consumer 回调
     */
    @SuppressLint("CheckResult")
    public static void openBlt(Activity activity, Consumer<YcForResultBean> consumer) throws YcBltException {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            throw new YcBltException("该设备不支持蓝牙功能", YcBluetoothExceptionErrorEnum.ERROR_NOT_SUPPER_BLT);
        }
        //检测设备是否打开蓝牙
        if (!bluetoothAdapter.enable()) {
            YcForResult result = new YcForResult(activity);
            Intent openBltIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (consumer != null) {
                result.start(openBltIntent).subscribe(consumer);
            } else {
                result.start(openBltIntent).subscribe();
            }
        }
    }

}
