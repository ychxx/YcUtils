package com.yc.ycutilslibrary.action;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import com.yc.ycutilslibrary.common.YcTransform;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class YcAction {

    private Activity activity;
    private ResultSuccess mResultSuccess;
    private ResultFail mResultFail;

    public static YcAction newInstance(Activity activity) {
        YcAction ycAction = new YcAction();
        ycAction.activity = activity;
        return ycAction;
    }

    List<YcActionBean> mYcActionBeans = new ArrayList<>();

    public YcAction newAction(ActionTypeEnum actionTypeEnum) {
        mYcActionBeans.add(new YcActionBean(actionTypeEnum));
        return this;
    }

    public YcAction addMsg(String msg) {
        mYcActionBeans.get(mYcActionBeans.size() - 1).setMsg(msg);
        return this;
    }


    public YcAction setResultSuccess(ResultSuccess resultSuccess) {
        mResultSuccess = resultSuccess;
        return this;
    }

    public YcAction setResultFail(ResultFail resultFail) {
        mResultFail = resultFail;
        return this;
    }

    public void start() {
        YcActionFragment.newInstance(activity, new YcActionFragment.Start() {
            @Override
            public void onExe(Fragment fragment) {
                startNext(fragment);
            }
        }, new YcActionFragment.Result() {
            @Override
            public void onCall(Fragment fragment, int requestCode, int resultCode, Intent data) {
                if (resultCode == Activity.RESULT_OK && requestCode == currentActionBean.getRequestCode()) {
                    if (mResultSuccess != null)
                        mResultSuccess.onSuccess(currentActionBean.getPath(data, fragment.getActivity()));
                } else {
                    if (mResultFail != null) {
                        mResultFail.onFail();
                    }
                }
                startNext(fragment);
            }
        });
    }

    private YcActionBean currentActionBean;

    private void startNext(Fragment fragment) {
        if (mYcActionBeans == null || mYcActionBeans.size() <= 0)
            return;
        int lastIndex = mYcActionBeans.size() - 1;
        currentActionBean = mYcActionBeans.get(lastIndex);
        currentActionBean.play(fragment);
        mYcActionBeans.remove(lastIndex);
    }

    public interface ResultFail {
        void onFail();
    }

    public interface ResultSuccess {
        void onSuccess(String path);
    }
}
