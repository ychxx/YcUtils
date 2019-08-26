package com.yc.ycutils;

import android.app.Application;

import com.yc.yclibrary.YcInit;

import org.xutils.x;

/**
 *
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YcInit.init(this, "https://bg.fnpsy.com/");
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
    }
}
