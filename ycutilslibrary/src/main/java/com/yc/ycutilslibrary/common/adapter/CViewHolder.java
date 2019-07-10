package com.yc.ycutilslibrary.common.adapter;

import android.support.v7.widget.RecyclerView.ViewHolder;

/**
 *复制于CommonAdapter里的ViewHolder
 */

public class CViewHolder extends ViewHolder {
    private CViewHolderHelper mAdapterHelper;

    public CViewHolder(CViewHolderHelper adapterHelper) {
        super(adapterHelper.getView());
        this.mAdapterHelper = adapterHelper;
    }

    public CViewHolderHelper getAdapterHelper() {
        return mAdapterHelper;
    }
}
