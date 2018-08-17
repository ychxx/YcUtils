package com.yc.ycutils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.action.YcActionTypeEnum;
import com.yc.ycutilslibrary.action.YcAction;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("asd", "asd");
    }

    @OnClick({R.id.selectPermissionBtn, R.id.selectAction,R.id.selectRandom})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectPermissionBtn:
                TestPermissionActivity.newInstance(getActivity());
                break;
            case R.id.selectAction:
                YcAction.newInstance(getActivity())
//                        .newAction(YcActionTypeEnum.CAMERA)
                        .newAction(YcActionTypeEnum.CROP)
                        .addMsg("/storage/emulated/0/YcUtils/1534488187403_328/.png")
//                        .newAction(YcActionTypeEnum.WEB)
//                        .addMsg("https://www.baidu.com")
                        .setResultSuccess(new YcAction.ResultSuccess() {
                            @Override
                            public void onSuccess(String path, YcActionTypeEnum actionTypeEnum) {
                                Log.e("asd", "成功：" + path);
                            }
                        })
                        .setResultFail(new YcAction.ResultFail() {
                            @Override
                            public void onFail(YcActionTypeEnum actionTypeEnum) {
                                Log.e("asd", "失败");
                            }
                        }).start();
                break;
            case R.id.selectRandom:
                TestActivity.newInstance(getActivity());
                break;
        }
    }
}
