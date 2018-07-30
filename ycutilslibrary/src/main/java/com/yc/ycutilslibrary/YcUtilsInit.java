package com.yc.ycutilslibrary;

import android.app.Application;

/**
 *  初始化作用
 */

public class YcUtilsInit {
    public static Application mApplication = null;

    /**
     * 使用PrefHelper必须先初始化
     * @param application
     */
    public static void init(Application application) {
        mApplication = application;
    }
}
