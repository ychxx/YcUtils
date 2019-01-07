package com.yc.ycutils.test.vr_img;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutils.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  vr图片展示
 */

public class VrImgActivity extends YcAppCompatActivity {
    @BindView(R.id.pano_view)
    VrPanoramaView panoView;

    @Override
    protected int getLayoutId() {
        return R.layout.test_vr_img_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
        panoView.setEventListener(new VrPanoramaEventListener());
        VrPanoramaView.Options  paNormalOptions = new VrPanoramaView.Options();
//  mVrPanoramaView.setFullscreenButtonEnabled (false); //隐藏全屏模式按钮
        panoView.setInfoButtonEnabled(false); //设置隐藏最左边信息的按钮
        panoView.setStereoModeButtonEnabled(false); //设置隐藏立体模型的按钮
        panoView.setEventListener(new VrPanoramaEventListener()); //设置监听
        paNormalOptions.inputType = VrPanoramaView.Options.TYPE_MONO;
        //加载本地的图片源
        panoView.loadImageFromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.vr1), paNormalOptions);

    }

}
