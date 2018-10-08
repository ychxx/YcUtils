package com.yc.ycutilslibrary.toactivity;

import android.content.Intent;

/**
 *  返回的数据
 */

public class YcForResultBean {
    private int mResultCode;
    private Intent mData;

    public YcForResultBean(int resultCode, Intent data) {
        this.mResultCode = resultCode;
        this.mData = data;
    }

    public int getResultCode() {
        return mResultCode;
    }

    public void setResultCode(int resultCode) {
        this.mResultCode = resultCode;
    }

    public Intent getData() {
        return mData;
    }

    public void setData(Intent mData) {
        this.mData = mData;
    }
}
