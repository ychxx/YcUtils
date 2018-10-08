package com.yc.ycutilslibrary.common;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.yc.yclibrary.YcInit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用于获取资源(需初始化YcUtilsInit.init(x))
 */

public class YcResources {

    private static Resources getResources() {
        return YcInit.getContext().getResources();
    }

    /**
     * 获取颜色值
     *
     * @param resId 颜色资源id
     * @return 颜色值
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色值
     *
     * @param color 十六进制
     * @return
     */
    public static int getColor(String color) {
        return Color.parseColor(color);
    }

    /**
     * 获取Drawable
     *
     * @param resTd Drawable资源id
     * @return Drawable
     */
    public static Drawable getDrawable(int resTd) {
        return getResources().getDrawable(resTd);
    }

    /**
     * 获取字符串
     *
     * @param resId 字符串资源id
     * @return 字符串
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取字符串数组
     *
     * @param resId 数组资源id
     * @return 字符串数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取字符串数组
     *
     * @param resId 数组资源id
     * @return 字符串数组
     */
    public static List<String> getStringList(int resId) {
        return Arrays.asList(getResources().getStringArray(resId));
    }
}
