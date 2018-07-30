package com.yc.ycutils;

import android.os.Bundle;
import android.view.View;

import com.yc.yclibrary.base.YcAppCompatActivity;

import butterknife.OnClick;

/**
 * Created by yc on 2018/7/30/0030.
 */

public class SelectActivity extends YcAppCompatActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.select_activity;
    }

    @Override
    protected void initView(Bundle bundle) {
    }

    @OnClick({R.id.selectPermissionBtn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectPermissionBtn:
                TestPermissionActivity.newInstance(getActivity());
                break;
        }
    }
}
