package com.yc.ycutilslibrary.action.bean;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.yc.ycutilslibrary.action.YcAction;
import com.yc.ycutilslibrary.action.YcActionTypeEnum;
import com.yc.ycutilslibrary.action.YcActionUtils;

/**
 *
 */

public class YcActionTelephoneBean extends YcActionBean {
    private String phoneNum;//手机号码

    @Override
    public void start(Fragment fragment) {
        YcActionUtils.openTelephone(fragment, phoneNum, getActionType().getRequestCode());
    }

    @Override
    public String result(Intent data, Context context) {
        return "";
    }
    @Override
    public YcActionTypeEnum getActionType() {
        return YcActionTypeEnum.TELEPHONE;
    }

    @Override
    public int getRequestCode() {
        return YcActionTypeEnum.TELEPHONE.getRequestCode();
    }
}
