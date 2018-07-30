package com.yc.ycutilslibrary.common;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * 跳转到其他应用
 */

public class YcIntentAction {
    private final int YC_FILE_MANAGER_CODE = 23301;
    private OpenFileCall mOpenFileCall;

    /**
     * 打开文件管理器（不限制文件类型）
     *
     * @param activity     页面
     * @param openFileCall 结果回调
     * @param activity     页面
     */
    public void openFileManager(Activity activity, OpenFileCall openFileCall) {
        openFileManager(activity, "*/*", openFileCall);
    }

    /**
     * 打开文件管理器
     *
     * @param activity     页面
     * @param fileType     限制打开的文件类型
     * @param openFileCall 结果回调
     */
    public void openFileManager(Activity activity, String fileType, OpenFileCall openFileCall) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(fileType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        mOpenFileCall = openFileCall;
        activity.startActivityForResult(intent, YC_FILE_MANAGER_CODE);
    }

    public interface OpenFileCall {
        void fileCall(int resultCode, Intent data);
    }

    /**
     * 该方法必须在Activity的onActivityResult里调用，否则回调失效
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case YC_FILE_MANAGER_CODE:
                if (mOpenFileCall != null) {
                    mOpenFileCall.fileCall(resultCode, data);
                }
                break;
        }
    }
}
