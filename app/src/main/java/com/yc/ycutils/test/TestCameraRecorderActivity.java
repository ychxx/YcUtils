package com.yc.ycutils.test;

import android.os.Bundle;
import android.view.View;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutils.R;
import com.yc.ycutilslibrary.camera.YcCameraVideotapeSfv;
import com.yc.ycutilslibrary.permissions.YcUtilPermission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class TestCameraRecorderActivity extends YcAppCompatActivity {
    @BindView(R.id.testCameraRecorderSfv)
    YcCameraVideotapeSfv mSfv;

    @Override
    protected int getLayoutId() {
        return R.layout.test_camera_recorder_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
        YcUtilPermission.newInstance(getActivity())
                .addPermissions(YcUtilPermission.PERMISSION_PHONE)
                .addPermissions(YcUtilPermission.PERMISSION_CAMERA)
                .addPermissions(YcUtilPermission.PERMISSION_MICROPHONE)
                .start();
    }


    @OnClick({R.id.testCameraRecorderStartBtn, R.id.testCameraRecorderEndBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.testCameraRecorderStartBtn:
                mSfv.startRecord();
                break;
            case R.id.testCameraRecorderEndBtn:
                mSfv.stopRecord();
                break;
        }
    }
}
