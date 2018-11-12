package com.yc.ycutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.action.YcCropActivity;
import com.yc.ycutilslibrary.actionnew.YcAction;
import com.yc.ycutilslibrary.common.YcRandom;
import com.yc.ycutilslibrary.configure.GlideApp;
import com.yc.ycutilslibrary.file.YcImgUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */

public class TestActionActivity extends YcAppCompatActivity {

    @BindView(R.id.test1Iv)
    ImageView test1Iv;
    @BindView(R.id.test2Iv)
    ImageView test2Iv;

    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity, TestActionActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_action_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
    }

    public static String getImgFileName() {
        return Environment.getExternalStorageDirectory() + "/YcUtils/" + YcRandom.getNameImgOfPNG();
    }

    @OnClick({R.id.test1Btn, R.id.test2Btn, R.id.test3Btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.test1Btn:
                YcAction action = new YcAction(getActivity());
                String imgPath = getImgFileName();
                String imgSavePath =Environment.getExternalStorageDirectory() + "/YcUtils/"+"test.png";
                action.startCamera(imgPath)
                        .doOnNext(ycForResultBean -> {
                            if (ycForResultBean.getResultCode() == Activity.RESULT_OK) {
                                Log.e("asd", "拍照成功");
                                YcImgUtils.loadLocalImg(getActivity(),imgPath,test1Iv);
                            } else {
                                Log.e("asd", "拍照失败");
                            }
                        }).flatMap(ycForResultBean -> new YcAction(getActivity()).startCrop(imgPath,imgSavePath))
                        .subscribe(ycForResultBean -> {
                            if (ycForResultBean.getResultCode() == YcAction.CODE_RESULT_FAIL) {
                                Log.e("asd", "裁剪成功");
                                YcImgUtils.loadLocalImg(getActivity(),imgSavePath,test1Iv);
                            } else {
                                Log.e("asd", "裁剪失败");
                            }
                        });
                break;
            case R.id.test2Btn:
                Intent intent = new Intent(getActivity(), YcCropActivity.class);
                intent.putExtra("img_path", "/storage/emulated/0/YcUtils/1539157946904_259.png");
                intent.putExtra( "img_path_save_key", Environment.getExternalStorageDirectory() + "/YcUtils/"+"test.png");
                startActivity(intent);
                break;
            case R.id.test3Btn:
                YcImgUtils.loadLocalImg(getActivity(),Environment.getExternalStorageDirectory() +"/YcUtils/1539157946904_259.png",test1Iv);
                break;
        }
    }
}
