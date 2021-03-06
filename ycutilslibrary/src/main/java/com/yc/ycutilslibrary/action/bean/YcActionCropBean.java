package com.yc.ycutilslibrary.action.bean;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.yc.ycutilslibrary.constant.YcActionTypeEnum;
import com.yc.ycutilslibrary.action.YcActionUtils;

/**
 *
 */

public class YcActionCropBean extends YcActionBean {
    private String imgPath;//资源图片地址
    private String imgSavePath;//截图保存路径

    @Override
    public void start(Fragment fragment) {
        YcActionUtils.openCrop(fragment, imgPath, imgSavePath, getActionType());
    }

    @Override
    public String result(Intent data, Context context) {
        return imgSavePath;
    }
    @Override @YcActionTypeEnum
    public int getActionType() {
        return YcActionTypeEnum.CROP;
    }

    @Override
    public int getRequestCode() {
        return YcActionTypeEnum.CROP;
    }
}
