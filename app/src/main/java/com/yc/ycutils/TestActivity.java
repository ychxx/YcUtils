package com.yc.ycutils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.call.IUploadFileCall;
import com.yc.ycutilslibrary.file.YcFileUtils;
import com.yc.ycutilslibrary.toactivity.YcForResult;
import com.yc.ycutilslibrary.toactivity.YcForResultBean;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
                YcForResult forResult = new YcForResult(getActivity());
                forResult.start(new Intent())
                        .subscribe(new Observer<YcForResultBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(YcForResultBean ycForResultBean) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                HashMap<String ,String> params = new HashMap<>();
                YcFileUtils.uploadFile(params, new IUploadFileCall() {
                    @Override
                    public void onSuccess(Object result) {

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(Exception ex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
            case R.id.test3Btn:
                break;
        }
    }
}
