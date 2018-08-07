package com.yc.ycutilslibrary.action;

import static com.yc.ycutilslibrary.action.YcAction.EMPTY_REQUEST_CODE;

/**
 * 打开第三方应用的类型
 */
public enum ActionTypeEnum {
    //文件选择器       照相机页面      浏览器     裁剪页面       拨号页面         发短信      系统设置页面       App信息页面
    SELECTOR(23301), CAMERA(23302), WEB(EMPTY_REQUEST_CODE), CROP(23304), TELEPHONE(EMPTY_REQUEST_CODE), SMS(23305), SETTING(23306), APP_INFO(EMPTY_REQUEST_CODE);
    private int mRequestCode;

    ActionTypeEnum(int id) {
        mRequestCode = id;
    }

    public int getRequestCode() {
        return mRequestCode;
    }
}