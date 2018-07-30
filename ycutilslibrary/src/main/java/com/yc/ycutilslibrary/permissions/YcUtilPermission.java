package com.yc.ycutilslibrary.permissions;

import android.Manifest;
import android.app.Activity;
import android.util.Log;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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

    private YcUtilPermission() {
    }

    private WeakReference<Activity> mActivity;
    private String[] mRequestPermissions;
    private List<String> mFailPermissions = new ArrayList<>();
    private List<String> mSuccessPermissions = new ArrayList<>();
    private List<String> mNeverAskAgainPermissions = new ArrayList<>();
    private SuccessCall mSuccessCall;
    private FailCall mFailCall;
    private NeverAskAgainCall mNeverAskAgainCall;

    public YcUtilPermission newInstance(Activity activity, String[] permissions) {
        this.mActivity = new WeakReference<Activity>(activity);
        this.mRequestPermissions = permissions;
        return new YcUtilPermission();
    }

    public YcUtilPermission setSuccessCall(SuccessCall successCall) {
        mSuccessCall = successCall;
        return this;
    }

    public YcUtilPermission setFailCall(FailCall failCall) {
        mFailCall = failCall;
        return this;
    }

    public YcUtilPermission setNeverAskAgainCall(NeverAskAgainCall neverAskAgainCall) {
        mNeverAskAgainCall = neverAskAgainCall;
        return this;
    }

    public void start() {
        RxPermissions rxPermissions = new RxPermissions(mActivity.get());
        rxPermissions.requestEach(mRequestPermissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        int failSize = mFailPermissions.size();
                        int successSize = mSuccessPermissions.size();
                        int requestSize = mRequestPermissions.length;
                        int neverAskAgain = mNeverAskAgainPermissions.size();
                        if (neverAskAgain + failSize + successSize >= requestSize) {
                            if (failSize <= 0) {
                                mSuccessCall.onCall();
                            } else if (neverAskAgain != 0) {
                                mNeverAskAgainCall.onCall();
                            } else {
                                mFailCall.onCall();
                            }
                        } else if (permission.granted) {
                            // 用户已经同意该权限
                            Log.i("YcUtilPermission", "" + permission.name + "用户已经同意该权限");
                            mSuccessPermissions.add(permission.name);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.i("YcUtilPermission", "" + permission.name + "用户拒绝了该权限，没有选中『不再询问』");
                            mFailPermissions.add(permission.name);
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                            Log.i("YcUtilPermission", "" + permission.name + "用户拒绝了该权限，并且选中『不再询问』");
                            mNeverAskAgainPermissions.add(permission.name);
                        }
                    }
                });
    }

    public interface SuccessCall {
        void onCall();
    }

    public interface FailCall {
        void onCall();
    }

    public interface NeverAskAgainCall {
        void onCall();
    }
}

