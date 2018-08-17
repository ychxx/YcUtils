package com.yc.ycutilslibrary.action;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.R;
import com.yc.ycutilslibrary.R2;
import com.yc.ycutilslibrary.common.YcTransform;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */

public class YcCropActivity extends YcAppCompatActivity {
    @BindView(R2.id.ycCropImageView)
    CropImageView mCropImageView;
    private static final String IMG_PATH_KEY = "img_path";
    private static final String REQUEST_CODE_KEY = "request_code_key";
    private String mImgPath;
    private int mRequestCode;

    public static void newInstance(Fragment fragment, String imgPath, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), YcCropActivity.class);
        intent.putExtra(IMG_PATH_KEY, imgPath);
        intent.putExtra(REQUEST_CODE_KEY, requestCode);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.yc_crop_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
        int i = R.id.ycCropBreakIv;
        mImgPath = getIntent().getStringExtra(IMG_PATH_KEY);
        mRequestCode = getIntent().getIntExtra(REQUEST_CODE_KEY, 0);
        mCropImageView.setImageBitmap(YcTransform.imgIdResToBitmap(getActivity(),R.drawable.img_loading));
    }

    @OnClick({R2.id.ycCropBreakIv, R2.id.ycCropOkIv})
    void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ycCropBreakIv) {
            finish();
        } else if (i == R.id.ycCropOkIv) {
            Bitmap bitmap = mCropImageView.getCroppedImage();
            Intent intent = new Intent();
//                setResult();

        }
    }
}
