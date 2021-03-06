package com.yc.ycutilslibrary.action;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.ycutilslibrary.R;

/**
 *
 */

public class YcActionFragment extends Fragment {
    static final String TAG = "YcActionFragment";//标识
    private Result mResult;
    private Start mStart;

    public static YcActionFragment newInstance(Activity activity, Start start, Result result) {
        YcActionFragment ycActionFragment = (YcActionFragment) activity.getFragmentManager().findFragmentByTag(TAG);
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (ycActionFragment != null) {
            fragmentManager.beginTransaction().remove(ycActionFragment).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        ycActionFragment = new YcActionFragment();
        ycActionFragment.mResult = result;
        ycActionFragment.mStart = start;
        fragmentManager.beginTransaction()
                .add(ycActionFragment, TAG)
                .commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        return ycActionFragment;
    }

    public YcActionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.yc_crop_activity, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//防止Fragment因配置改变而销毁
        if (mStart != null) {
            mStart.onExe(this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mResult != null) {
            mResult.onCall(this, requestCode, resultCode, data);
        }
    }

    public interface Result {
        void onCall(Fragment fragment, int requestCode, int resultCode, Intent data);
    }

    public interface Start {
        void onExe(Fragment fragment);
    }
}
