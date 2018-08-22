package com.yc.ycutilslibrary;

import android.app.Application;
import android.support.annotation.IdRes;

import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.file.YcImgUtils;

import org.xutils.x;

/**
 * 初始化作用
 */

public class YcUtilsInit {
    public static Application mApplication = null;

    /**
     * 使用PrefHelper、下载必须先初始化
     *
     * @param application
     */
    public static void init(Application application) {
        mApplication = application;
        //下载XUtils3初始化
        x.Ext.init(application);
        x.Ext.setDebug(false); // 是否输出debug日志, 开启debug会影响性能.
    }

    /**
     * 设置加载网络图片时失败重新加载的次数
     *
     * @param num
     */
    public static void setReloadImgNum(int num) {
        if (num < 0) {
            YcLog.d("设置的失败重新加载次数小于0，按不重新加载执行");
        }
        YcImgUtils.IMG_FAIL_RELOAD_NUM = num;
    }
    public static void setLoadImgFail(@IdRes int imgIdRes){
        YcImgUtils.IMG_FAIL_ID_RES = imgIdRes;
    }
}
