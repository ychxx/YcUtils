package com.yc.ycutilslibrary.action.bean;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.yc.ycutilslibrary.constant.YcActionTypeEnum;
import com.yc.ycutilslibrary.action.YcActionUtils;
import com.yc.ycutilslibrary.common.YcEmpty;
import com.yc.ycutilslibrary.file.YcFileUtils;

import java.io.File;

/**
 *
 */

public class YcActionCameraBean extends YcActionBean {
    private String imgSavePath;//照片保存路径

    @Override
    public void start(Fragment fragment) {
        File saveCameraImgFile;
        if (YcEmpty.isEmpty(imgSavePath)) {
            imgSavePath = YcFileUtils.newRandomImgFileName();
        }
        saveCameraImgFile = YcFileUtils.createFile(imgSavePath);
        YcActionUtils.openCamera(fragment, saveCameraImgFile, getActionType());
    }

    @Override
    public String result(Intent data, Context context) {
        return imgSavePath;
    }

    @Override
    @YcActionTypeEnum
    public int getActionType() {
        return YcActionTypeEnum.CAMERA;
    }

    @Override
    public int getRequestCode() {
        return YcActionTypeEnum.CAMERA;
    }
}
