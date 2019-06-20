package com.yc.ycutilslibrary.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 搜索蓝牙设备状态
 */
@IntDef({YcBluetoothFoundStateEnum.FOUND_START, YcBluetoothFoundStateEnum.FOUNDING, YcBluetoothFoundStateEnum.FOUND_FINISHED})
@Retention(RetentionPolicy.SOURCE)
public @interface YcBluetoothFoundStateEnum {
    /**
     * 开始搜索蓝牙设备
     */
    int FOUND_START = 0;
    /**
     * 正在搜索蓝牙设备
     */
    int FOUNDING = 1;
    /**
     * 搜索蓝牙设备完成
     */
    int FOUND_FINISHED = 2;
}
