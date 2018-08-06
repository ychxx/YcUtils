package com.yc.ycutils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.action.ActionTypeEnum;
import com.yc.ycutilslibrary.action.YcAction;
import com.yc.ycutilslibrary.action.YcActionBean;

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

    @OnClick({R.id.selectPermissionBtn, R.id.selectAction})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectPermissionBtn:
                TestPermissionActivity.newInstance(getActivity());
                break;
            case R.id.selectAction:
//                YcActionFragment.newInstance(getActivity(), null, null);
                YcAction.newInstance(getActivity())
                        .newAction(ActionTypeEnum.SELECTOR)
                        .addMsg("*/*")
                        .newAction(ActionTypeEnum.TELEPHONE)
                        .addMsg("18094012545")
                        .newAction(ActionTypeEnum.SETTING)
                        .newAction(ActionTypeEnum.APP_INFO)
                        .newAction(ActionTypeEnum.WEB)
                        .addMsg("https://www.baidu.com")
                        .setResultSuccess(new YcAction.ResultSuccess() {
                            @Override
                            public void onSuccess(String path) {
                                Log.e("asd", "成功：" + path);
                            }
                        })
                        .setResultFail(new YcAction.ResultFail() {
                            @Override
                            public void onFail() {
                                Log.e("asd", "失败");
                            }
                        }).start();
                break;
        }
    }
}
