package com.yc.ycutilslibrary.common;

import android.text.TextUtils;

/**
 * 空处理
 */

public class YcEmpty {
    /**
     * 获取字符串
     *
     * @param data 数据
     * @return null时返回“”
     */
    public static String getString(String data) {
        return TextUtils.isEmpty(data) ? "" : data;
    }

    /**
     * 获取字符串
     *
     * @param data  数据
     * @param empty null时返回的字符串
     * @return
     */
    public static String getString(String data, String empty) {
        return TextUtils.isEmpty(data) ? empty : data;
    }
}
