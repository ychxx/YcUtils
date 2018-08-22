package com.yc.ycutilslibrary.action.bean;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.yc.ycutilslibrary.action.YcAction;
import com.yc.ycutilslibrary.action.YcActionTypeEnum;
import com.yc.ycutilslibrary.action.YcActionUtils;
import com.yc.ycutilslibrary.common.YcEmpty;
import com.yc.ycutilslibrary.common.YcTransform;
import com.yc.ycutilslibrary.file.YcFileUtils;

import java.io.File;

/**
 *
 */

public abstract class YcActionBean {

    public YcActionBean() {
    }

    public abstract void start(Fragment fragment);

    public abstract String result(Intent data, Context context);

    public abstract YcActionTypeEnum getActionType();

    //    public void setActionType(YcActionTypeEnum actionType) {
//        this.mActionType = actionType;
//    }
    public abstract int getRequestCode();
//
//    public YcAction newAction() {
//    }
}
