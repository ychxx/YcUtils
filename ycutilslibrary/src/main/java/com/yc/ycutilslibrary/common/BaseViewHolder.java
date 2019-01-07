package com.yc.ycutilslibrary.common;

import android.support.v7.widget.RecyclerView.ViewHolder;

import com.classic.adapter.BaseAdapterHelper;

/**
 *
 */

public class BaseViewHolder extends ViewHolder {
    private BaseViewHolderHelper mAdapterHelper;

    public BaseViewHolder(BaseViewHolderHelper adapterHelper) {
        super(adapterHelper.getView());
        this.mAdapterHelper = adapterHelper;
    }

    public BaseViewHolderHelper getAdapterHelper() {
        return mAdapterHelper;
    }
}
