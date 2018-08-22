package com.yc.ycutilslibrary.action;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import com.yc.ycutilslibrary.action.bean.YcActionBean;
import com.yc.ycutilslibrary.action.bean.YcActionCameraBean;
import com.yc.ycutilslibrary.action.bean.YcActionSelectorBean;

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

    public YcActionSelectorBean newActionSelector() {
        YcActionSelectorBean selectorBean = new YcActionSelectorBean();
        mYcActionBeans.add(selectorBean);
        return selectorBean;
    }

    public YcActionCameraBean newActionCamera() {
        YcActionCameraBean selectorBean = new YcActionCameraBean();
        mYcActionBeans.add(selectorBean);
        return selectorBean;
    }
//    @SuppressWarnings("unchecked")
//    public <T> T newActionCamera(final Class<T> actionClass) {
//        return actionClass.newInstance();
//    }

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
                        mResultSuccess.onSuccess(currentActionBean.result(data, fragment.getActivity()), currentActionBean.getActionType());
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
        currentActionBean.start(fragment);
        mYcActionBeans.remove(0);
    }

    public interface ResultFail {
        void onFail(YcActionTypeEnum actionTypeEnum);
    }

    public interface ResultSuccess {
        void onSuccess(String path, YcActionTypeEnum actionTypeEnum);
    }
}
