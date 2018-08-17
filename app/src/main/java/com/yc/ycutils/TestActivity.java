package com.yc.ycutils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.yc.yclibrary.base.YcAppCompatActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */

public class TestActivity extends YcAppCompatActivity {
    @BindView(R.id.test1Iv)
    ImageView mImageView1;
    @BindView(R.id.test2Iv)
    CropImageView mImageView2;

    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity, TestActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
        mImageView2.setImageResource(R.drawable.img_test);
    }

    @OnClick({R.id.test1Btn, R.id.test2Btn, R.id.test3Btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.test1Btn:
                Bitmap b = mImageView2.getCroppedImage();
                mImageView1.setImageBitmap(b);
                break;
            case R.id.test2Btn:
                break;
            case R.id.test3Btn:
                break;
        }
    }
}
