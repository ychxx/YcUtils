package com.yc.ycutilslibrary;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;

import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.file.YcImgUtils;

import org.xutils.x;

/**
 * 初始化作用
 */

public class YcUtilsInit {
    private static Application mApplication = null;

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
     */
    public static void setReloadImgNum(int num) {
        if (num < 0) {
            YcLog.d("设置的失败重新加载次数小于0，按不重新加载执行");
        }
        YcImgUtils.IMG_FAIL_RELOAD_NUM = num;
    }
    /**
     * 设置加载网络图片失败时显示的图片
     */
    public static void setLoadImgDefaultFail(@IdRes int imgIdRes) {
        YcImgUtils.IMG_FAIL_ID_RES = imgIdRes;
    }
    /**
     * 设置加载网络图片时显示的图片
     */
    public static void setLoadImgDefaultLoading(@IdRes int imgIdRes) {
        YcImgUtils.IMG_LOADING_ID_RES = imgIdRes;
    }
    public static Application getApplication(){
        return mApplication;
    }
    public static Resources getResources() {
        if (mApplication == null) {
            return Resources.getSystem();
        } else {
            return mApplication.getResources();
        }
    }
}
