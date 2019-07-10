package com.yc.ycutils.test.adapter;

import android.content.Context;

import com.yc.ycutils.R;
import com.yc.ycutilslibrary.common.adapter.CViewHolder;
import com.yc.ycutilslibrary.common.adapter.YcMultipleRecycleViewAdapter;

/**
 *
 */

public class TestRecycleViewAdapter extends YcMultipleRecycleViewAdapter {


    public TestRecycleViewAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public void onBindViewHolder(CViewHolder holder, int position) {
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
