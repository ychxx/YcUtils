package com.yc.ycutilslibrary.action.bean;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.yc.ycutilslibrary.action.YcActionTypeEnum;
import com.yc.ycutilslibrary.action.YcActionUtils;
import com.yc.ycutilslibrary.common.YcTransform;

/**
 *
 */

public class YcActionSelectorBean extends YcActionBean {
    private String openFileType;//打开的类型

    public String getOpenFileType() {
        return openFileType;
    }

    public void setOpenFileType(String openFileType) {
        this.openFileType = openFileType;
    }

    @Override
    public void start(Fragment fragment) {
        YcActionUtils.openFileManager(fragment, openFileType, getActionType().getRequestCode());
    }

    @Override
    public String result(Intent data, Context context) {
        return YcTransform.imgUriToAbsolutePath(context, data.getData());
    }
    @Override
    public YcActionTypeEnum getActionType() {
        return YcActionTypeEnum.SELECTOR;
    }

    @Override
    public int getRequestCode() {
        return YcActionTypeEnum.SELECTOR.getRequestCode();
    }
}
