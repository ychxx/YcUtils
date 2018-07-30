package com.yc.ycutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.permissions.YcUtilPermission;

import butterknife.OnClick;

/**
 * Created by yc on 2018/7/30/0030.
 */

public class TestPermissionActivity extends YcAppCompatActivity {

    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity, TestPermissionActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.permission_activity;
    }

    @Override
    protected void initView(Bundle bundle) {

    }

    private void getPermission() {
        YcUtilPermission.newInstance(getActivity())
                .addPermissions(YcUtilPermission.PERMISSION_PHONE)
                .addPermissions(YcUtilPermission.PERMISSION_CAMERA)
                .addPermissions(YcUtilPermission.PERMISSION_LOCATION)
                .setSuccessCall(() -> showToast("成功"))
                .setFailCall(() -> showToast("申请权限被拒绝!"))
                .setNeverAskAgainCall(() -> showToast("申请权限被拒绝且勾选了『不再询问』"))
                .start();
    }

    @OnClick({R.id.testPermissionBtn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.testPermissionBtn:
                getPermission();
                break;
        }
    }
}
