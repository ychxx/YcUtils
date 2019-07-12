package com.yc.ycutilslibrary.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  照相机状态
 */
@IntDef({CameraStateEnum.START, CameraStateEnum.PREVIEW, CameraStateEnum.STOP, CameraStateEnum.ERROR})
@Retention(RetentionPolicy.SOURCE)
public @interface CameraStateEnum {
    int START = 0;
    int PREVIEW = 1;
    int STOP = 2;
    int ERROR = 3;
}
