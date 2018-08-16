package com.yc.ycutilslibrary.action;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.yc.ycutilslibrary.common.YcEmpty;
import com.yc.ycutilslibrary.common.YcTransform;
import com.yc.ycutilslibrary.file.YcFileUtils;

import java.io.File;

/**
 *
 */

public class YcActionBean {
    private YcActionTypeEnum mActionType;
    private String mMsg;

    public void play(Fragment fragment) {
        switch (mActionType) {
            case SELECTOR:
                YcActionUtils.openFileManager(fragment, mMsg, mActionType.getRequestCode());
                break;
            case CAMERA:
                //TODO 权限申请功能后期完成
                File saveCameraImgFile;
                if (YcEmpty.isEmpty(mMsg)) {
                    mMsg = YcFileUtils.getImgFileName();
                }
                saveCameraImgFile = YcFileUtils.createFile(mMsg);
                YcActionUtils.openCamera(fragment, saveCameraImgFile, mActionType.getRequestCode());
                break;
            case WEB:
                YcActionUtils.openWeb(fragment, mMsg, mActionType.getRequestCode());
                break;
            case CROP:
                YcActionUtils.openCrop(fragment, mMsg, mActionType.getRequestCode());
                break;
            case TELEPHONE:
                YcActionUtils.openTelephone(fragment, mMsg, mActionType.getRequestCode());
                break;
            case SMS:
                YcActionUtils.openSms(fragment, mMsg, mActionType.getRequestCode());
                break;
            case SETTING:
                YcActionUtils.openSetting(fragment, mActionType.getRequestCode());
                break;
            case APP_INFO:
                YcActionUtils.openAppInfo(fragment, mActionType.getRequestCode());
                break;
        }
    }

    public String getPath(Intent data, Context context) {
        switch (mActionType) {
            case SELECTOR:
                return YcTransform.imgUriToAbsolutePath(context, data.getData());
            case CAMERA:
                return mMsg;
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

    public YcActionBean(YcActionTypeEnum actionType) {
        mActionType = actionType;
    }


    public YcActionTypeEnum getActionType() {
        return mActionType;
    }

    public void setActionType(YcActionTypeEnum actionType) {
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
