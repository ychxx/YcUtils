package com.yc.ycutils.test.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.yc.ycutils.R;
import com.yc.ycutilslibrary.common.BaseViewHolder;
import com.yc.ycutilslibrary.common.YcRecycleViewAdapter;

import io.reactivex.functions.Consumer;

/**
 *
 */

public class TestRecycleViewAdapter extends YcRecycleViewAdapter {


    public TestRecycleViewAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.test_item;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public String getItem(int position) {
        return position+"asd";
    }
}
