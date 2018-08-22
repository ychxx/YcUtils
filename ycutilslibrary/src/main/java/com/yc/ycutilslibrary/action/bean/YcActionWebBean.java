package com.yc.ycutilslibrary.action.bean;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.yc.ycutilslibrary.action.YcActionTypeEnum;
import com.yc.ycutilslibrary.action.YcActionUtils;

/**
 *
 */

public class YcActionWebBean extends YcActionBean {
    private String url;//网页地址

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public void start(Fragment fragment) {
        YcActionUtils.openWeb(fragment, url, getActionType().getRequestCode());
    }

    @Override
    public String result(Intent data, Context context) {
        return "";
    }
    @Override
    public YcActionTypeEnum getActionType() {
        return YcActionTypeEnum.WEB;
    }

    @Override
    public int getRequestCode() {
        return YcActionTypeEnum.WEB.getRequestCode();
    }

}
