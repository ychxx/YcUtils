package com.yc.ycutils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.action.YcActionTypeEnum;
import com.yc.ycutilslibrary.action.YcAction;
import com.yc.ycutilslibrary.exception.YcException;

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

    @OnClick({R.id.selectPermissionBtn, R.id.selectAction})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectPermissionBtn:
//                TestPermissionActivity.newInstance(getActivity());
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");  // 选择文件类型
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 233);
                break;
            case R.id.selectAction:
                YcAction.newInstance(getActivity())
                        .newAction(YcActionTypeEnum.SELECTOR)
                        .addMsg("*/*")
                        .newAction(YcActionTypeEnum.TELEPHONE)
                        .addMsg("18094012545")
                        .newAction(YcActionTypeEnum.SETTING)
                        .newAction(YcActionTypeEnum.APP_INFO)
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
        }
    }
}
