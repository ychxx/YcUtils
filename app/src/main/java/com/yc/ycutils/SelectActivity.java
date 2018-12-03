package com.yc.ycutils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.constant.YcActionTypeEnum;
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

    @OnClick({R.id.selectPermissionBtn, R.id.selectAction, R.id.selectRandom,R.id.selectWidget})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectPermissionBtn:
                TestPermissionActivity.newInstance(getActivity());
                break;
            case R.id.selectAction:
                YcAction action= YcAction.newInstance(getActivity());
                action.newActionSelector().setOpenFileType("*/*");
                action.setResultSuccess((Intent path, @YcActionTypeEnum int actionTypeEnum)->{
//                    Intent intent = new Intent();
//                    ComponentName cn = new ComponentName("cn.wps.moffice_eng", "cn.wps.moffice.documentmanager.PreStartActivity2");
//                    intent.setAction(Intent.ACTION_MAIN);
//                    intent.setComponent(cn);
//                    intent.putExtra("aaa","lalla");
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);

                    Intent intent2 = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("OpenMode", "ReadOnly");//打开模式
                    //	bundle.putBoolean("SendCloseBroad", true);//关闭是否发送广播
                    bundle.putString("ThirdPackage",getPackageName());//输入自己应用包名
                    bundle.putBoolean("ClearTrace", true);//删除打开记录
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.setAction(android.content.Intent.ACTION_VIEW);
                    intent2.setClassName("cn.wps.moffice_eng", "cn.wps.moffice.documentmanager.PreStartActivity");
                    intent2.setData(path.getData());
                    intent2.putExtras(bundle);
                    startActivity(intent2);
                }).start();
//                YcAction.newInstance(getActivity())
////                        .newAction(YcActionTypeEnum.CAMERA)
//                        .newAction(YcActionTypeEnum.CROP)
//                        .addMsg("/storage/emulated/0/YcUtils/1534488187403_328/.png")
////                        .newAction(YcActionTypeEnum.WEB)
////                        .addMsg("https://www.baidu.com")
//                        .setResultSuccess(new YcAction.ResultSuccess() {
//                            @Override
//                            public void onSuccess(String path, YcActionTypeEnum actionTypeEnum) {
//                                Log.e("asd", "成功：" + path);
//                            }
//                        })
//                        .setResultFail(new YcAction.ResultFail() {
//                            @Override
//                            public void onFail(YcActionTypeEnum actionTypeEnum) {
//                                Log.e("asd", "失败");
//                            }
//                        }).start();
                break;
            case R.id.selectRandom:
                TestActivity.newInstance(getActivity());
                break;
            case R.id.selectWidget:
                TestWidgetActivity.newInstance(getActivity());
                break;
        }
    }
}
