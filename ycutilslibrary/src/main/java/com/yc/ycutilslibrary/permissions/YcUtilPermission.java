package com.yc.ycutilslibrary.permissions;

import android.Manifest;
import android.app.Activity;
import android.util.Log;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 *
 */

public class YcUtilPermission {
    /**
     * 日历权限
     */
    public static final String[] PERMISSION_CALENDAR = {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};
    /**
     * 相机权限
     */
    public static final String[] PERMISSION_CAMERA = {Manifest.permission.CAMERA};
    /**
     * 联系人权限
     */
    public static final String[] PERMISSION_CONTACTS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS};
    /**
     * 位置权限
     */
    public static final String[] PERMISSION_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    /**
     * 麦克风权限
     */
    public static final String[] PERMISSION_MICROPHONE = {Manifest.permission.RECORD_AUDIO};
    /**
     * 手机权限
     */
    public static final String[] PERMISSION_PHONE = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.ADD_VOICEMAIL, Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS};
    /**
     * 传感器权限
     */
    public static final String[] PERMISSION_SENSORS = {Manifest.permission.BODY_SENSORS};
    /**
     * 短信权限
     */
    public static final String[] PERMISSION_SMS = {Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS};
    /**
     * 存储卡权限
     */
    public static final String[] PERMISSION_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 申请权限(多个申请结果会合并成一个(一个失败即为失败)，即PermissionCall只会被调用一次)
     *
     * @param activity    activity
     * @param permissions 权限名
     * @param call        回调
     */
    public static void requestPermissions(Activity activity, String[] permissions, PermissionCall call) {
        RxPermissions rxPermission = new RxPermissions(activity);
        rxPermission.request(permissions)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        call.onSuccess();
                    } else {
                        call.onFail();
                    }
                });
    }

    public static void requestPermissionsMoreResult(Activity activity, String[] permissions, PermissionMoreCall call) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.i("YcUtilPermission", "" + permission.name + "用户已经同意该权限");
                            if (call != null) call.onSuccess();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.i("YcUtilPermission", "" + permission.name + "用户拒绝了该权限，没有选中『不再询问』");
                            if (call != null) call.onFail();
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                            Log.i("YcUtilPermission", "" + permission.name + "用户拒绝了该权限，并且选中『不再询问』");
                            if (call != null) call.onNoPrompt();
                        }
                    }
                });
    }

    /**
     * 权限回调，用于处理申请成功和失败的回调
     */
    public interface PermissionCall {
        /**
         * 请求权限成功
         */
        void onSuccess();

        /**
         * 请求权限失败
         */
        void onFail();
    }

    public interface PermissionMoreCall extends PermissionCall {
        /**
         * 用户拒绝了该权限，并且选中『不再询问』
         */
        void onNoPrompt();
    }
}

