package com.yc.ycutils;

import android.app.Application;

import com.yc.yclibrary.YcInit;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 *
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        YcInit.init(this,"https://bg.fnpsy.com/");
    }
}
