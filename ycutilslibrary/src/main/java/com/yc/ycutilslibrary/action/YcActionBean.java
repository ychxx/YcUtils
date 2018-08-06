package com.yc.ycutilslibrary.action;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.yc.ycutilslibrary.common.YcTransform;

/**
 *
 */

public class YcActionBean {
    private ActionTypeEnum mActionType;
    private String mMsg;

    public void play(Fragment fragment) {
        switch (mActionType) {
            case SELECTOR:
                YcActionUtils.openFileManager(fragment, mMsg, mActionType.getRequestCode());
                break;
            case CAMERA:
                YcActionUtils.openCamera(fragment, mMsg, mActionType.getRequestCode());
                break;
            case WEB:
                YcActionUtils.openWeb(fragment, mMsg);
                break;
            case CROP:
                YcActionUtils.openCrop(fragment, mMsg, mActionType.getRequestCode());
                break;
            case TELEPHONE:
                YcActionUtils.openTelephone(fragment, mMsg);
                break;
            case SMS:
                YcActionUtils.openSms(fragment, mMsg, mActionType.getRequestCode());
                break;
            case SETTING:
                YcActionUtils.openSetting(fragment);
                break;
            case APP_INFO:
                YcActionUtils.openAppInfo(fragment);
                break;
        }
    }

    public String getPath(Intent data, Context context) {
        switch (mActionType) {
            case SELECTOR:
                return YcTransform.imgUriToAbsolutePath(context, data.getData());
            case CAMERA:
                return YcTransform.imgUriToAbsolutePath(context, data.getData());
            case WEB:
                return "";
            case CROP:
                return YcTransform.imgUriToAbsolutePath(context, data.getData());
            case TELEPHONE:
                return "";
            case SMS:
                return YcTransform.imgUriToAbsolutePath(context, data.getData());
            case SETTING:
                return "";
            case APP_INFO:
                return "";
            default:
                return "";
        }
    }

    public YcActionBean(ActionTypeEnum actionType) {
        mActionType = actionType;
    }


    public ActionTypeEnum getActionType() {
        return mActionType;
    }

    public void setActionType(ActionTypeEnum actionType) {
        this.mActionType = actionType;
    }

    public int getRequestCode() {
        return this.mActionType.getRequestCode();
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        this.mMsg = msg;
    }
}
