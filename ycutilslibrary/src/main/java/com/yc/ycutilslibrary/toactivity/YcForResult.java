package com.yc.ycutilslibrary.toactivity;

import android.app.Activity;
import android.content.Intent;

import io.reactivex.Observable;

/**
 *  带返回参数的跳转
 */

public class YcForResult {
    private YcForResultFragment mFragment;
    private Intent intent;
    public YcForResult(Activity activity) {
        mFragment = YcForResultFragment.getFragment(activity);
    }
    public Observable<YcForResultBean> start(Intent intent) {
        return mFragment.put(intent);
    }
}
