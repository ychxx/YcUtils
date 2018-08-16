package com.yc.ycutilslibrary.action;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class YcAction {
    public final static int EMPTY_REQUEST_CODE = 233;
    private Activity activity;
    private ResultSuccess mResultSuccess;
    private ResultFail mResultFail;
    public static YcAction newInstance(Activity activity) {
        YcAction ycAction = new YcAction();
        ycAction.activity = activity;
        return ycAction;
    }

    List<YcActionBean> mYcActionBeans = new ArrayList<>();

    public YcAction newAction(YcActionTypeEnum actionTypeEnum) {
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
                if (resultCode == Activity.RESULT_OK && requestCode == currentActionBean.getRequestCode() || requestCode == EMPTY_REQUEST_CODE) {
                    if (mResultSuccess != null)
                        mResultSuccess.onSuccess(currentActionBean.getPath(data, fragment.getActivity()), currentActionBean.getActionType());
                } else {
                    if (mResultFail != null) {
                        mResultFail.onFail(currentActionBean.getActionType());
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
        currentActionBean = mYcActionBeans.get(0);
        currentActionBean.play(fragment);
        mYcActionBeans.remove(0);
    }

    public interface ResultFail {
        void onFail(YcActionTypeEnum actionTypeEnum);
    }

    public interface ResultSuccess {
        void onSuccess(String path, YcActionTypeEnum actionTypeEnum);
    }
}
