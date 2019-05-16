package com.yc.ycutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.widget.YcCircleImageView;

import butterknife.BindView;

/**
 *
 */

public class TestWidgetActivity extends YcAppCompatActivity {
    @BindView(R.id.widgetCircleIv)
    YcCircleImageView ycCircleImageView;
    @BindView(R.id.widgetCircleNetIv)
    YcCircleImageView netYcCircleImageView;
    private String imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534841642611&di=88c0745a0c0cebd2403bd95abf6e38d4&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3D5910c1edafec8a13144f5fe4c233bdb9%2Fd50735fae6cd7b89906ddf81092442a7d9330eaa.jpg";

    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity, TestWidgetActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.widget_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
        netYcCircleImageView.loadNewImg(imgUrl);
    }
}
