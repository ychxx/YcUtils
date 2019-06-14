package com.yc.ycutils;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.widget.dialog.TopDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class TestDialogActivity extends YcAppCompatActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_dialog;
    }

    TopDialog mTopDialog;

    @Override
    protected void initView(Bundle bundle) {
        mTopDialog = new TopDialog(getActivity());
    }

    @OnClick({R.id.button5, R.id.button6, R.id.button7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button5:
                mTopDialog.show();
                break;
            case R.id.button6:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder
                        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setTitle("这是标题")
                        .setMessage("这是内容")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.button7:
                break;
        }
    }
}
