package com.yc.ycutilslibrary.action.bean;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.yc.ycutilslibrary.constant.YcActionTypeEnum;

/**
 *
 */

public abstract class YcActionBean {

    public YcActionBean() {
    }

    public abstract void start(Fragment fragment);

    public abstract String result(Intent data, Context context);

    @YcActionTypeEnum
    public abstract int getActionType();

    //    public void setActionType(YcActionTypeEnum actionType) {
//        this.mActionType = actionType;
//    }
    public abstract int getRequestCode();
//
//    public YcAction newAction() {
//    }
}
